package org.martinlaw.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.kew.doctype.service.DocumentTypeService;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.krad.datadictionary.DataObjectEntry;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.test.KRADTestCase;

public abstract class MartinlawTestsBase extends KRADTestCase {
	private BusinessObjectService boSvc;
	private TestUtils testUtils;

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
	 * @param viewId TODO
	 */
	protected void verifyMaintDocDataDictEntries(Class<?> dataObjectClass, String viewId) {
		verifyInquiryLookup(dataObjectClass);
		assertTrue(dataObjectClass + " should be maintainable", KRADServiceLocatorWeb.getViewDictionaryService().isMaintainable(dataObjectClass));
		assertNotNull("maint doc entry should not be null", KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getMaintenanceDocumentEntryForBusinessObjectClass(dataObjectClass));
		//params = 
		//assertNotNull("View should not be null",				KRADServiceLocatorWeb.getDataDictionaryService().getViewByTypeIndex(UifConstants.ViewType.MAINTENANCE, viewId));
	}

	/**
	 * check for lookup, inquiry view definitions
	 * 
	 * @param dataObjectClass - the data object class used in the definitions
	 */
	protected void verifyInquiryLookup(Class<?> dataObjectClass) {
		assertTrue(KRADServiceLocatorWeb.getViewDictionaryService().isInquirable(dataObjectClass));
		assertTrue(KRADServiceLocatorWeb.getViewDictionaryService().isLookupable(dataObjectClass));
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
		assertNotNull(dataObject.getAttributeNames());
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

}