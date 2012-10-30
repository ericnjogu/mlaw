/**
 * 
 */
package org.martinlaw.keyvalues;

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
