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


import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
/**
 * a common parent used to associate people with matters
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MartinlawPerson extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1900404269057393848L;
	@Column(name = "principal_name", length = 30, nullable = false)
	private String principalName;
	/** the client**/
	@Transient
	private Person person;

	/**
	 * @return the principalName
	 */
	public String getPrincipalName() {
		return principalName;
	}

	/**
	 * @param principalName the principalName to set
	 */
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	//@Override
	protected Map<String, Object> toStringMapper() {
		Map<String, Object> propMap = new LinkedHashMap<String, Object>();
		propMap.put("principalName", getPrincipalName());
		return propMap;
	}

	/**
	 * @param id the id to set
	 */
	public abstract void setId(Long id);

	/**
	 * @return the id
	 */
	public abstract Long getId();

	public MartinlawPerson() {
		super();
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * @return the client
	 */
	public org.kuali.rice.kim.api.identity.Person getPerson() {
		if ((person == null) || !StringUtils.equals(person.getPrincipalName(), getPrincipalName())) {
			person = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(getPrincipalName());
	
	        if (person == null) {
	            try {
	                person = KimApiServiceLocator.getPersonService().getPersonImplementationClass().newInstance();
	            } catch (Exception e) {
	                throw new RuntimeException(e);
	            }
	        }
	    }
		return person;
	}

}