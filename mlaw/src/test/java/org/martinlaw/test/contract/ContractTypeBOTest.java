/**
 * 
 */
package org.martinlaw.test.contract;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.test.SQLDataLoader;
import org.kuali.rice.test.lifecycles.KEWXmlDataLoaderLifecycle;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader(
				"classpath:org/martinlaw/scripts/contract-type-test-data.sql",
				";").runSql();
	}

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
		verifyMaintDocDataDictEntries(dataObjectClass, null);
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
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/contractType.xml"));
		return suiteLifecycles;
	}

}
