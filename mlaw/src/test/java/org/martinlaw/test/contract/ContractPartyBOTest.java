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

import org.junit.Test;
import org.martinlaw.bo.contract.ContractParty;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link ContractParty}
 * 
 * @author mugo
 * 
 */
public class ContractPartyBOTest extends MartinlawTestsBase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-party-test-data.sql", ";").runSql();
	}*/

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testContractPartyNullableFields() {
		ContractParty ContractParty = new ContractParty();
		getBoSvc().save(ContractParty);
	}

	@Test
	/**
	 * test that the ContractParty is loaded into the data dictionary
	 */
	public void testContractPartyAttributes() {
		testBoAttributesPresent(ContractParty.class.getCanonicalName());
		Class<ContractParty> dataObjectClass = ContractParty.class;
		verifyInquiryLookup(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from date inserted via sql
	 */
	public void testContractPartyRetrieve() {
		// retrieve object populated via sql script
		ContractParty ContractParty = getBoSvc().findBySinglePrimaryKey(
				ContractParty.class, 1001l);
		assertNotNull(ContractParty);
		assertEquals("en", ContractParty.getPrincipalName());
	}

	@Test
	/**
	 * test CRUD for {@link ContractParty}
	 */
	public void testContractPartyCRUD() {
		// C
		ContractParty contractParty = new ContractParty();
		String name = "am";
		contractParty.setPrincipalName(name);
		contractParty.setContractId(1001l);
		getBoSvc().save(contractParty);
		// R
		contractParty.refresh();
		assertEquals("principal name does not match", name, contractParty.getPrincipalName());
		// U
		name = "zn";
		contractParty.setPrincipalName(name);
		contractParty.refresh();
		assertEquals("principal name does not match", name, contractParty.getPrincipalName());
		// D
		getBoSvc().delete(contractParty);
		assertNull(getBoSvc().findBySinglePrimaryKey(ContractParty.class,
				contractParty.getId()));
	}
}
