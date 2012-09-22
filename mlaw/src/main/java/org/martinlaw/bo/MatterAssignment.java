package org.martinlaw.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

@MappedSuperclass
public abstract class MatterAssignment<M extends Matter, A extends MatterAssignee> extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1294047536564649104L;
	@Id
	@Column(name = "assignment_id")
	private Long id;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "assignmentId")
	protected List<A> assignees;
	@Transient
	private Long matterId;
	@OneToOne
	@JoinColumn(name = "matter_id", nullable = false, updatable = false)
	private M matter;

	public MatterAssignment() {
		super();
		assignees = new ArrayList<A>();
	}

	/**
	 * gets the pk for the matter - a contract or conveyance etc
	 *  
	 * @return the matterId
	 */
	public Long getMatterId() {
		return matterId;
	}

	/**
	 * @param matterId the matterId to set
	 */
	public void setMatterId(Long matterId) {
		this.matterId = matterId;
	}

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
	 * @return the assignees
	 */
	public List<A> getAssignees() {
		return assignees;
	}

	/**
	 * @param assignees the assignees to set
	 */
	public void setAssignees(List<A> assignees) {
		this.assignees = assignees;
	}

	/**
	 * @return the matter
	 */
	public M getMatter() {
		return matter;
	}

	/**
	 * @param matter the matter to set
	 */
	public void setMatter(M m) {
		this.matter = m;
	}

}