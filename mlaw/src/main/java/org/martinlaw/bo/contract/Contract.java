/**
 * 
 */
package org.martinlaw.bo.contract;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.martinlaw.bo.Matter;

/**
 * holds information on a contract
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_contract_t")
public class Contract extends Matter {
	@Id
    @GeneratedValue(generator="martinlaw_contract_id_s")
	@GenericGenerator(name="martinlaw_contract_id_s",strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",parameters={
			@Parameter(name="sequence_name",value="martinlaw_contract_id_s"),
			@Parameter(name="value_column",value="id")
	})
	@Column(name="contract_id")
	private Long id;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2429155482331853569L;
	/**
	 * initializes fields
	 */
	public Contract() {
		parties = new ArrayList<ContractParty>();
		signatories = new ArrayList<ContractSignatory>();
	}
	//column defined using reference below - this is for the sake of ojb
	@Transient
	private Long typeId;
	@OneToOne
	@JoinColumn(name = "type_id", nullable = false, updatable = false)
	private ContractType type;
	@Column(name="service_offered", length=255)
	private String serviceOffered;
	@Column(name="summary_of_terms", length=255)
	private String summaryOfTerms;
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="contractId")
	private List<ContractParty> parties;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="contractId")
	private List<ContractSignatory> signatories;
	
	// column defined using reference below - this is for the sake of ojb
	@Transient
	private Long contractConsiderationId;
	@OneToOne
	@JoinColumn(name = "contract_consideration_id", nullable = false, updatable = true)
	private ContractConsideration contractConsideration;
	
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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the contractConsiderationId
	 */
	public Long getContractConsiderationId() {
		return contractConsiderationId;
	}
	/**
	 * @param contractConsiderationId the contractConsiderationId to set
	 */
	public void setContractConsiderationId(Long contractConsiderationId) {
		this.contractConsiderationId = contractConsiderationId;
	}
	/**
	 * @return the contractConsideration
	 */
	public ContractConsideration getContractConsideration() {
		return contractConsideration;
	}
	/**
	 * @param contractConsideration the contractConsideration to set
	 */
	public void setContractConsideration(ContractConsideration contractConsideration) {
		this.contractConsideration = contractConsideration;
	}
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
}
