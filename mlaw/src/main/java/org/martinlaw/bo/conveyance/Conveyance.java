/**
 * 
 */
package org.martinlaw.bo.conveyance;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012,2013 Eric Njogu (kunadawa@gmail.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.martinlaw.bo.Matter;
import org.martinlaw.service.RiceServiceHelper;

/**
 * holds information that keeps  track of a conveyancing matter
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_conveyance_t")
public class Conveyance extends Matter<Assignee, Work, Client, Consideration, Event> {
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="conveyanceId")
	private List<ConveyanceAnnex> annexes;
	// column def given on the object reference below - this is for the sake of ojb
	@Transient
	private Long typeId;
	@Transient
	private RiceServiceHelper RiceServiceHelper;
	/**
	 * default constructor which initializes lists
	 */
	public Conveyance() {
		setClients(new ArrayList<Client>());
		annexes = new ArrayList<ConveyanceAnnex>();
		setRiceServiceHelper(new RiceServiceHelper());
		try {
			setConsiderations(createDefaultConsiderations(Consideration.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@OneToOne
	@JoinColumn(name="convey_type_id", nullable=false, updatable=false)
	private ConveyanceType type;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7207626236630736596L;

	/**
	 * gets the annexes, replacing the current list if the type id has changed
	 * 
	 * <p>replacement is only done if {@link #hasAttachments()} is false</p>
	 * 
	 * @return the annexes
	 */
	public List<ConveyanceAnnex> getAnnexes() {
		if (annexes == null || annexes.size() == 0) {
			if (getTypeId() != null) {
				annexes = createAnnexes();
			}
		} else {
			// check whether current type id is the one used to fetch the annexes
			// for some reason, the annex type in the annexes is null when it is fetched by ojb
			Long typeIdInAnnexes = null;
			if (annexes.get(0).getType() == null) {
				ConveyanceAnnexType convAnnexType = getRiceServiceHelper().getBusinessObjectService().findBySinglePrimaryKey(
						ConveyanceAnnexType.class, annexes.get(0).getConveyanceAnnexTypeId());
				if (convAnnexType != null) {
					typeIdInAnnexes = convAnnexType.getConveyanceTypeId();
				}
			} else {
				typeIdInAnnexes = annexes.get(0).getType().getConveyanceTypeId();
			}
			if (getTypeId() != null && typeIdInAnnexes != null && !getTypeId().equals(typeIdInAnnexes)) {
				if (hasAttachments()) {
					getLog().error("conveyance type id '" + getTypeId() 
							+ "' does not match '" +  typeIdInAnnexes 
							+ "' in current annexes with attachments already associated");
					getLog().info("restoring type id to '" + typeIdInAnnexes + "'");
					setTypeId(typeIdInAnnexes);
				} else {
					annexes = createAnnexes();	
				}
			}
		}
		return annexes;
	}
	/**
	 * create annexes from the each corresponding annex type
	 * 
	 * @return 
	 */
	protected List<ConveyanceAnnex> createAnnexes() {
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("conveyanceTypeId", getTypeId());
		Collection<ConveyanceAnnexType> result = getRiceServiceHelper().getBusinessObjectService().findMatching(ConveyanceAnnexType.class, params);
		if (result == null || result.size() == 0) {
			return Collections.emptyList();
		} else {
			List<ConveyanceAnnex> annexes = new ArrayList<ConveyanceAnnex>(result.size());
			for (ConveyanceAnnexType annexType: result) {
				ConveyanceAnnex annex = new ConveyanceAnnex();
				annex.setConveyanceAnnexTypeId(annexType.getId());
				annex.setType(annexType);
				annexes.add(annex);
			}
			return annexes;
		}
	}
	
	
	/**
	 * @param annexes the annexes to set
	 */
	public void setAnnexes(List<ConveyanceAnnex> annexes) {
		this.annexes = annexes;
	}
	
	/**
	 * @return the typeId
	 */
	public Long getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Long conveyanceTypeId) {
		this.typeId = conveyanceTypeId;
	}
	/**
	 * @return the type
	 */
	public ConveyanceType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(ConveyanceType conveyanceType) {
		this.type = conveyanceType;
	}
	
	/**
	 * checks whether any of the annexes has an attachment associated with it
	 * 
	 * Refering to annexes directly as  {@link #getAnnexes} uses this and it would create a recursive call
	 * 
	 * @return true if at least one annex has an attachment, false otherwise
	 */
	public boolean hasAttachments() {
		if (annexes == null) {
			return false;
		} else {
			if (annexes.size() == 0) {
				return false;
			} else {
				for (ConveyanceAnnex annex: annexes) {
					if (annex.getAttachments() != null && annex.getAttachments().size() != 0) {
						 /*sometimes, when the conv att has not been persisted, get attachment will return null yet an attachment
						 *may have been associated via the note time stamp*/
						for (ConveyanceAttachment convAtt: annex.getAttachments()) {
							if (convAtt.getAttachment() != null || convAtt.getNoteTimestamp() != null) {
								return true;
							}
						}
					}
				}
				return false;
			}
		}
	}
	/**
	 * @return the riceServiceHelper
	 */
	public RiceServiceHelper getRiceServiceHelper() {
		return RiceServiceHelper;
	}
	/**
	 * @param riceServiceHelper the riceServiceHelper to set
	 */
	public void setRiceServiceHelper(RiceServiceHelper riceServiceHelper) {
		RiceServiceHelper = riceServiceHelper;
	}
	/**
	 * avoids serialization errors which occur when log is a member of this class 
	 * @return the log
	 */
	protected Log getLog() {
		return LogFactory.getLog(getClass());
	}
	@Override
	public Class<Work> getWorkClass() {
		return Work.class;
	}
}
