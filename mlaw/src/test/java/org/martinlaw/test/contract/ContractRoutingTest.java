/**
 * 
 */
package org.martinlaw.test.contract;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.contract.Contract;
import org.martinlaw.test.KewTestsBase;

/**
 * tests routing for {@link Contract}
 * @author mugo
 *
 */
public class ContractRoutingTest extends KewTestsBase {
	@Test
	/**
	 * test that ContractMaintenanceDocument routes to clerk then lawyer on submit
	 */
	public void testContractTypeRouting() {
		Contract testContract = getTestUtils().getTestContract();
		try {
			testMaintenanceRouting("ContractMaintenanceDocument", testContract);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("test failed", e);
			fail("test routing ContractMaintenanceDocument caused an exception");
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("localReference", getTestUtils().getTestContractLocalReference());
		Collection<Contract> result = getBoSvc().findAll(Contract.class);
		assertEquals(1, result.size());
		for (Contract contract: result) {
			getTestUtils().testContractFields(contract);
		}

	}
	
	@Test
	/**
	 * test that a contract maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testContractTypeMaintDocPerms() {
		testCreateMaintain(Contract.class, "ContractMaintenanceDocument");
	}
	
	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-perms-roles.sql", ";").runSql();
		// for the status id
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
		// for the contract type
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-type-test-data.sql", ";").runSql();
	}
}
