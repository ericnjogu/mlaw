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


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * holds info on an assignee
 * <p>use the person affiliation to specify an assignee type, e.g. staff, external, researcher, intern, auditor</p>
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_matter_assignee_t")
public class MatterAssignee extends MartinlawPerson {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3568126367973141813L;
	@Id
	@Column(name = "assignee_id")
	private Long id;
	@Column(name = "matter_id")
	private Long matterId;
	@Column(name = "active", columnDefinition=" varchar(1) not null")
	private Boolean active = true;
	@Column(name = "has_physical_file", columnDefinition=" varchar(1) not null")
	private Boolean hasPhysicalFile = false;
	@OneToOne
	@JoinColumn(name = "matter_id", nullable = false, updatable = false, insertable = false)
	private Matter matter;

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
	public void setMatterId(Long matterId) {
		this.matterId = matterId;
	}
	
	/**
	 * whether this assignment is active. The reasons for deactivation can be placed in the {@link #comment}
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * whether this assignee has the physical file
	 * @return the hasPhysicalFile
	 */
	public Boolean getHasPhysicalFile() {
		return hasPhysicalFile;
	}

	/**
	 * @param hasPhysicalFile the hasPhysicalFile to set
	 */
	public void setHasPhysicalFile(Boolean hasPhysicalFile) {
		this.hasPhysicalFile = hasPhysicalFile;
	}

	/**
	 * @return the matter
	 */
	public Matter getMatter() {
		return matter;
	}

	/**
	 * @param matter the matter to set
	 */
	public void setMatter(Matter matter) {
		this.matter = matter;
	}

}