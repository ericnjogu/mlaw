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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * gives information on which matter(s) a status applies to
 * type (one) -> Scope (many)
 * <p>a type could be matter type, event type, etc</p>
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_type_scope_t")
public class Scope extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7657417655869201305L;
	@Column(name = "qualified_class_name", nullable = false, length = 100)
	private String qualifiedClassName;
	@Id
	@Column(name = "scope_id")
	private Long id;
	@Column(name = "type_id", nullable = false)
	private Long typeId;

	public Scope() {
		super();
	}

	/**
	 * initialize scope with qualified class name
	 * @param qualifiedClassName
	 */
	public Scope(String qualifiedClassName) {
		this.qualifiedClassName = qualifiedClassName;
	}

	/**
	 * the qualified class name of the matter
	 * @return the qualifiedClassName
	 */
	public String getQualifiedClassName() {
		return qualifiedClassName;
	}

	/**
	 * @param qualifiedClassName the qualifiedClassName to set
	 */
	public void setQualifiedClassName(String qualifiedClassName) {
		this.qualifiedClassName = qualifiedClassName;
	}

	/**
	 * @return the simple matter class name for display to users
	 */
	public String getSimpleClassName() {
		if (getQualifiedClassName() == null) {
			return "";
		} else {
			return getQualifiedClassName().substring(getQualifiedClassName().lastIndexOf('.') + 1);
		}
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
	 * the foreign key that links to the related status, matter type, event type etc
	 * @return the statusId
	 */
	public Long getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

}