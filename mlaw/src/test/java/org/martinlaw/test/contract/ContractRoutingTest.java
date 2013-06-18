/**
 * 
 */
package org.martinlaw.test.contract;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012, 2013 Eric Njogu (kunadawa@gmail.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.martinlaw.bo.contract.Consideration;
import org.martinlaw.bo.contract.Contract;
import org.martinlaw.test.KewTestsBase;
import org.martinlaw.util.SearchTestCriteria;

/**
 * tests routing for {@link Contract}
 * @author mugo
 *
 */
public class ContractRoutingTest extends KewTestsBase {
	private Log log = LogFactory.getLog(getClass());

	@Test
	/**
	 * test that ContractMaintenanceDocument routes to clerk then lawyer on submit
	 */
	public void testContractRouting() {
		Contract testContract = getTestUtils().getTestContract();
		try {
			testMaintenanceRoutingInitToFinal("ContractMaintenanceDocument", testContract);
		} catch (Exception e) {
			log.error("test failed", e);
			fail("test routing ContractMaintenanceDocument caused an exception " + e);
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("localReference", getTestUtils().getTestContractLocalReference());
		Collection<Contract> result = getBoSvc().findMatching(Contract.class, params);
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
	
	@Test
	/**
	 * test ContractMaintenanceDocument doc search
	 */
	public void testContractDocSearch() throws WorkflowException, InstantiationException, IllegalAccessException {
		Contract testContract = getTestUtils().getTestContract();
		final String docType = "ContractMaintenanceDocument";
		testMaintenanceRoutingInitToFinal(docType, testContract);
		
		Contract testContract2 = getTestUtils().getTestContract();
		testContract2.setName("salary and terms for temporary dev");
		testContract2.setLocalReference("MY/FIRM/CONTRACTS/2013/27");
		testMaintenanceRoutingInitToFinal(docType, testContract2);
		
		Contract testContract3 = getTestUtils().getTestContract();
		testContract3.setName("supply of veges");
		testContract3.setLocalReference("MY/FIRM/CONTRACTS/2013/21");
		testContract3.getConsiderations().add((Consideration) getTestUtils().getTestConsideration(Consideration.class));
		testMaintenanceRoutingInitToFinal(docType, testContract3);
		
		// no document criteria given, so both documents should be found
		SearchTestCriteria crit1 = new SearchTestCriteria();
		crit1.setExpectedDocuments(3);
		// search for exact local reference
		SearchTestCriteria crit2 = new SearchTestCriteria();
		crit2.setExpectedDocuments(1);
		crit2.getFieldNamesToSearchValues().put("localReference", testContract.getLocalReference());
		// search for name
		SearchTestCriteria crit3 = new SearchTestCriteria();
		crit3.setExpectedDocuments(1);
		crit3.getFieldNamesToSearchValues().put("name", "*temporary*");
		// search for local reference wild-card
		SearchTestCriteria crit5 = new SearchTestCriteria();
		crit5.setExpectedDocuments(2);
		crit5.getFieldNamesToSearchValues().put("localReference", "*2013*");
		
		List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
		crits.add(crit1);
		crits.add(crit2);
		crits.add(crit3);
		crits.add(crit5);
		getTestUtils().runDocumentSearch(crits, docType);
	}
}
