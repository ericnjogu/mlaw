/**
 * 
 */
package org.martinlaw.bo.contract;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012, 2013 Eric Njogu (kunadawa@gmail.com)
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
 * holds information on a contract
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_contract_t")
public class Contract extends Matter<Assignee, Work, Client, Consideration, Event> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2429155482331853569L;
	/**
	 * initializes fields
	 */
	public Contract() {
		setParties(new ArrayList<ContractParty>());
		setSignatories(new ArrayList<ContractSignatory>());
		setClients(new ArrayList<Client>());
		setEvents(new ArrayList<Event>());
		try {
			setConsiderations(createDefaultConsiderations(Consideration.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//column defined using reference below - this is for the sake of ojb
	@Transient
	private Long typeId;
	@OneToOne
	@JoinColumn(name = "contract_type_id", nullable = false, updatable = false)
	private ContractType type;
	@Column(name="service_offered", length=255)
	private String serviceOffered;
	@Column(name="summary_of_terms", length=255)
	private String summaryOfTerms;
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="contractId")
	private List<ContractParty> parties;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="contractId")
	private List<ContractSignatory> signatories;
	
	/*// column defined using reference below - this is for the sake of ojb
	@Transient
	private Long considerationId;
	@OneToOne
	@JoinColumn(name = "consideration_id", nullable = false, updatable = true)
	private Consideration consideration;*/
	
	// column defined using reference below - this is for the sake of ojb
	@Transient
	private Long contractDurationId;
	@OneToOne
	@JoinColumn(name = "contract_duration_id", nullable = false, updatable = true)
	private ContractDuration contractDuration;
	/**
	 * @return the contractTypeId
	 */
	public Long getTypeId() {
		return typeId;
	}
	/**
	 * @param contractTypeId the contractTypeId to set
	 */
	public void setTypeId(Long contractTypeId) {
		this.typeId = contractTypeId;
	}
	/**
	 * @return the type
	 */
	public ContractType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(ContractType contractType) {
		this.type = contractType;
	}
	
	/**
	 * @return the serviceOffered
	 */
	public String getServiceOffered() {
		return serviceOffered;
	}
	/**
	 * @param serviceOffered the serviceOffered to set
	 */
	public void setServiceOffered(String serviceOffered) {
		this.serviceOffered = serviceOffered;
	}
	/**
	 * @return the summaryOfTerms
	 */
	public String getSummaryOfTerms() {
		return summaryOfTerms;
	}
	/**
	 * @param summaryOfTerms the summaryOfTerms to set
	 */
	public void setSummaryOfTerms(String summaryOfTerms) {
		this.summaryOfTerms = summaryOfTerms;
	}
	/**
	 * @return the parties
	 */
	public List<ContractParty> getParties() {
		return parties;
	}
	/**
	 * @param parties the parties to set
	 */
	public void setParties(List<ContractParty> parties) {
		this.parties = parties;
	}
	/**
	 * @return the signatories
	 */
	public List<ContractSignatory> getSignatories() {
		return signatories;
	}
	/**
	 * @param signatories the signatories to set
	 */
	public void setSignatories(List<ContractSignatory> signatories) {
		this.signatories = signatories;
	}
	/**
	 * @return the considerationId
	 *//*
	public Long getConsiderationId() {
		return considerationId;
	}
	*//**
	 * @param considerationId the considerationId to set
	 *//*
	public void setConsiderationId(Long considerationId) {
		this.considerationId = considerationId;
	}*/
	/**
	 * the value of the contract, not what the client is paying for the contract to be drawn up
	 * 
	 * @return the consideration
	 *//*
	public Consideration getContractConsideration() {
		return consideration;
	}
	*//**
	 * @param consideration the consideration to set
	 *//*
	public void setContractConsideration(Consideration consideration) {
		this.consideration = consideration;
	}*/
	/**
	 * @return the contractDurationId
	 */
	public Long getContractDurationId() {
		return contractDurationId;
	}
	/**
	 * @param contractDurationId the contractDurationId to set
	 */
	public void setContractDurationId(Long contractDurationId) {
		this.contractDurationId = contractDurationId;
	}
	/**
	 * @return the contractDuration
	 */
	public ContractDuration getContractDuration() {
		return contractDuration;
	}
	/**
	 * @param contractDuration the contractDuration to set
	 */
	public void setContractDuration(ContractDuration contractDuration) {
		this.contractDuration = contractDuration;
	}
	@Override
	public Class<Work> getWorkClass() {
		return Work.class;
	}
	/**
	 * @return the consideration
	 *//*
	public Consideration getConsideration() {
		return consideration;
	}
	*//**
	 * @param consideration the consideration to set
	 *//*
	public void setConsideration(Consideration consideration) {
		this.consideration = consideration;
	}*/
}
