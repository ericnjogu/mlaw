/**
 * 
 */
package org.martinlaw.bo;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * links a court case to a kuali person
 * 
 * @author mugo
 * 
 */
@Entity
@Table(name="martinlaw_court_case_client_t")
public class CourtCaseClient extends CourtCasePerson {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6185040053095072666L;
	@Id
	@Column(name="court_case_client_id")
	private Long id;

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
	
	/* (non-Javadoc)
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> propMap = new LinkedHashMap<String, Object>();
		propMap.put("courtCaseId", getCourtCaseId());
		propMap.put("principalName", getPrincipalName());
		propMap.put("courtCaseClientId", getId());
		return propMap;
	}

}
