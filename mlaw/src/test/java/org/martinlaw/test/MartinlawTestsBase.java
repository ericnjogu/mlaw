package org.martinlaw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.kew.doctype.service.DocumentTypeService;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.datadictionary.DataObjectEntry;
import org.kuali.rice.krad.document.DocumentBase;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.test.KRADTestCase;
import org.martinlaw.bo.Fee;
import org.martinlaw.bo.MartinlawPerson;
import org.martinlaw.bo.courtcase.CourtCasePerson;
import org.martinlaw.util.TestUtils;

public abstract class MartinlawTestsBase extends KRADTestCase {
	private BusinessObjectService boSvc;
	private TestUtils testUtils;
	private Log log = LogFactory.getLog(getClass());

	public MartinlawTestsBase() {
		// TODO - can this be retrieved from the properties? (maybe they are not available at this time) or JVM params?
		super("mlaw");//provide module name to override default krad test module name - works for 2.2.0-M2-SNAPSHOT+*/
		//super();// to allow pre 2.2.0-M2 to compile
		//setModuleName("mlaw");*/
	}	

	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#getLoadApplicationLifecycle()
	 */
	@Override
	protected Lifecycle getLoadApplicationLifecycle() {
		//TODO: create a get application context file() method in KRADTestCase so that we only override that
		// and keep the logic there
		SpringResourceLoader springResourceLoader = new SpringResourceLoader(new QName("KRADTestResourceLoader"), "classpath:org/martinlaw/bo/BOTest-context.xml", null);
    	springResourceLoader.setParentSpringResourceLoader(getTestHarnessSpringResourceLoader());
    	return springResourceLoader;
	}
	
