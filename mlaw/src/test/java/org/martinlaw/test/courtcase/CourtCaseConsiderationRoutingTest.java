/**
 * 
 */
package org.martinlaw.test.courtcase;

import org.martinlaw.bo.MatterConsideration;
import org.martinlaw.bo.courtcase.Consideration;
import org.martinlaw.test.MatterConsiderationRoutingTestBase;

/**
 * tests {@link Consideration} routing
 * @author mugo
 *
 */
public class CourtCaseConsiderationRoutingTest extends
		MatterConsiderationRoutingTestBase {

	/* (non-Javadoc)
	 * @see org.martinlaw.test.MatterConsiderationRoutingTestBase#getDataObjectClass()
	 */
	@Override
	public Class<? extends MatterConsideration> getDataObjectClass() {
		return Consideration.class;
	}

}
