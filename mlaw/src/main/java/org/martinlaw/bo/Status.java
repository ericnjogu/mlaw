/**
 * 
 */
package org.martinlaw.bo;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * represents a court case or conveyance status
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_status_t")
public class Status extends PersistableBusinessObjectBase {
	// could possibly become a full blown maintainable when the scope goes beyond court cases and conveyancing
	//TODO use locale props to display strings
	public static final ConcreteKeyValue COURT_CASE_TYPE = new ConcreteKeyValue("COURT_CASE_TYPE", "applies to court cases");
	public static final ConcreteKeyValue CONVEYANCE_TYPE = new ConcreteKeyValue("CONVEYANCE_TYPE", "applies to conveyancing");
	public static final ConcreteKeyValue ANY_TYPE = new ConcreteKeyValue("ANY_TYPE", "applies to conveyancing and court cases");
	/**
	 * initializes class with with default values for the fields
	 * 
	 * @param id - the primary key
	 * @param status - the description
	 */
	public Status(Long id, String status) {
		this.id = id;
		this.status = status;
	}
	
	/**
	 * default constructor
	 */
	public Status() {
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
	@Column(name="status_id")
	private Long id;
	/**
	 * e.g. 'pending hearing date'
	 */
	@Column(name="status", length=100, nullable=false)
	private String status;
	/**
	 * the type of the status - the key value of {@link #ANY_TYPE}, {@link #CONVEYANCE_TYPE} or {@link #COURT_CASE_TYPE}
	 */
	@Column(name="type", length=50, nullable=false)
	private String type;
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

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
