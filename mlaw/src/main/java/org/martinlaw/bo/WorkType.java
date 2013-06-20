/**
 * 
 */
package org.martinlaw.bo;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2013 Eric Njogu (kunadawa@gmail.com)
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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.martinlaw.ScopedKeyValue;

/**
 * defines an work type
 * <p>e.g. demand letter, legal opinion, research, interview, meeting minutes etc</p>
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_work_type_t")
public class WorkType extends BaseDetail  implements ScopedKeyValue {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4927814949112777900L;
	@Id
	@Column(name="work_type_id")
	private Long id;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "workTypeId")
	private List<WorkTypeScope> scope;

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
	 * @return the scope
	 */
	public List<WorkTypeScope> getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(List<WorkTypeScope> scope) {
		this.scope = scope;
	}

	@Override
	public String getKey() {
		return String.valueOf(getId());
	}

	@Override
	public String getValue() {
		return getName();
	}

	/**
	 * default constructor
	 */
	public WorkType() {
		setScope(new ArrayList<WorkTypeScope>());
	}
}
