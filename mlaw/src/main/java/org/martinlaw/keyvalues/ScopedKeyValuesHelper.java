/**
 * 
 */
package org.martinlaw.keyvalues;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.ScopedKeyValue;
import org.martinlaw.bo.Scope;

/**
 * holds common logic for retrieving objects that are scoped to specific matters e.g. event type, status etc
 * @author mugo
 *
 */
public class ScopedKeyValuesHelper {
	private BusinessObjectService businessObjectService;

	/**
	 * gets every key value whose scope either includes the provided matter class name or has an empty scope (applies to all)
	 * 
	 * @param qualifiedMatterClassName - the class name of the matter for which we should retrieve statuses
	 * @return matching status as key values
	 */
	public List<KeyValue> getKeyValues(String qualifiedMatterClassName, Class<? extends BusinessObject> scopedClass) {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();

        @SuppressWarnings("unchecked")
		Collection<BusinessObject> kvs = (Collection<BusinessObject>) getBusinessObjectService().findAll(scopedClass);
        // to be configured via xml using blankOption
        /*keyValues.add(new ConcreteKeyValue("", ""));*/
        for (BusinessObject bo : kvs ) {
        	ScopedKeyValue kv = (ScopedKeyValue)bo;
        	if (kv.getScope().isEmpty()) {
        		keyValues.add(new ConcreteKeyValue(kv.getKey(), kv.getValue()));
        	} else {
        		for (Scope scope: kv.getScope()) {
        			if (StringUtils.equals(qualifiedMatterClassName, scope.getQualifiedClassName())) {
        				keyValues.add(new ConcreteKeyValue(kv.getKey(), kv.getValue()));
        				break;
        			}
        		}
        	}
        }

        return keyValues;
	}

	/**
	 * @return the krad implementation or a mock
	 */
	protected BusinessObjectService getBusinessObjectService() {
		if (businessObjectService == null) {
			businessObjectService = KRADServiceLocator.getBusinessObjectService();
		}
		return businessObjectService;
	}

	/**
	 * @param businessObjectService the businessObjectService to set
	 */
	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}
}
