/**
 * 
 */
package org.martinlaw.test.courtcase;



import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.courtcase.Assignee;
import org.martinlaw.bo.courtcase.Assignment;
import org.martinlaw.test.BaseAssignmentRoutingTest;
import org.kuali.rice.test.BaselineTestCase;

/**
 * tests routing for {@link Assignment}
 * @author mugo
 *
 */
// @BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
@SuppressWarnings("unused")
public class CourtCaseAssignmentRoutingTest extends BaseAssignmentRoutingTest {
	@Test
	/**
	 * test that a CourtCase assignment maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testCourtCaseAssignmentMaintDocPerms() {
		testCreateMaintain(Assignment.class, "CourtCaseAssignmentMaintenanceDocument");
	}
	
	/**
	 * tests CourtCase assignment maintenance doc routing
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	@Test
	public void testCourtCaseAssignmentRouting() throws InstantiationException, IllegalAccessException {
		Assignment testAssignment = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		super.testAssignmentRouting(testAssignment, "CourtCaseAssignmentMaintenanceDocument");
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.BaseAssignmentRoutingTest#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-assignment-perms-roles.sql", ";").runSql();
	}
}
