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


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.martinlaw.bo.contract.ContractDuration;

/**
 * displays time units derived from {@link Calendar} to be used to specify the value of {@link ContractDuration#getDurationTimeUnit()}
 * 
 * @author mugo
 *
 */
public class TimeUnitKeyValues extends KeyValuesBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6437266342673833765L;

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
        keyValues.add(new ConcreteKeyValue("", ""));
        keyValues.add(new ConcreteKeyValue(String.valueOf(Calendar.DAY_OF_YEAR), "Day"));
        keyValues.add(new ConcreteKeyValue(String.valueOf(Calendar.WEEK_OF_YEAR), "Week"));
        keyValues.add(new ConcreteKeyValue(String.valueOf(Calendar.MONTH), "Month"));
        keyValues.add(new ConcreteKeyValue(String.valueOf(Calendar.YEAR), "Year"));
        return keyValues;
	}
}
