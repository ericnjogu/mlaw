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
 * specifies consideration type e.g. legal fee, contract value, deposit etc
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_consideration_type_t")
public class ConsiderationType extends BaseDetail implements ScopedKeyValue {
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "considerationTypeId")
	private List<ConsiderationTypeScope> scope;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4184910355599564922L;
	@Id
	@Column(name="consideration_type_id")
	private Long id;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * initialize lists
	 */
	public ConsiderationType() {
		setScope(new ArrayList<ConsiderationTypeScope>());
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String getKey() {
		return String.valueOf(getId());
	}
	@Override
	public String getValue() {
		return getName();
	}
	@Override
	public List<ConsiderationTypeScope> getScope() {
		return scope;
	}
	/**
	 * @param scope the scope to set
	 */
	public void setScope(List<ConsiderationTypeScope> scope) {
		this.scope = scope;
	}

}