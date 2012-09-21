/**
 * 
 */
package org.martinlaw.test.contract;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.contract.ContractAssignment;
import org.martinlaw.test.KewTestsBase;

/**
 * tests routing for {@link ContractAssignment}
 * @author mugo
 *
 */
public class ContractAssignmentRoutingTest extends KewTestsBase {
	@Test
	/**
	 * test that ContractAssignmentMaintenanceDocument routes to clerk then lawyer on submit
	 */
	public void testContractAssignmentTypeRouting() {
		ContractAssignment testContractAssignment = getTestUtils().getTestContractAssignment();
		try {
			testMaintenanceRouting("ContractAssignmentMaintenanceDocument", testContractAssignment);
		} catch (Exception e) {
			log.error("test failed", e);
			fail("test routing ContractAssignmentMaintenanceDocument caused an exception");
		}
		// confirm that BO was saved to DB
		Collection<ContractAssignment> result = getBoSvc().findAll(ContractAssignment.class);
		assertEquals("number of contract assignments was not the expected number", 1, result.size());
		for (ContractAssignment contractAssignment: result) {
			getTestUtils().testContractAssignmentFields(contractAssignment);
		}

	}
	
	@Test
	/**
	 * test that a contract maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testContractAssignmentTypeMaintDocPerms() {
		testCreateMaintain(ContractAssignment.class, "ContractAssignmentMaintenanceDocument");
	}
	
	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-assignment-perms-roles.sql", ";").runSql();
	}
}
