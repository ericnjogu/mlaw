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
import javax.persistence.MappedSuperclass;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * holds common fields for scope objects
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class Scope extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7657417655869201305L;
	@Column(name = "qualified_class_name", nullable = false, length = 100)
	private String qualifiedClassName;

	public Scope() {
		super();
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

}