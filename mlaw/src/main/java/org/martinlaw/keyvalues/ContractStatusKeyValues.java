/**
 * 
 */
package org.martinlaw.keyvalues;

import java.util.List;

import org.kuali.rice.core.api.util.KeyValue;
import org.martinlaw.bo.Status;

/**
 * displays statuses whose {@link Status#getType} is {@link Status#CONTRACT_TYPE}
 * 
 * @author mugo
 *
 */
public class ContractStatusKeyValues  extends StatusKeyValuesBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2999340104639417876L;

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.keyvalues.KeyValuesFinder#getKeyValues()
	 */
	@Override
	public List<KeyValue> getKeyValues() {
		return super.getKeyValues(Status.CONTRACT_TYPE.getKey());	
	}
}
