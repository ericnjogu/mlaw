/**
 * 
 */
package org.martinlaw.keyvalues;

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
