/**
 * 
 */
package org.martinlaw.service;

import java.io.Serializable;

import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;

/**
 * Holds references to rice services in a mock friendly way
 * 
 * no static methods
 * 
 * @author mugo
 *
 */
public class RiceServiceHelper implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7253109023620643364L;

	/**
	 * enables the boSvc to be mocked
	 * 
	 * @return the business object service
	 */
	public BusinessObjectService getBusinessObjectService() {
			return KRADServiceLocator.getBusinessObjectService();
	}
}
