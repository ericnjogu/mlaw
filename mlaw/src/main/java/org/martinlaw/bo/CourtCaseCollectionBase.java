/**
 * 
 */
package org.martinlaw.bo;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * holds the common fields present in objects that appear in the court case as a collection
 * 
 * @author mugo
 */
@MappedSuperclass
public class CourtCaseCollectionBase extends org.kuali.rice.krad.bo.PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8593072356110230223L;

	/**
	 * @ojb.field column= indexed="true"
	 * participates in a 1:1 relationship with court case, the object relationship is impl as a collection on the 
	 * court case side
	 */
	@Column(name="court_case_id", nullable=false)
	private Long courtCaseId;
	/* (non-Javadoc)
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	// @Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> propMap = new LinkedHashMap<String, Object>();
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
