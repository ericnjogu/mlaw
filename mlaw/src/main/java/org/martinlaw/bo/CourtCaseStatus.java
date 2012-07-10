/**
 * 
 */
package org.martinlaw.bo;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * represents a court case status
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_court_case_status_t")
public class CourtCaseStatus extends PersistableBusinessObjectBase {
	/**
	 * initializes class with with default values for the fields
	 * 
	 * @param id - the primary key
	 * @param status - the description
	 */
	public CourtCaseStatus(Long id, String status) {
		this.id = id;
		this.status = status;
	}
	
	/**
	 * default constructor
	 */
	public CourtCaseStatus() {
		super();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 2361877298799195456L;
	/**
	 * the primary key
	 */
	@Id
	@Column(name="court_case_status_id")
	private Long id;
	/**
	 * e.g. 'pending hearing date'
	 */
	@Column(name="status", length=100, nullable=false)
	private String status;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/* (non-Javadoc)
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	// @Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> propMap = new LinkedHashMap<String, Object>();
		propMap.put("id", getId());
		propMap.put("status", getStatus());
		return propMap;
	}

}