	@Override
	/**
	 * make sure the right module name is set so that the relevant config files are loaded
	 */
	protected List<String> getConfigLocations() {
		return Arrays.asList(new String[]{"classpath:META-INF/mlaw-test-config.xml", "classpath:META-INF/krad-test-config.xml"});
	}

	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		setBoSvc(KRADServiceLocator.getBusinessObjectService());
	}

	/**
	 * @return the boSvc
	 */
	public BusinessObjectService getBoSvc() {
		return boSvc;
	}

	/**
	 * @param boSvc the boSvc to set
	 */
	public void setBoSvc(BusinessObjectService boSvc) {
		this.boSvc = boSvc;
	}

	/**
	 * check for lookup, inquiry, maint view definitions, maintenance entry def
	 * 
	 * @param dataObjectClass - the data object class
	 */
	protected void verifyMaintDocDataDictEntries(Class<?> dataObjectClass) {
		verifyInquiryLookup(dataObjectClass);
		assertTrue(dataObjectClass + " should be maintainable",
				KRADServiceLocatorWeb.getViewDictionaryService().isMaintainable(dataObjectClass));
		assertNotNull("maint doc entry should not be null",
				KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getMaintenanceDocumentEntryForBusinessObjectClass(dataObjectClass));
		//params = 
		//assertNotNull("View should not be null",				KRADServiceLocatorWeb.getDataDictionaryService().getViewByTypeIndex(UifConstants.ViewType.MAINTENANCE, viewId));
	}

	/**
	 * check for lookup, inquiry view definitions
	 * 
	 * @param dataObjectClass - the data object class used in the definitions
	 */
	protected void verifyInquiryLookup(Class<?> dataObjectClass) {
		assertTrue(dataObjectClass + " should be inquirable", 
				KRADServiceLocatorWeb.getViewDictionaryService().isInquirable(dataObjectClass));
		assertTrue(dataObjectClass + " should be lookupable",
				KRADServiceLocatorWeb.getViewDictionaryService().isLookupable(dataObjectClass));
	}

	/**
	 * test whether a business object entry can be retrieved. contrived after null pointer exceptions
	 * with court case status
	 * could be modified to return the bo entry for specific tests
	 * 
	 * @param className - fully qualified class name
	 */
	protected void testBoAttributesPresent(String className) {
		DataObjectEntry dataObject = KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getDataObjectEntry(className);
		assertNotNull("data object should not be null", dataObject);
		assertNotNull("attribute names should not be null", dataObject.getAttributeNames());
	}

	/**
	 * convenience method to retrive obj ref 
	 */
	protected DocumentTypeService getDocTypeSvc() {
		return KEWServiceLocator.getDocumentTypeService();
	}
	
	/**
	 * convenience method for access in derived tests
	 * 
	 * @return
	 */
	public TestUtils getTestUtils() {
		if (testUtils == null) {
			testUtils = new TestUtils();
		}
		return testUtils;
	}

	@Test
	public void validateDataDictionary() {
		KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().validateDD(true);
	}

	/**
	 * convenience method to verify fee attribute values
	 * 
	 * @param fee - the test fee
	 */
	protected void testFeeFields(Fee fee) {
		log.info("fee amount is: " + fee.getAmount().toPlainString());
		assertEquals("2500.58", fee.getAmount().toPlainString());
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(fee.getDate().getTime());
		assertEquals(2011,cal.get(Calendar.YEAR));
		assertEquals(Calendar.JUNE, cal.get(Calendar.MONTH));
		assertEquals(12, cal.get(Calendar.DATE));
	}

	/**
	 * common method to test court case and conveyance fee CRUD
	 */
	public void testFeeCRUD(Fee fee, Class<? extends Fee> klass) {
		//CourtCaseFee fee = new CourtCaseFee();
		BigDecimal amount = new BigDecimal(1000);
		fee.setAmount(amount);
		//fee.setCourtCaseId(1001l);
		fee.setDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		// leave description blank
		//save
		getBoSvc().save(fee);
		//retrieve
		fee = getBoSvc().findBySinglePrimaryKey(klass, fee.getId());
		//fee.refresh();
		assertNotNull(fee);
		assertEquals(0, amount.compareTo(new BigDecimal(1000)));
		//edit
		amount = new BigDecimal(900); //discount 
		fee.setAmount(amount);
		getBoSvc().save(fee);
		//confirm change
		// fee = boSvc.findBySinglePrimaryKey(CourtCaseFee.class, id);
		fee.refresh();
		assertEquals(0, amount.compareTo(new BigDecimal(900)));
		//delete
		getBoSvc().delete(fee);
		assertNull(getBoSvc().findBySinglePrimaryKey(klass, fee.getId()));
		
	}

	/**
	 * a common method to perform CRUD on objects with {@link CourtCasePerson} as the parent
	 * @param principalName - the principal name e.g. enjogu
	 * @param newBo TODO
	 */
	protected <T extends BusinessObject> void testMartinlawPersonCRUD(
			T t, String principalName, MartinlawPerson newBo) {
				MartinlawPerson personRetrieve = (MartinlawPerson) getBoSvc().findBySinglePrimaryKey(t.getClass(), new Long(1001));
				assertNotNull(personRetrieve);
				assertEquals(principalName, personRetrieve.getPrincipalName());
				// C
				
				newBo.setPrincipalName("mkoobs");
				getBoSvc().save(newBo);
				// R
				newBo = (MartinlawPerson) getBoSvc().findBySinglePrimaryKey(t.getClass(), newBo.getId());
				assertNotNull(newBo);
				// U
				newBo.setPrincipalName("mogs");
				getBoSvc().save(newBo);
				newBo.refresh();
				// D
				getBoSvc().delete(newBo);
				assertNull((MartinlawPerson) getBoSvc().findBySinglePrimaryKey(t.getClass(), newBo.getId()));
			}

	/**
	 * test DD entries for a transactional doc
	 * 
	 * @param docType - the document type name
	 * @param klass - the document class
	 * @param viewId - the view id
	 */
	public void testTxDocDD (String docType, Class<? extends DocumentBase> klass, String viewId) {
		assertNotNull("document entry should not be null", KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getDocumentEntry(docType));
		assertEquals("document type name does not match", docType, KRADServiceLocatorWeb.getDataDictionaryService().getDocumentTypeNameByClass(klass));
		assertNotNull("viewId should not be null", KRADServiceLocatorWeb.getDataDictionaryService().getViewById(viewId));
	}
}