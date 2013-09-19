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


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
public class Conveyance extends Matter {
	@Column(name="convey_type_id")
	private Long typeId;
	@OneToOne
	@JoinColumn(name="convey_type_id", nullable=false, updatable=false, insertable=false)
	private ConveyanceType type;
	@Transient
	private RiceServiceHelper RiceServiceHelper;
	/**
	 * default constructor which calls super class constructor
	 */
	public Conveyance() {
		super();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7207626236630736596L;

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
	/*@Override
	public Class<Work> getWorkClass() {
		return Work.class;
	}*/
}
