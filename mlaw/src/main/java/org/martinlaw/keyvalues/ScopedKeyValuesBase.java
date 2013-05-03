package org.martinlaw.keyvalues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.ScopedKeyValue;
import org.martinlaw.bo.Scope;

public abstract class ScopedKeyValuesBase extends KeyValuesBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3514101768472447004L;

	public ScopedKeyValuesBase() {
		super();
	}
	
	/**
	 * gets every key value whose scope either includes the provided matter class name or has an empty scope (applies to all)
	 * 
	 * @param qualifiedMatterClassName - the class name of the matter for which we should retrieve statuses
	 * @return matching status as key values
	 */
	public List<KeyValue> getKeyValues(String qualifiedMatterClassName, Class<? extends BusinessObject> scopedClass) {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();

        @SuppressWarnings("unchecked")
		Collection<ScopedKeyValue> kvs = (Collection<ScopedKeyValue>) KRADServiceLocator.getBusinessObjectService().findAll(scopedClass);
        
        keyValues.add(new ConcreteKeyValue("", ""));
        for (ScopedKeyValue kv : kvs ) {
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

}