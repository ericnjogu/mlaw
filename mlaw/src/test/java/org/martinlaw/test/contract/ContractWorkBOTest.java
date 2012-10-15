/**
 * 
 */
package org.martinlaw.test.contract;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.Constants;
import org.martinlaw.bo.contract.Work;

/**
 * tests CRUD and data dictionary of {@link Work}
 * 
 * @author mugo
 *
 */
public class ContractWorkBOTest extends ContractBoTestBase {
	
	@Test
	/**
	 * test that {@link Work} is loaded into the data dictionary
	 */
	public void testContractWorkDD() {
		assertNotNull("document entry should not be null", KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getDocumentEntry(Constants.DocTypes.CONTRACT_WORK));
		assertEquals("document type name does not match", Constants.DocTypes.CONTRACT_WORK, KRADServiceLocatorWeb.getDataDictionaryService().getDocumentTypeNameByClass(Work.class));
	}
	
	/**
	 * test retrieving a {@link Work} that has been created through sql
	 */
	@Test
	public void testContractWorkRetrieve() {
		Work contractWork = getBoSvc().findBySinglePrimaryKey(Work.class, 1001l);
		assertNotNull("result should not be null", contractWork);
		//assertNotNull("contract should not be null", contractWork.getMatter());
	}

	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-work-test-data.sql", ";").runSql();
	}
	
	@Test
	/**
	 * tests {@link org.martinlaw.bo.MatterWork#isMatterIdValid()}
	 */
	public void testMatterIdValidity() {
		Work contractWork = new Work();
		assertFalse("un-initialized matter id should be invalid", contractWork.isMatterIdValid());
		contractWork.setMatterId(-1l);
		assertFalse("matter id should be invalid", contractWork.isMatterIdValid());
		contractWork.setMatterId(1001l);
		assertTrue("matter id should be valid", contractWork.isMatterIdValid());
	}
	
	@Test
	/**
	 * tests for the expected label, as it is used to display validation messages by {@link org.martinlaw.bo.MatterWorkRule}
	 */
	public void testMatterIdLabel() {
		assertEquals("label differs", "Contract Id", 
				KRADServiceLocatorWeb.getDataDictionaryService().getAttributeLabel(Work.class, Constants.PropertyNames.MATTER_ID));
		assertNotNull(KRADServiceLocatorWeb.getDataDictionaryService().getAttributeDefinition(Work.class.getCanonicalName(), Constants.PropertyNames.MATTER_ID));
	}
}
