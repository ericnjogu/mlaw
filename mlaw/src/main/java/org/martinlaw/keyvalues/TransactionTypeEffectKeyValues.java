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
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.martinlaw.bo.TransactionType;
import org.martinlaw.bo.TransactionType.TRANSACTION_EFFECT_ON_CONSIDERATION;

/**
 * displays effects of a transaction on the associated consideration
 * 
 * @author mugo
 *
 */
public class TransactionTypeEffectKeyValues extends KeyValuesBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1190039375358997018L;

	/**
	 * gets the time unit key values
	 * 
	 * <p>day, week, month, year</p>
	 * TODO convert the string labels to internationalized strings
	 * @param type - the desired type
	 * @return matching status as key values
	 */
	public List<KeyValue> getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		for (TRANSACTION_EFFECT_ON_CONSIDERATION effect: TransactionType.TRANSACTION_EFFECT_ON_CONSIDERATION.values()) {
			keyValues.add(new ConcreteKeyValue(effect.toString(), effect.toString()));
		}
        return keyValues;
	}
}
