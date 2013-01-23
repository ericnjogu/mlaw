package org.martinlaw.test;

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
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.kuali.rice.test.SQLDataLoader;
import org.kuali.rice.test.lifecycles.KEWXmlDataLoaderLifecycle;
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
		SpringResourceLoader springResourceLoader = new SpringResourceLoader(new QName("KRADTestResourceLoader"), "classpath:org/martinlaw/bo/KRADTestSpringBeans-custom.xml", null);
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
	 * test whether a business object entry can be retrieved. 
	 * <p>contrived after null pointer exceptions
	 * with court case status. Could be modified to return the bo entry for specific tests</p>
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

	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/test-perms-roles.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-assignment-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-assignment-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/note-atts-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-fee-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-work-test-data.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-type-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-assignment-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-assignment-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-work-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-fee-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-party-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-signatory-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-type-perms-roles.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-assignment-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-date-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-assignment-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/date-type-default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-date-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-fee-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-work-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/date-type-perms-roles.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/calendar-event-test-data.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-assignment-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-assignment-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-fee-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-work-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/test-identity-mgr-perm-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/identity-mgr-perm-roles.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/ldap/ldap.sql", ";").runSql();
	}

	@Override
	protected List<Lifecycle> getSuiteLifecycles() {
		List<Lifecycle> suiteLifecycles = super.getSuiteLifecycles();
		/*
		 * needs to be here rather in loadData() since it leads to 'object modified' OJB exceptions
		 */
		// uncomment users.xml if unit tests are running without the ldap server
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/kim/users.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/kim/groups.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/rules/rule-templates.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/case.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/status.xml"));
		// to be maintained via the conveyance type
		/*suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/conveyanceAnnexType.xml"));*/
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/conveyanceType.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/conveyance.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/contract.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/contractType.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/contractAssignment.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/conveyanceAssignment.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/caseAssignment.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/opinion.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/opinionAssignment.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/contractWork.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/caseWork.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/opinionWork.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/conveyanceWork.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/contractFee.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/conveyanceFee.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/caseFee.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/opinionFee.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/dateType.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/caseDate.xml"));
		return suiteLifecycles;
	}
}