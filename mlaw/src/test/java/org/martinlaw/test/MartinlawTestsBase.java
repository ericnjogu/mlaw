package org.martinlaw.test;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012, 2013 Eric Njogu (kunadawa@gmail.com)
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

import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.QName;

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
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.test.SQLDataLoader;
import org.kuali.rice.test.lifecycles.KEWXmlDataLoaderLifecycle;
import org.kuali.test.KRADTestCase;
import org.martinlaw.bo.MartinlawPerson;
import org.martinlaw.bo.courtcase.CourtCasePerson;
import org.martinlaw.util.TestUtils;

public abstract class MartinlawTestsBase extends KRADTestCase {
	private BusinessObjectService boSvc;
	private TestUtils testUtils;
	//private Log log = LogFactory.getLog(getClass());

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
	 * check for maintenance entry def
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
	 * a common method to perform CRUD on objects with {@link CourtCasePerson} as the parent
	 * @param principalName - the principal name e.g. enjogu
	 * @param newBo - the new business object
	 */
	protected <T extends BusinessObject> void testMartinlawPersonCRUD(
			Class<T> t, String principalName, MartinlawPerson newBo) {
		MartinlawPerson personRetrieve = (MartinlawPerson) getBoSvc().findBySinglePrimaryKey(t, new Long(1001));
		assertNotNull(personRetrieve);
		assertEquals(principalName, personRetrieve.getPrincipalName());
		// C
		
		newBo.setPrincipalName("mkoobs");
		getBoSvc().save(newBo);
		// R
		newBo = (MartinlawPerson) getBoSvc().findBySinglePrimaryKey(t, newBo.getId());
		assertNotNull(newBo);
		// U
		newBo.setPrincipalName("mogs");
		getBoSvc().save(newBo);
		newBo.refresh();
		// D
		getBoSvc().delete(newBo);
		assertNull((MartinlawPerson) getBoSvc().findBySinglePrimaryKey(t, newBo.getId()));
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
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/test-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/notifications.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/event-type-default-data.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/consideration-type-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/consideration-type-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/transaction-type-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/transaction-type-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/work-type-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/work-type-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/event-type-perms-roles.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/matter-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/matter-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/matter-event-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/matter-transaction-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/matter-assignee-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/matter-work-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/matter-assignee-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/matter-consideration-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/matter-event-perms-roles.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/conveyance-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/note-atts-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/conveyance-work-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/conveyance-work-perms-roles.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/contract-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/contract-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/contract-type-perms-roles.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/court-case-type-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/court-case-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/court-case-type-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/land-case-perms-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/notification-content-event-type.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/calendar-event-test-data.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/test-identity-mgr-perm-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/identity-mgr-perm-roles.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/openid-activation-test-data.sql", ";").runSql();
		
		new SQLDataLoader("classpath:org/martinlaw/scripts/sql/openid-setup.sql", ";").runSql();
		
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
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/rules/shared-rule-attributes.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/rules/rule-templates.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/martinlaw-default-routing-document.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/rules/rules.xml"));
		
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/case.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/status.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/conveyanceType.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/conveyance.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/conveyanceWork.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/contract.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/contractType.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/eventType.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/considerationType.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/matterConsideration.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/matter.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/matterEvent.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/matterAssignee.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/matterTransactionDoc.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/matterWork.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/transactionType.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/workType.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/caseType.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/landCase.xml"));
		return suiteLifecycles;
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.test.RiceTestCase#tearDown()
	 */
	/**
	 * clear error messages from the global variables
	 */
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		GlobalVariables.getMessageMap().clearErrorMessages();
	}
}