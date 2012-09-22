package org.martinlaw.test.contract;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.MatterAssignment;
import org.martinlaw.bo.contract.Assignment;
import org.martinlaw.test.KewTestsBase;

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
			fail("test routing ContractAssignmentMaintenanceDocument caused an exception");
		}
		// confirm that BO was saved to DB
		Collection<Assignment> result = getBoSvc().findAll(Assignment.class);
		assertEquals("number of contract assignments was not the expected number", 1, result.size());
		for (Assignment assignment: result) {
			getTestUtils().testContractAssignmentFields(assignment);
		}
	
	}

	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		// needed for the one-one relationship with contract and contract's relationships with contract type and status
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-type-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-test-data.sql", ";").runSql();
	}

}