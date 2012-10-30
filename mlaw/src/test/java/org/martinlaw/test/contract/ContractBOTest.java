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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.test.SQLDataLoader;
import org.kuali.rice.test.lifecycles.KEWXmlDataLoaderLifecycle;
import org.martinlaw.bo.contract.ClientFee;
import org.martinlaw.bo.contract.Contract;
import org.martinlaw.bo.contract.ContractConsideration;
import org.martinlaw.bo.contract.ContractDuration;
import org.martinlaw.bo.contract.ContractParty;
import org.martinlaw.bo.contract.ContractSignatory;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link Contract}
 * 
 * @author mugo
 * 
 */
public class ContractBOTest extends ContractBoTestBase {

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testContractTypeNullableFields() {
		Contract contract = new Contract();
		getBoSvc().save(contract);
	}

	@Test
	/**
	 * test that the ContractType is loaded into the data dictionary
	 */
	public void testContractTypeAttributes() {
		testBoAttributesPresent(Contract.class.getCanonicalName());
		Class<Contract> dataObjectClass = Contract.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from date inserted via sql
	 */
	public void testContractRetrieve() {
		// retrieve object populated via sql script
		Contract contract = getBoSvc().findBySinglePrimaryKey(
				Contract.class, 1001l);
		assertNotNull("contract should exist in database", contract);
		assertEquals("contract name not the expected value", "buru ph2 h24", contract.getName());
		assertNotNull("contract type should not be null", contract.getType());
		assertEquals("contract type name does not match", "rent agreement", contract.getType().getName());
		assertNotNull("contract consideration should not be null", contract.getContractConsideration());
		assertEquals("contract consideration amount differs", 0, 
				contract.getContractConsideration().getAmount().compareTo(new BigDecimal(41000l)));
		assertNotNull("contract duration should not be null" ,contract.getContractDuration());
		assertNotNull("contract duration start date should not be null", contract.getContractDuration().getStartDate());
		assertNotNull("contract duration end date should not be null", contract.getContractDuration().getEndDate());
		getTestUtils().testAssignees(contract.getAssignees());
		
		List<ClientFee> fees = contract.getFees();
		getTestUtils().testClientFeeList(fees);
		
		getTestUtils().testWorkList(contract.getWork());
	}

	@Test
	/**
	 * test CRUD for {@link Contract}
	 */
	public void testContractCRUD() {
		// C
		Contract contract = getTestUtils().getTestContract();
		
		getBoSvc().save(contract);
		// R
		contract.refresh();
		getTestUtils().testContractFields(contract);
		// U
		String serviceOffered = "flat 3f2";
		contract.setServiceOffered(serviceOffered);
		contract.refresh();
		assertEquals("contract svc offered does not match after update", serviceOffered, contract.getServiceOffered());
		// D
		getBoSvc().delete(contract);
		assertNull("contract should not exist", getBoSvc().findBySinglePrimaryKey(Contract.class, contract.getId()));
		assertNull("consideration should not exist", getBoSvc().findBySinglePrimaryKey(ContractConsideration.class, contract.getId()));
		assertNull("duration should not exist", getBoSvc().findBySinglePrimaryKey(ContractDuration.class, contract.getId()));
		
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("contractId", contract.getId());
		assertEquals("contract signatories should have been deleted", 0, getBoSvc().findMatching(ContractSignatory.class, criteria).size());
		assertEquals("contract parties should have been deleted", 0, getBoSvc().findMatching(ContractParty.class, criteria).size());
	}
	
	@Test
	/**
	 * tests that the document type is loaded ok
	 */
	public void testContractDocType() {
		assertNotNull("document type should not be null", getDocTypeSvc().findByName("ContractMaintenanceDocument"));
	}

	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#getSuiteLifecycles()
	 */
	/**
	 * provide the document type definition file and the supporting files groups > users.
	 */
	@Override
	protected List<Lifecycle> getSuiteLifecycles() {
		List<Lifecycle> suiteLifecycles = super.getSuiteLifecycles();
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/kim/users.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/kim/groups.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/rules/rule-templates.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/contract.xml"));
		return suiteLifecycles;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.contract.ContractBoTestBase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		// loads contract work
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-work-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-fee-test-data.sql", ";").runSql();
	}

}
