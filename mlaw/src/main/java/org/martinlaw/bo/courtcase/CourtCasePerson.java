/**
 * 
 */
package org.martinlaw.bo.courtcase;


import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.martinlaw.bo.MartinlawPerson;


/**
 * @author mugo
 *serve as a common parent to court case witness and client
 */
@MappedSuperclass
public abstract class CourtCasePerson extends MartinlawPerson {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7290495005716959116L;
	
	@Column(name="court_case_id", nullable=false)
	private Long courtCaseId;

	@Override
	protected Map<String, Object> toStringMapper() {
		Map<String, Object> propMap = super.toStringMapper();
		propMap.put("courtCaseId", getCourtCaseId());
		return propMap;
	}
	
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
