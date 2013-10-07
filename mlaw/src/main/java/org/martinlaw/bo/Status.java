/**
 * 
 */
package org.martinlaw.bo;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012, 2013 Eric Njogu (kunadawa@gmail.com)
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

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.martinlaw.ScopedKeyValue;

/**
 * represents a matter status
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_status_t")
public class Status extends PersistableBusinessObjectBase implements ScopedKeyValue {

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "typeId")
	private List<StatusScope> scope;
	
	/**
	 * initializes class with with default values for the fields
	 * 
	 * @param id - the primary key
	 * @param status - the description
	 */
	public Status(Long id, String status) {
		this();
		this.id = id;
		this.status = status;
	}
	
	/**
	 * default constructor
	 */
	public Status() {
		super();
		setScope(new ArrayList<StatusScope>());
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 2361877298799195456L;
	/**
	 * the primary key
	 */
	@Id
	@Column(name="status_id")
	private Long id;
	/**
	 * e.g. 'pending hearing date'
	 */
	@Column(name="status", length=100, nullable=false)
	private String status;
	
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the scope
	 */
	public List<StatusScope> getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(List<StatusScope> scope) {
		this.scope = scope;
	}

	@Override
	public String getKey() {
		return String.valueOf(getId());
	}

	@Override
	public String getValue() {
		return getStatus();
	}

}
