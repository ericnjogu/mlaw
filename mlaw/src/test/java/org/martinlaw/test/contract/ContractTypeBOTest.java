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
import org.martinlaw.bo.contract.ContractType;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link ContractType}
 * 
 * @author mugo
 * 
 */
public class ContractTypeBOTest extends MartinlawTestsBase {

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testContractTypeNullableFields() {
		ContractType contractType = new ContractType();
		getBoSvc().save(contractType);
	}

	@Test
	/**
	 * test that the ContractType is loaded into the data dictionary
	 */
	public void testContractTypeAttributes() {
		testBoAttributesPresent(ContractType.class.getCanonicalName());
		Class<ContractType> dataObjectClass = ContractType.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from date inserted via sql
	 */
	public void testContractTypeRetrieve() {
		// retrieve object populated via sql script
		ContractType contractType = getBoSvc().findBySinglePrimaryKey(
				ContractType.class, 1001l);
		assertNotNull(contractType);
		assertEquals("rent agreement", contractType.getName());
	}

	@Test
	/**
	 * test CRUD for {@link ContractType}
	 */
	public void testContractTypeCRUD() {
		// C
		ContractType contractType = new ContractType();
		String name = "employment";
		contractType.setName(name);
		getBoSvc().save(contractType);
		// R
		contractType.refresh();
		assertEquals("contract name does not match", name, contractType.getName());
		// U
		contractType.setDescription("signed before a commissioner of oaths");
		contractType.refresh();
		assertNotNull("contract description should not be null", contractType.getDescription());
		// D
		getBoSvc().delete(contractType);
		assertNull(getBoSvc().findBySinglePrimaryKey(ContractType.class,
				contractType.getId()));
	}
	
	@Test
	/**
	 * tests that the document type is loaded ok
	 */
	public void testContractTypeDocType() {
		assertNotNull("document type should not be null", getDocTypeSvc().findByName("ContractTypeMaintenanceDocument"));
	}
}
