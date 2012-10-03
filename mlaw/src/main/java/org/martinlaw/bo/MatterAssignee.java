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
	@Column(name = "matter_id")
	private Long matterId;

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
	 * @return the matterId
	 */
	public Long getMatterId() {
		return matterId;
	}

	/**
	 * @param matterId the matterId to set
	 */
	public void setMatterId(Long assignmentId) {
		this.matterId = assignmentId;
	}

}