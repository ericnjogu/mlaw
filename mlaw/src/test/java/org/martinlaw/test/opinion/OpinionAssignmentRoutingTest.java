/**
 * 
 */
package org.martinlaw.test.opinion;



import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.opinion.Assignee;
import org.martinlaw.bo.opinion.Assignment;
import org.martinlaw.test.BaseAssignmentRoutingTest;
import org.kuali.rice.test.BaselineTestCase;

/**
 * tests routing for {@link Assignment}
 * @author mugo
 *
 */
// @BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
@SuppressWarnings("unused")
public class OpinionAssignmentRoutingTest extends BaseAssignmentRoutingTest {
	@Test
	/**
	 * test that a Opinion assignment maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testOpinionAssignmentMaintDocPerms() {
		testCreateMaintain(Assignment.class, "OpinionAssignmentMaintenanceDocument");
	}
	
	/**
	 * tests Opinion assignment maintenance doc routing
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	@Test
	public void testOpinionAssignmentRouting() throws InstantiationException, IllegalAccessException {
		Assignment testAssignment = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		super.testAssignmentRouting(testAssignment, "OpinionAssignmentMaintenanceDocument");
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.BaseAssignmentRoutingTest#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-assignment-perms-roles.sql", ";").runSql();
	}
}
