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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
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
	
	/**
	 * a common method to test clerk - lawyer routing for transactional docs
	 * @param docType
	 * @throws WorkflowException 
	 */
	public void testTransactionalRouting(String docType) throws WorkflowException {
		WorkflowDocument doc = WorkflowDocumentFactory.createDocument(getPrincipalIdForName("clerk1"), docType);
		doc.saveDocument("saved");
		doc = WorkflowDocumentFactory.loadDocument(getPrincipalIdForName("clerk1"), doc.getDocumentId());
		assertTrue(doc.isSaved());
		doc.approve("routing");
		doc = WorkflowDocumentFactory.loadDocument(getPrincipalIdForName("lawyer1"), doc.getDocumentId());
		assertTrue(doc.isEnroute());
		doc.approve("OK");
		//re-retrieve document to get updated status
		doc = WorkflowDocumentFactory.loadDocument(getPrincipalIdForName("lawyer1"), doc.getDocumentId());
		assertTrue(doc.isFinal());
	}
}