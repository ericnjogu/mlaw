/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * gives information on which matter(s) a status applies to
 * Status (one) -> StatusScope (many)
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_status_scope_t")
public class StatusScope extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 698178229853855467L;
	@Id
	@Column(name="status_scope_id")
	private Long id;
	@Column(name="status_id", nullable=false)
	private Long statusId;
	@Column(name="qualified_class_name", nullable=false, length=100)
	private String qualifiedClassName;
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
	 * the foreign key that links to the related status
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
	 * the qualified class name of the matter
	 * @return the qualifiedClassName
	 */
	public String getQualifiedClassName() {
		return qualifiedClassName;
	}
	/**
	 * @param qualifiedClassName the qualifiedClassName to set
	 */
	public void setQualifiedClassName(String qualifiedClassName) {
		this.qualifiedClassName = qualifiedClassName;
	}
	
	/**
	 * @return the simple matter class name for display to users
	 */
	public String getSimpleClassName() {
		if (getQualifiedClassName() == null) {
			return "";
		} else {
			return getQualifiedClassName().substring(getQualifiedClassName().lastIndexOf('.') + 1);
		}
	}
}
