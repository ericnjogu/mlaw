/**
 * 
 */
package org.martinlaw.bo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * represents a court case
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_court_case_t")
public class CourtCase extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8431403352189825050L;

	@Id
	@Column(name="court_case_id")
	private Long id;
	
	/**law firms ref e.g. NN/N201/MN
	 * @ojb.field  column="local_reference"
	 */
	@Column(name="local_reference", length=20, nullable=false)
	private String localReference;
	
	/**
	 * court number - could be initially null as we await to file the papers in court
	 * 
	 */
	@Column(name="court_reference", length=20) 
	private String courtReference;

	//@Column(name="court_case_status_id", nullable=false)
	private Long statusId;
	/** 
	 * case e.g. Mike Vs Iron (2002) 
	 */
	@Column(name="name", length=100) 
	private String name;
	 
	@OneToOne
	@JoinColumn(name="court_case_status_id", nullable=false, updatable=false)
	private Status status;

	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="courtCaseId")
	private List<CourtCaseWitness> witnesses;

	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="courtCaseId")
	private List<CourtCaseClient> clients;
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="courtCaseId")
	private List<HearingDate> hearingDates;
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="courtCaseId")
	private List<CourtCaseFee> fees;
	
	public CourtCase() {
		super();
		//initialize collections
		setClients(new ArrayList<CourtCaseClient>());
		setWitnesses(new ArrayList<CourtCaseWitness>());
		setHearingDates(new ArrayList<HearingDate>());
		setFees(new ArrayList<CourtCaseFee>());
	}
	/**
	 * @return the localReference
	 */
	public String getLocalReference() {
		return localReference;
	}
	/**
	 * @param localReference the localReference to set
	 */
	public void setLocalReference(String localReference) {
		this.localReference = localReference;
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

	/* (non-Javadoc)
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	//@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> propMap = new LinkedHashMap<String, Object>();
		propMap.put("localReference", getLocalReference());
		propMap.put("courtReference", getCourtReference());
		return propMap;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	public List<CourtCaseClient> getClients() {
		return clients;
	}
	/**
	 * @param clients the clients to set
	 */
	public void setClients(List<CourtCaseClient> clients) {
		this.clients = clients;
	}
	/**
	 * @return the statusId
	 */
	public Long getStatusId() {
		return statusId;
	}
	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param hearingDates the hearingDates to set
	 */
	public void setHearingDates(List<HearingDate> hearingDates) {
		this.hearingDates = hearingDates;
	}
	/**
	 * @return the hearingDates
	 */
	public List<HearingDate> getHearingDates() {
		return hearingDates;
	}
	/**
	 * @param fees the fees to set
	 */
	public void setFees(List<CourtCaseFee> payments) {
		this.fees = payments;
	}
	/**
	 * @return the fees
	 */
	public List<CourtCaseFee> getFees() {
		return fees;
	}

}
