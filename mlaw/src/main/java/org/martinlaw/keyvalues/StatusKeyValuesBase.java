/**
 * 
 */
package org.martinlaw.keyvalues;

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
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.bo.Status;
import org.martinlaw.bo.StatusScope;

/**
 * displays statuses whose {@link Status#getScope()} is either empty or includes the specific matter (via the class name)
 * 
 * @author mugo
 *
 */
public abstract class StatusKeyValuesBase extends KeyValuesBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6437266342673833765L;

	/**
	 * gets every status whose scope either includes the provided matter class name or has an empty scope (applies to all)
	 * 
	 * @param qualifiedMatterClassName - the class name of the matter for which we should retrieve statuses
	 * @return matching status as key values
	 */
	public List<KeyValue> getKeyValues(String qualifiedMatterClassName) {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();

        Collection<Status> bos = KRADServiceLocator.getBusinessObjectService().findAll( Status.class );
        
        keyValues.add(new ConcreteKeyValue("", ""));
        for ( Status status : bos ) {
        	if (status.getScope().isEmpty()) {
        		keyValues.add(new ConcreteKeyValue(status.getId().toString(), status.getStatus()));
        	} else {
        		for (StatusScope scope: status.getScope()) {
        			if (StringUtils.equals(qualifiedMatterClassName, scope.getQualifiedClassName())) {
        				keyValues.add(new ConcreteKeyValue(status.getId().toString(), status.getStatus()));
        				break;
        			}
        		}
        	}
        }

        return keyValues;
	}
}
