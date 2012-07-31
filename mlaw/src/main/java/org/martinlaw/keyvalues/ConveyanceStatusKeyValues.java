/**
 * 
 */
package org.martinlaw.keyvalues;

import java.util.List;

import org.kuali.rice.core.api.util.KeyValue;
import org.martinlaw.bo.Status;

/**
 * displays statuses whose {@link Status#getType} is {@link Status#CONVEYANCE_TYPE}
 * 
 * @author mugo
 *
 */
public class ConveyanceStatusKeyValues  extends StatusKeyValuesBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6437266342673833765L;

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.keyvalues.KeyValuesFinder#getKeyValues()
	 */
	@Override
	public List<KeyValue> getKeyValues() {
		return super.getKeyValues(Status.CONVEYANCE_TYPE.getKey());	
	}
}
