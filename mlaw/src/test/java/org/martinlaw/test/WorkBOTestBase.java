package org.martinlaw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.martinlaw.Constants;
import org.martinlaw.bo.MatterWork;

/**
 * base class with BO tests for {@link MatterWork} objects
 * @author mugo
 *
 */
public class WorkBOTestBase extends MartinlawTestsBase {

	public WorkBOTestBase() {
		super();
	}

	/**
	 * test that a {@link MatterWork} object created via sql can be retrieved
	 * @param klass
	 */
	public void testWorkRetrieve(Class<? extends MatterWork> klass) {
		MatterWork work = getBoSvc().findBySinglePrimaryKey(klass, 1001l);
		assertNotNull("result should not be null", work);
		//assertNotNull("contract should not be null", contractWork.getMatter());
	}

	/**
	 * test that {@link MatterWork} is loaded into the data dictionary
	 */
	public void testWorkDD(String docType, Class<? extends MatterWork> klass) {
		assertNotNull("document entry should not be null", KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getDocumentEntry(docType));
		assertEquals("document type name does not match", Constants.DocTypes.CONTRACT_WORK, KRADServiceLocatorWeb.getDataDictionaryService().getDocumentTypeNameByClass(klass));
	}

	/**
	 * tests {@link org.martinlaw.bo.MatterWork#isMatterIdValid()}
	 */
	public void testMatterIdValidity(MatterWork work) {
		assertFalse("un-initialized matter id should be invalid", work.isMatterIdValid());
		work.setMatterId(-1l);
		assertFalse("matter id should be invalid", work.isMatterIdValid());
		work.setMatterId(1001l);
		assertTrue("matter id should be valid", work.isMatterIdValid());
	}
}