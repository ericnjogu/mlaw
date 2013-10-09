/**
 * 
 */
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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.martinlaw.ScopedKeyValue;

/**
 * a super class that holds some common fields useful for creating types
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_type_t")
@Inheritance(strategy=InheritanceType.JOINED)
public class Type extends PersistableBusinessObjectBase  implements ScopedKeyValue {
	
	/**
	 * 
	 */
	public Type() {
		super();
		setScope(new ArrayList<Scope>());
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8844219522196909942L;
	@Column(name="name", length=100, nullable=false) 
	private String name;
	@Column(name = "description", length = 250)
	private String description;
	@Id
	@Column(name = "type_id")
	private Long id;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "typeId")
	private List<Scope> scope;
	/**
	 * can be null if name is descriptive enough
	 * 
	 * @return e.g. 'The lands board minutes where the sale of the said land was approved'
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return e.g. 'Lands board approval'
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the scope
	 */
	public List<Scope> getScope() {
		return scope;
	}
	/**
	 * @param scope the scope to set
	 */
	public void setScope(List<Scope> scope) {
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
}
