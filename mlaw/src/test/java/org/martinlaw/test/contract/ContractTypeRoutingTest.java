/**
 * 
 */
package org.martinlaw.test.contract;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012 Eric Njogu (kunadawa@gmail.com)
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
import org.martinlaw.bo.contract.ContractType;
import org.martinlaw.test.KewTestsBase;
import org.martinlaw.util.SearchTestCriteria;

/**
 * tests routing for {@link ContractType}
 * @author mugo
 *
 */
public class ContractTypeRoutingTest extends KewTestsBase {
	private Log log = LogFactory.getLog(getClass());

	@Test
	/**
	 * test that {@link ContractType} routes to final on submit
	 */
	public void testContractTypeRouting() {
		ContractType contractType = new ContractType();
		String name = "resale agreement";
		contractType.setName(name);
		try {
			testMaintenanceRoutingInitToFinal("ContractTypeMaintenanceDocument", contractType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log .error("test failed", e);
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
	 * test that a Contract maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testContractTypeMaintDocPerms() {
		testCreateMaintain(ContractType.class, "ContractTypeMaintenanceDocument");
	}
	
	/**
	 * test that ContractTypeDocument doc search works
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws WorkflowException 
	 */
	@Test
	public void testContractTypeRoutingDocSearch() throws WorkflowException, InstantiationException, IllegalAccessException {
		ContractType contractType = new ContractType();
		contractType.setName("permanent and pensionable");
		final String docType = "ContractTypeMaintenanceDocument";
		testMaintenanceRoutingInitToFinal(docType, contractType);
		
		ContractType contractType2 = new ContractType();
		contractType2.setName("supply of goods and services");
		testMaintenanceRoutingInitToFinal(docType, contractType2);
		
		// no document criteria given, so both documents should be found
		SearchTestCriteria crit1 = new SearchTestCriteria();
		crit1.setExpectedDocuments(2);
		// search for name
		SearchTestCriteria crit2 = new SearchTestCriteria();
		crit2.setExpectedDocuments(1);
		crit2.getFieldNamesToSearchValues().put("name", "permanent*");
		// search for non-existent name
		SearchTestCriteria crit3 = new SearchTestCriteria();
		crit3.setExpectedDocuments(0);
		crit3.getFieldNamesToSearchValues().put("name", "*temp*");
		
		List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
		crits.add(crit1);
		crits.add(crit2);
		crits.add(crit3);
		runDocumentSearch(crits, docType);
	}
}
