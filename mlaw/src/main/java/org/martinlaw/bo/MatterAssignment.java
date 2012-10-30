package org.martinlaw.bo;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012 Eric Njogu (kunadawa@gmail.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

@SuppressWarnings("rawtypes")
@MappedSuperclass
public abstract class MatterAssignment<M extends Matter, A extends MatterAssignee> extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1294047536564649104L;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "matterId")
	protected List<A> assignees;
	@Id
	@Column(name = "matter_id")
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
	 *//*
	public Long getId() {
		return id;
	}

	*//**
	 * @param id the id to set
	 *//*
	public void setId(Long id) {
		this.id = id;
	}
*/
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