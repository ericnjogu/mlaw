/**
 * 
 */
package org.martinlaw.bo.courtcase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.martinlaw.bo.Matter;



/**
 * represents a court case
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_court_case_t")
public class CourtCase extends Matter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8431403352189825050L;

	@Id
	@Column(name="court_case_id")
	private Long id;
	
	/**
	 * court number - could be initially null as we await to file the papers in court
	 * 
	 */
	@Column(name="court_reference", length=20) 
	private String courtReference;

	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="courtCaseId")
	private List<CourtCaseWitness> witnesses;

	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="courtCaseId")
	private List<CourtCaseDate> dates;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "matterId")
	private List<CourtCaseClient> clients;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "matterId")
	private List<CourtCaseFee> fees;
	
	public CourtCase() {
		super();
		//initialize collections
		setClients(new ArrayList<CourtCaseClient>());
		setWitnesses(new ArrayList<CourtCaseWitness>());
		setDates(new ArrayList<CourtCaseDate>());
		setFees(new ArrayList<CourtCaseFee>());
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
	 * @param dates the dates to set
	 */
	public void setDates(List<CourtCaseDate> courtCaseDates) {
		this.dates = courtCaseDates;
	}
	/**
	 * @return the dates
	 */
	public List<CourtCaseDate> getDates() {
		return dates;
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
