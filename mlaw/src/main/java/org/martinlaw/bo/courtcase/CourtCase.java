/**
 * 
 */
package org.martinlaw.bo.courtcase;

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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.martinlaw.bo.Matter;



/**
 * represents a court case
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_court_case_t")
public class CourtCase extends Matter<Assignee, Work, Client, Consideration, Event> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8431403352189825050L;
	
	/**
	 * court number - could be initially null as we await to file the papers in court
	 * 
	 */
	@Column(name="court_reference", length=200) 
	private String courtReference;

	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="courtCaseId")
	private List<CourtCaseWitness> witnesses;
	
	@Transient
	private Long typeId;
	@OneToOne
	@JoinColumn(name = "court_case_type_id", nullable = false, updatable = false)
	private CourtCaseType type;
	
	public CourtCase() {
		super();
		//initialize collections
		setClients(new ArrayList<Client>());
		setWitnesses(new ArrayList<CourtCaseWitness>());
		setEvents(new ArrayList<Event>());
		try {
			setConsiderations(createDefaultConsiderations(Consideration.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @return the courtReference
	 */
	public String getCourtReference() {
		return courtReference;
	}
	/**
	 * @param courtReference the courtReference to set
	 */
	public void setCourtReference(String courtReference) {
		this.courtReference = courtReference;
	}
	
	/**
	 * @return the witnesses
	 */
	public List<CourtCaseWitness> getWitnesses() {
		return witnesses;
	}
	/**
	 * @param witnesses the witnesses to set
	 */
	public void setWitnesses(List<CourtCaseWitness> witnesses) {
		this.witnesses = witnesses;
	}
	/**
	 * @return the clients
	 */
	
	@Override
	public Class<Work> getWorkClass() {
		return Work.class;
	}
	/**
	 * @return the courtCaseTypeId
	 */
	public Long getTypeId() {
		return typeId;
	}
	/**
	 * @param courtCaseTypeId the courtCaseTypeId to set
	 */
	public void setTypeId(Long courtCaseTypeId) {
		this.typeId = courtCaseTypeId;
	}
	/**
	 * @return the courtCaseType
	 */
	public CourtCaseType getType() {
		return type;
	}
	/**
	 * @param courtCaseType the courtCaseType to set
	 */
	public void setType(CourtCaseType courtCaseType) {
		this.type = courtCaseType;
	}
}
