/**
 * 
 */
package org.martinlaw.test.conveyance;



import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.conveyance.Assignee;
import org.martinlaw.bo.conveyance.Assignment;
import org.martinlaw.test.BaseAssignmentRoutingTest;
import org.kuali.rice.test.BaselineTestCase;

/**
 * tests routing for {@link Assignment}
 * @author mugo
 *
 */
// @BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
@SuppressWarnings("unused")
public class ConveyanceAssignmentRoutingTest extends BaseAssignmentRoutingTest {
	@Test
	/**
	 * test that a conveyance assignment maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testConveyanceAssignmentMaintDocPerms() {
		testCreateMaintain(Assignment.class, "ConveyanceAssignmentMaintenanceDocument");
	}
	
	/**
	 * tests Conveyance assignment maintenance doc routing
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	@Test
	public void testConveyanceAssignmentRouting() throws InstantiationException, IllegalAccessException {
		Assignment testAssignment = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		super.testAssignmentRouting(testAssignment, "ConveyanceAssignmentMaintenanceDocument");
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.BaseAssignmentRoutingTest#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-assignment-perms-roles.sql", ";").runSql();
	}
}
