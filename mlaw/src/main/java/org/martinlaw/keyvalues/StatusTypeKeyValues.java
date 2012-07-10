/**
 * 
 */
package org.martinlaw.keyvalues;

import java.util.ArrayList;
import java.util.List;

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
		kv.add(Status.ANY_TYPE);
		kv.add(Status.CONVEYANCE_TYPE);
		kv.add(Status.COURT_CASE_TYPE);
		return kv;
	}

}
