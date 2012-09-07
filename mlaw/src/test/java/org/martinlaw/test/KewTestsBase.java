package org.martinlaw.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.DataObjectAuthorizationService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.test.SQLDataLoader;
import org.kuali.rice.test.lifecycles.KEWXmlDataLoaderLifecycle;
/**
 * loads config files for testing routing
 * TODO - clear GlobalVariables error map after each test so that a failing test does not affect the rest
 * @author mugo
 *
 */
public abstract class KewTestsBase extends MartinlawTestsBase {

	public KewTestsBase() {
		super();
	}

	@Override
	protected List<Lifecycle> getSuiteLifecycles() {
		List<Lifecycle> suiteLifecycles = super.getSuiteLifecycles();
		/*
		 * needs to be here rather in loadData() since it leads to 'object modified' OJB exceptions
		 */
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
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/rules/rules.xml"));
		return suiteLifecycles;
	}
	
	/**
	 * convenience method to retrieve a principal id
	 * @param name - the principal name
	 * @return the principal id
	 */
	protected String getPrincipalIdForName(String name) {
		return KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(name).getPrincipalId();
	}

	/**
	 * creates a new maintenance document for the given doc type and business object
	 * 
	 * @param docType e.g. CaseMaintenanceDocument
	 * @param bo TODO
	 * @return the document, populated with BO info
	 * @throws WorkflowException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	protected Document getPopulatedMaintenanceDocument(String docType, PersistableBusinessObject bo)
			throws WorkflowException, InstantiationException,
			IllegalAccessException {
				GlobalVariables.setUserSession(new UserSession("clerk1"));
				MaintenanceDocument doc = (MaintenanceDocument) KRADServiceLocatorWeb.getDocumentService().getNewDocument(docType);
				//old bo
				doc.getOldMaintainableObject().setDataObjectClass(bo.getClass());
				doc.getOldMaintainableObject().setDataObject(bo.getClass().newInstance());
				//new object
				doc.getNewMaintainableObject().setDataObjectClass(bo.getClass());
				doc.getNewMaintainableObject().setDataObject(bo);
				doc.getNewMaintainableObject().setMaintenanceAction(KRADConstants.MAINTENANCE_NEW_ACTION);
				//required fields
				doc.getDocumentHeader().setDocumentDescription("new test maint doc");
				return doc;
			}

	/**
	 * test routing for maintenance documents which cannot work with workflowdocument(...) when the default preprocessor is replaced
	 * @param docType - the document type name
	 * @param bo the business object
	 * @throws WorkflowException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void testMaintenanceRouting(String docType, PersistableBusinessObject bo)
			throws WorkflowException, InstantiationException,
			IllegalAccessException {
				//initiate as the clerk
				Document doc = getPopulatedMaintenanceDocument(docType, bo);
				KRADServiceLocatorWeb.getDocumentService().saveDocument(doc);
				KRADServiceLocatorWeb.getDocumentService().routeDocument(doc, "submitted", null);
				//retrieve as the lawyer
				GlobalVariables.setUserSession(new UserSession("lawyer1"));
				doc = KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(doc.getDocumentNumber());
				assertTrue(doc.getDocumentHeader().getWorkflowDocument().isEnroute());
				KRADServiceLocatorWeb.getDocumentService().approveDocument(doc, "right", null);
				//retrieve again to confirm status
				doc = KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(doc.getDocumentNumber());
				assertTrue(doc.getDocumentHeader().getWorkflowDocument().isApproved());
			}

	/**
	 * test routing for maintenance documents which are submitted by the initiator into final status
	 * 
	 * @param docType - the document type name
	 * @param bo the business object
	 * @throws WorkflowException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void testMaintenanceRoutingInitToFinal(String docType, PersistableBusinessObject bo)
			throws WorkflowException, InstantiationException,
			IllegalAccessException {
				//initiate as the clerk
				Document doc = getPopulatedMaintenanceDocument(docType, bo);
				KRADServiceLocatorWeb.getDocumentService().saveDocument(doc);
				KRADServiceLocatorWeb.getDocumentService().routeDocument(doc, "submitted", null);
				//retrieve again to confirm status
				doc = KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(doc.getDocumentNumber());
				assertTrue("document should have been approved", doc.getDocumentHeader().getWorkflowDocument().isApproved());
				assertTrue("document should be final", doc.getDocumentHeader().getWorkflowDocument().isFinal());
			}

	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/perms-roles.sql",
				";").runSql();
		new SQLDataLoader(
				"classpath:org/martinlaw/scripts/test-perms-roles.sql", ";")
				.runSql();
	}

	protected DataObjectAuthorizationService getDataObjAuthSvc() {
		return KRADServiceLocatorWeb
				.getDataObjectAuthorizationService();
	}

	/**
	 * a common method to test create and maintain permissions
	 * 
	 * @param klass - the data object class
	 * @param docType - the document type name
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	protected void testCreateMaintain(Class<?> klass, String docType) {
		String[] authPrincipalNames = { "clerk1", "lawyer1"};
		for (String principalName : authPrincipalNames) {
			assertTrue(principalName + " has permission to create", getDataObjAuthSvc().canCreate(klass, 
					KimApiServiceLocator.getPersonService().getPersonByPrincipalName(principalName), docType));
			
			try {
				assertTrue(principalName + " has permission to maintain", getDataObjAuthSvc().canMaintain(klass.newInstance(), 
						KimApiServiceLocator.getPersonService().getPersonByPrincipalName(principalName), docType));
			} catch (InstantiationException e) {
				fail(e.getMessage());
			} catch (IllegalAccessException e) {
				fail(e.getMessage());
			}
		}
		
		String[] nonAuthPrincipalNames = { "witness1", "client1" };
		for (String principalName : nonAuthPrincipalNames) {
			assertFalse(principalName + " has no permission to create", getDataObjAuthSvc().canCreate(klass,
					KimApiServiceLocator.getPersonService()
							.getPersonByPrincipalName(principalName), docType));
			
			try {
				assertFalse(principalName + " has no permission to maintain", getDataObjAuthSvc().canMaintain(klass.newInstance(), 
						KimApiServiceLocator.getPersonService().getPersonByPrincipalName(principalName), docType));
			} catch (InstantiationException e) {
				fail(e.getMessage());
			} catch (IllegalAccessException e) {
				fail(e.getMessage());
			}
		}
	}
}