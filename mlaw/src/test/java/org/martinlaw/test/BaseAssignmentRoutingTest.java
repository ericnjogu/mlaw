package org.martinlaw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.MatterAssignment;

public abstract class BaseAssignmentRoutingTest extends KewTestsBase {
	/**
	 * default constructor
	 */
	public BaseAssignmentRoutingTest() {
		super();
	}
	
	/**
	 * test routing for a child of {@link MatterAssignment}
	 * @param testAssignment - the BO to use in the maintenance doc
	 * @param docType -  the doc type to use
	 */
	public void testAssignmentRouting(MatterAssignment<?, ?> testAssignment, String docType) {
		// Assignment testAssignment = getTestUtils().getTestContractAssignment();
		try {
			testMaintenanceRouting(docType, testAssignment);
		} catch (Exception e) {
			log.error("test failed", e);
			fail("test routing ConveyanceAssignmentMaintenanceDocument caused an exception");
		}
		// confirm that BO was saved to DB
		@SuppressWarnings("rawtypes")
		Collection<? extends MatterAssignment> result = getBoSvc().findAll(testAssignment.getClass());
		assertEquals("number of assignments was not the expected number", 1, result.size());
		for (@SuppressWarnings("rawtypes") MatterAssignment assignment: result) {
			getTestUtils().testAssignmentFields(assignment);
		}
	
	}

	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
	}

}