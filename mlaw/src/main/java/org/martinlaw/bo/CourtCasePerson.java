/**
 * 
 */
package org.martinlaw.bo;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

/**
 * @author mugo
 *serve as a common parent to witness and client
 */
@MappedSuperclass
public abstract class CourtCasePerson extends CourtCaseCollectionBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7290495005716959116L;
	@Column(name="principal_name", length=30, nullable=false)
	private String principalName;
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
	/* (non-Javadoc)
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> propMap = super.toStringMapper();
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
	
	/** the client**/
	@Transient
	private Person person;
	
	/**
	 * @param client the client to set
	 */
	public void setPerson(Person client) {
		this.person = client;
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
