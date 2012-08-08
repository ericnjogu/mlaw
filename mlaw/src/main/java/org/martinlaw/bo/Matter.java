package org.martinlaw.bo;


import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
/**
 * a super class that holds the information common to court case and conveyance
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public class Matter extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3504756475357807641L;
	/**law firms ref e.g. NN/N201/MN
	 * @ojb.field  column="local_reference"
	 */
	@Column(name = "local_reference", length = 20, nullable = false)
	private String localReference;
	//column defined using reference below - this is for the sake of ojb
	@Transient
	private Long statusId;
	/** 
	 * case e.g. Mike Vs Iron (2002) 
	 */
	@Column(name = "name", length = 100)
	private String name;
	@OneToOne
	@JoinColumn(name = "status_id", nullable = false, updatable = false)
	private Status status;
	public Matter() {
		super();
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

}