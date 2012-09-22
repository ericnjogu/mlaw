package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class MatterAssignee extends MartinlawPerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3568126367973141813L;
	@Id
	@Column(name = "assignee_id")
	private Long id;
	@Column(name = "assignment_id")
	private Long assignmentId;

	public MatterAssignee() {
		super();
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	
	}

	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the assignmentId
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}

	/**
	 * @param assignmentId the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

}