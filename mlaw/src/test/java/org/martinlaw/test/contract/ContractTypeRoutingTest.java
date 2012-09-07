/**
 * 
 */
package org.martinlaw.test.contract;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.test.SQLDataLoader;
import org.kuali.rice.test.lifecycles.KEWXmlDataLoaderLifecycle;
import org.martinlaw.bo.contract.ContractType;
import org.martinlaw.test.KewTestsBase;

/**
 * tests routing for {@link ContractType}
 * @author mugo
 *
 */
public class ContractTypeRoutingTest extends KewTestsBase {
	@Test
	/**
	 * test that ConveyanceAnnexTypeDocument routes to final on submit
	 */
	public void testContractTypeRouting() {
		ContractType contractType = new ContractType();
		String name = "resale agreement";
		contractType.setName(name);
		try {
			testMaintenanceRoutingInitToFinal("ContractTypeMaintenanceDocument", contractType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("test failed", e);
			fail("test routing ContractTypeMaintenanceDocument caused an exception");
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		Collection<ContractType> result = getBoSvc().findMatching(ContractType.class, params);
		assertEquals(1, result.size());
	}
	
	@Test
	/**
	 * test that a conveyance maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testContractTypeMaintDocPerms() {
		testCreateMaintain(ContractType.class, "ContractTypeMaintenanceDocument");
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#getSuiteLifecycles()
	 */
	/**
	 * provide the document type definition file.
	 */
	@Override
	protected List<Lifecycle> getSuiteLifecycles() {
		List<Lifecycle> suiteLifecycles = super.getSuiteLifecycles();
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/contractType.xml"));
		return suiteLifecycles;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-type-perms-roles.sql", ";").runSql();
	}
}
