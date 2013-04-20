/**
 * 
 */
package org.martinlaw.test.conveyance;

import org.martinlaw.bo.MatterConsideration;
import org.martinlaw.bo.conveyance.Consideration;
import org.martinlaw.test.MatterConsiderationRoutingTestBase;

/**
 * tests {@link Consideration} routing 
 * @author mugo
 *
 */
public class ConveyanceConsiderationRoutingTest extends
		MatterConsiderationRoutingTestBase {

	/* (non-Javadoc)
	 * @see org.martinlaw.test.MatterConsiderationRoutingTestBase#getDataObjectClass()
	 */
	@Override
	public Class<? extends MatterConsideration> getDataObjectClass() {
		return Consideration.class;
	}

}
