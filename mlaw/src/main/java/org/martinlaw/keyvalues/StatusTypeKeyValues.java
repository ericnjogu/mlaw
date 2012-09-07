/**
 * 
 */
package org.martinlaw.keyvalues;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.martinlaw.bo.Status;

/**
 * generates a list of key values for the status type - to be displayed in a select box on the status maintenance doc
 * 
 * @author mugo
 *
 */
public class StatusTypeKeyValues extends KeyValuesBase {
	Log log = LogFactory.getLog(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 9206469740259414962L;

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.keyvalues.KeyValuesFinder#getKeyValues()
	 */
	@Override
	public List<KeyValue> getKeyValues() {
		List<KeyValue> kv = new ArrayList<KeyValue>(3);
		Status status = new Status();
		Field[] fields = status.getClass().getFields();
		for (Field field: fields) {
			if (field.getType().isAssignableFrom(ConcreteKeyValue.class)) {
				try {
					kv.add((KeyValue) field.get(status));
				} catch (IllegalArgumentException e) {
					log.error("failed to get field value", e);
				} catch (IllegalAccessException e) {
					log.error("failed to get field value", e);
				}
			}
		}
		return kv;
	}

}
