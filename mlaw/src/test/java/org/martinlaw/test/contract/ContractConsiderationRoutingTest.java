/**
 * 
 */
package org.martinlaw.test.contract;

import org.martinlaw.bo.MatterConsideration;
import org.martinlaw.bo.contract.Consideration;
import org.martinlaw.test.MatterConsiderationRoutingTestBase;

/**
 * tests {@link Consideration} routing
 * @author mugo
 *
 */
public class ContractConsiderationRoutingTest extends MatterConsiderationRoutingTestBase {

	/* (non-Javadoc)
	 * @see org.martinlaw.test.MatterConsiderationRoutingTestBase#getDataObjectClass()
	 */
	@Override
	public Class<? extends MatterConsideration> getDataObjectClass() {
		return Consideration.class;
	}

}
