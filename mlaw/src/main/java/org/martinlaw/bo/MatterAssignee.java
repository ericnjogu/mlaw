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
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * holds info on an assignee
 * <p>use the person affiliation to specify an assignee type, e.g. staff, external, researcher, intern, auditor</p>
 * @author mugo
 *
 */

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
	public void setMatterId(Long matterId) {
		this.matterId = matterId;
	}

}