/**
 * 
 */
package org.martinlaw.bo.utils;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

/**
 * holds various utility methods for working with {@
 * @author mugo
 *
 */
public class PersonUtils implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9221763716638272836L;

	/**
	 * retrieve a person from the database
	 * @param existingPerson - only retrieve if the existing person has a different principal name
	 * @param principalName - the principal name of the desired person
	 * @return the person if they exist, or a new Person object
	 */
	public Person getPerson(Person existingPerson, String principalName) {
		if ((existingPerson == null) || !StringUtils.equals(existingPerson.getPrincipalName(), principalName)) {
			existingPerson = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(principalName);
	
	        if (existingPerson == null) {
	            try {
	                existingPerson = KimApiServiceLocator.getPersonService().getPersonImplementationClass().newInstance();
	            } catch (Exception e) {
	                throw new RuntimeException(e);
	            }
	        }
	    }
		return existingPerson;
	}
}
