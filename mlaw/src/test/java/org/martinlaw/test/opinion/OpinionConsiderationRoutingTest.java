/**
 * 
 */
package org.martinlaw.test.opinion;

import org.martinlaw.bo.MatterConsideration;
import org.martinlaw.bo.opinion.Consideration;
import org.martinlaw.test.MatterConsiderationRoutingTestBase;

/**
 * tests {@link Consideration} routing
 * @author mugo
 *
 */
public class OpinionConsiderationRoutingTest extends MatterConsiderationRoutingTestBase {

	/* (non-Javadoc)
	 * @see org.martinlaw.test.MatterConsiderationRoutingTestBase#getDataObjectClass()
	 */
	@Override
	public Class<? extends MatterConsideration> getDataObjectClass() {
		return Consideration.class;
	}

}
