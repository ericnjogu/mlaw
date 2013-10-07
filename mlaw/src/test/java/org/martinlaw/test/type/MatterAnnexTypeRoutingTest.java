/**
 * 
 */
package org.martinlaw.test.type;

import static org.junit.Assert.fail;

import org.kuali.rice.kew.api.exception.WorkflowException;
import org.martinlaw.bo.BaseDetail;
import org.martinlaw.bo.MatterAnnexType;

/**
 * tests {@link MatterAnnexType} routing
 * @author mugo
 *
 */
public class MatterAnnexTypeRoutingTest extends BaseDetailRoutingTestBase {

	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#getDocTypeName()
	 */
	@Override
	public String getDocTypeName() {
		return "MatterAnnexTypeMaintenanceDocument";
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.type.BaseDetailRoutingTestBase#getDataObjectClass()
	 */
	@Override
	public Class<? extends BaseDetail> getDataObjectClass() {
		return MatterAnnexType.class;
	}
	
	/**
	 * a copy of {@link org.martinlaw.test.KewTestsBase#testInitiatorFYI()} since the parent class does nothing
	 */
	public void testInitiatorFYI() {
		try {
			testWorkflowRoutingOnly_initiator_FYI(getDocTypeName() + "Test");
		} catch (WorkflowException e) {
			fail(e.getMessage());
		}
	}

}
