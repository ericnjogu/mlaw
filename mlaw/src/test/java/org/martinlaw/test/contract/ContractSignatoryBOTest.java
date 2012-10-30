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
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.contract.ContractSignatory;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link ContractSignatory}
 * 
 * @author mugo
 * 
 */
public class ContractSignatoryBOTest extends ContractBoTestBase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader(
				"classpath:org/martinlaw/scripts/contract-signatory-test-data.sql",
				";").runSql();
	}

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testContractSignatoryNullableFields() {
		ContractSignatory contractSignatory = new ContractSignatory();
		getBoSvc().save(contractSignatory);
	}

	@Test
	/**
	 * test that the ContractSignatory is loaded into the data dictionary
	 */
	public void testContractSignatoryAttributes() {
		testBoAttributesPresent(ContractSignatory.class.getCanonicalName());
		Class<ContractSignatory> dataObjectClass = ContractSignatory.class;
		verifyInquiryLookup(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from date inserted via sql
	 */
	public void testContractSignatoryRetrieve() {
		// retrieve object populated via sql script
		ContractSignatory contractSignatory = getBoSvc().findBySinglePrimaryKey(
				ContractSignatory.class, 1001l);
		assertNotNull(contractSignatory);
		assertEquals("en", contractSignatory.getPrincipalName());
	}

	@Test
	/**
	 * test CRUD for {@link ContractSignatory}
	 */
	public void testContractSignatoryCRUD() {
		// C
		ContractSignatory contractSignatory = new ContractSignatory();
		String name = "am";
		contractSignatory.setPrincipalName(name);
		contractSignatory.setContractId(1001l);
		getBoSvc().save(contractSignatory);
		// R
		contractSignatory.refresh();
		assertEquals("principal name does not match", name, contractSignatory.getPrincipalName());
		// U
		name = "zn";
		contractSignatory.setPrincipalName(name);
		contractSignatory.refresh();
		assertEquals("principal name does not match", name, contractSignatory.getPrincipalName());
		// D
		getBoSvc().delete(contractSignatory);
		assertNull(getBoSvc().findBySinglePrimaryKey(ContractSignatory.class,
				contractSignatory.getId()));
	}
}
