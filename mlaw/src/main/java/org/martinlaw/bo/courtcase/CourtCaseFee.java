/**
 * 
 */
package org.martinlaw.bo.courtcase;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.martinlaw.bo.Fee;

/**
 * CourtCaseFee represents a fee paid to a lawyer by a client for a court case
 * @author mugo
 */
@Entity
@Table(name="martinlaw_court_case_fee_t")
// @Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class CourtCaseFee extends Fee {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5139498341007335334L;
	@Id
	@Column(name="court_case_fee_id")
	Long id;
	
	/**
	 * get the primary key
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
	 * @ojb.field column= indexed="true"
	 * participates in a 1:1 relationship with court case, the object relationship is impl as a collection on the 
	 * court case side
	 */
	@Column(name="court_case_id", nullable=false)
	private Long courtCaseId;
	
	/**
	 * @return the courtCaseId
	 */
	public Long getCourtCaseId() {
		return courtCaseId;
	}
	/**
	 * @param courtCaseId the courtCaseId to set
	 */
	public void setCourtCaseId(Long courtCaseId) {
		this.courtCaseId = courtCaseId;
	}
}
