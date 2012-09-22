/**
 * 
 */
package org.martinlaw.test.contract;



import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.contract.Assignee;
import org.martinlaw.bo.contract.Assignment;

/**
 * tests routing for {@link Assignment}
 * @author mugo
 *
 */
public class ContractAssignmentRoutingTest extends BaseAssignmentRoutingTest {
	@Test
	/**
	 * test that a contract maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testContractAssignmentTypeMaintDocPerms() {
		testCreateMaintain(Assignment.class, "ContractAssignmentMaintenanceDocument");
	}
	
	/**
	 * tests contract assignment maintenance doc routing
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	@Test
	public void testContractAssignmentTypeRouting() throws InstantiationException, IllegalAccessException {
		Assignment testAssignment = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		super.testAssignmentRouting(testAssignment, "ContractAssignmentMaintenanceDocument");
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.contract.BaseAssignmentRoutingTest#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-assignment-perms-roles.sql", ";").runSql();
	}
}
