/**
 * 
 */
package org.martinlaw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.ContractSignatory;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link ContractSignatory}
 * 
 * @author mugo
 * 
 */
public class ContractSignatoryBOTest extends MartinlawTestsBase {

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
		testBoAttributesPresent("org.martinlaw.bo.ContractSignatory");
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
