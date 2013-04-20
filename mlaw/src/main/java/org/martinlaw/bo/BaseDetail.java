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


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * a super class that holds some common fields useful for creating types
 * 
 * @author mugo
 */
@MappedSuperclass
public abstract class BaseDetail extends PersistableBusinessObjectBase{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8844219522196909942L;
	@Column(name="name", length=100, nullable=false) 
	private String name;
	@Column(name = "description", length = 250)
	private String description;
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
	 * child classes need to implement this for retrieving the primary key
	 * @return the id
	 */
	public abstract Long getId();
}
