package org.martinlaw.bo;

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
 * a common parent used to associate people with court cases and conveyances
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