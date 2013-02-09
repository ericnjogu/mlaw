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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * represents a matter status
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_status_t")
public class Status extends PersistableBusinessObjectBase {
	//TODO could possibly become a full blown maintainable - status type. How will we know which type has been assigned to which matter type?
	//TODO use locale props to display strings
	public static final ConcreteKeyValue ANY_TYPE = new ConcreteKeyValue("ANY_TYPE", "applies to any matter");
	public static final ConcreteKeyValue COURT_CASE_TYPE = new ConcreteKeyValue("COURT_CASE_TYPE", "applies to court cases");
	public static final ConcreteKeyValue CONVEYANCE_TYPE = new ConcreteKeyValue("CONVEYANCE_TYPE", "applies to conveyancing");
	public static final ConcreteKeyValue CONTRACT_TYPE = new ConcreteKeyValue("CONTRACT_TYPE", "applies to contracts");
	public static final ConcreteKeyValue OPINION_TYPE = new ConcreteKeyValue("OPINION_TYPE", "applies to opinions");
	
	/**
	 * initializes class with with default values for the fields
	 * 
	 * @param id - the primary key
	 * @param status - the description
	 */
	public Status(Long id, String status) {
		this.id = id;
		this.status = status;
	}
	
	/**
	 * default constructor
	 */
	public Status() {
		super();
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
	 * the type of the status - the key value of {@link #ANY_TYPE}, {@link #CONVEYANCE_TYPE} or {@link #COURT_CASE_TYPE}
	 */
	@Column(name="type", length=50, nullable=false)
	private String type;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
