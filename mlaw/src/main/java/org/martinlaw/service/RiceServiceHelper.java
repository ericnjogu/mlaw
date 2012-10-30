/**
 * 
 */
package org.martinlaw.service;

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
