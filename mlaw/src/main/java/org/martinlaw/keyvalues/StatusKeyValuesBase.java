/**
 * 
 */
package org.martinlaw.keyvalues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.bo.Status;

/**
 * displays statuses whose {@link Status#getType} is provided in {@link #getKeyValues(String)}
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
	 * gets the status whose type matches the one provided
	 * 
	 * @param type - the desired type
	 * @return matching status as key values
	 */
	public List<KeyValue> getKeyValues(String type) {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();

        Collection<Status> bos = KRADServiceLocator.getBusinessObjectService().findAll( Status.class );
        
        keyValues.add(new ConcreteKeyValue("", ""));
        for ( Status status : bos ) {
        	if (status.getType().equalsIgnoreCase(type) || status.getType().equalsIgnoreCase(Status.ANY_TYPE.getKey())) {
        		keyValues.add(new ConcreteKeyValue(status.getId().toString(), status.getStatus()));
        	}
        }

        return keyValues;
	}
}
