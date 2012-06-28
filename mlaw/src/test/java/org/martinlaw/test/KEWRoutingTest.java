/**
 * 
 */
package org.martinlaw.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.martinlaw.bo.Annex;
import org.martinlaw.bo.AnnexDocument;
import org.martinlaw.bo.AnnexType;
import org.martinlaw.bo.CourtCase;
import org.martinlaw.bo.CourtCaseStatus;

/**
 * @author mugo
 *
 */
public class KEWRoutingTest extends KewTestsBase {
	private Logger log = Logger.getLogger(getClass());
	private org.kuali.rice.krad.service.DocumentService docSvc;
	private org.kuali.rice.krad.service.BusinessObjectService boSvc;
	/**
	 * a common method to test clerk - lawyer routing for transactional docs
	 * a document cannot be routed if there is no 
	 * @param docType
	 * @throws WorkflowException 
	 */
	private void testTransactionalRouting(String docType) throws WorkflowException {
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
	/**
	 * test routing for maintenance documents which cannot work with workflowdocument(...) when the default preprocessor is replaced
	 * @param docType - the document type name
	 * @param bo the business object
	 * @throws WorkflowException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void testMaintenanceRouting(String docType, PersistableBusinessObject bo) throws WorkflowException, InstantiationException, IllegalAccessException {
		//initiate as the clerk
		Document doc = getPopulatedMaintenanceDocument(docType, bo);
		KRADServiceLocatorWeb.getDocumentService().saveDocument(doc);
		KRADServiceLocatorWeb.getDocumentService().routeDocument(doc, "submitted", null);
		//test initiating another edoc while one is saved, to verify 'maintenance record is locked' errors
		//it does cause the maintenance record locked error alright
		/*GlobalVariables.setUserSession(new UserSession("clerk2"));
		Document doc2 = getPopulatedMaintenanceDocument(docType, bo);
		KNSServiceLocator.getDocumentService().saveDocument(doc2);
		KNSServiceLocator.getDocumentService().cancelDocument(doc2, "duplicate");*/
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
	public void testMaintenanceRoutingInitToFinal(String docType, PersistableBusinessObject bo) throws WorkflowException, InstantiationException, IllegalAccessException {
		//initiate as the clerk
		Document doc = getPopulatedMaintenanceDocument(docType, bo);
		KRADServiceLocatorWeb.getDocumentService().saveDocument(doc);
		KRADServiceLocatorWeb.getDocumentService().routeDocument(doc, "submitted", null);
		//retrieve again to confirm status
		doc = KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(doc.getDocumentNumber());
		assertTrue(doc.getDocumentHeader().getWorkflowDocument().isApproved());
		assertTrue(doc.getDocumentHeader().getWorkflowDocument().isFinal());
	}
	
	/**
	 * @param docType e.g. CaseMaintenanceDocument
	 * @param bo TODO
	 * @return the document, populated with BO info
	 * @throws WorkflowException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private Document getPopulatedMaintenanceDocument(String docType, PersistableBusinessObject bo)
			throws WorkflowException, InstantiationException, IllegalAccessException {
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
	 * test initiating another edoc while one is saved, to verify 'maintenance record is locked' errors
	 * @throws WorkflowException
	 */
	@SuppressWarnings("unused")
	private void testInitiatingTransactionalDocTwice(String docType) throws WorkflowException {
		// "CaseMaintenanceDocument";
		WorkflowDocument doc = WorkflowDocumentFactory.createDocument(getPrincipalIdForName("clerk1"), docType);
		doc.saveDocument("saved");
		doc = WorkflowDocumentFactory.loadDocument(getPrincipalIdForName("clerk1"), doc.getDocumentId());
		assertTrue(doc.isSaved());
	
		WorkflowDocument doc2 = WorkflowDocumentFactory.createDocument(getPrincipalIdForName("lawyer1"), docType);
		doc2.saveDocument("saved");
		//re-retrieve document to get updated status
		doc2 = WorkflowDocumentFactory.loadDocument(getPrincipalIdForName("lawyer1"), doc2.getDocumentId());
		assertTrue(doc2.isSaved());
		doc.cancel("canceled");
		doc2.cancel("canceled");
	}
	@SuppressWarnings({ })
	@Test
	/**
	 * test that routing a court case maintenance document works as expected
	 * 
	 * @throws WorkflowException
	 */
	public void testCaseMaintenanceRouting() throws WorkflowException {
		//set up test status
		//boSvc.delete((List)boSvc.findAll(CourtCaseStatus.class));
		CourtCaseStatus status = new CourtCaseStatus();
		status.setStatus("Testing");
		boSvc.save(status);
		status.refresh();
		assertNotNull(status.getId());
		//create new case bo
		CourtCase caseBo = new CourtCase();
		caseBo.setLocalReference("local-ref-1");
		caseBo.setCourtReference("high-court-211");
		caseBo.setName("Flesh Vs Spirit (Lifetime)");
		caseBo.setStatus(status);
		try {
			testMaintenanceRouting("CaseMaintenanceDocument", caseBo);
			Collection<CourtCase> cases = KRADServiceLocator.getBusinessObjectService().findAll(CourtCase.class);
			assertEquals(1, cases.size());
			for (CourtCase cse: cases) {
				assertEquals("local-ref-1",cse.getLocalReference());
				assertEquals("high-court-211",cse.getCourtReference());
				assertNotNull(cse.getStatus());
				log.info("created status with id " + cse.getStatus().getId());
			}
		} catch (Exception e) {
			log.error("test failed", e);
			fail("test failed");
		}
	}
	
	@Test
	@Ignore
	public void testConveyanceMaintenanceRouting() throws WorkflowException {
		testTransactionalRouting("ConveyanceMaintenanceDocument");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCaseStatusMaintenanceRouting() throws WorkflowException {
		//testTransactionalRouting("CaseStatusMaintenanceDocument");
		CourtCaseStatus status = new CourtCaseStatus();
		String statusText = "pending";
		status.setStatus(statusText);
		int existingStatus = boSvc.findAll(CourtCaseStatus.class).size();
		try {
			testMaintenanceRoutingInitToFinal("CaseStatusMaintenanceDocument", status);
			Collection<CourtCaseStatus> allStatus = KRADServiceLocator.getBusinessObjectService().findAll(CourtCaseStatus.class);
			assertEquals(existingStatus+1, allStatus.size());
			@SuppressWarnings("rawtypes")
			Map map = new HashMap(1);
			map.put("status", statusText);
			@SuppressWarnings("rawtypes")
			Collection result = boSvc.findMatching(CourtCaseStatus.class, map);
			assertNotNull(result);
			assertEquals(1, result.size());
			
		} catch (Exception e) {
			log.error("test failed", e);
			fail("test failed");
		}
	}
	
	@SuppressWarnings({ })
	@Test
	@Ignore
	public void testAnnexRouting() throws WorkflowException {
		//create test annex type so that annex creation succeeds
		//boSvc.delete((List)boSvc.findAll(AnnexType.class));
		AnnexType annexType = new AnnexType();
		annexType.setValue("Test Type");
		boSvc.save(annexType);
		annexType.refresh();
		assertNotNull(annexType.getId());
		//create annex
		Annex anx = new Annex();
		anx.setTypeId(annexType.getId());
		GlobalVariables.setUserSession(new UserSession("clerk1"));
		AnnexDocument annexDoc = (AnnexDocument) KRADServiceLocatorWeb.getDocumentService().getNewDocument("AnnexDocument");
		annexDoc.setAnnex(anx);
		//anx.setAnnexDocumentNumber(annexDoc.getDocumentNumber());
		//boSvc.save(anx);
//		anx.refresh();
//		annexDoc.setAnnexId(anx.getId());
		annexDoc.getDocumentHeader().setDocumentDescription("test document");
		//docSvc.saveDocument(annexDoc);
		//AnnexDocument annexDoc = (AnnexDocument) docSvc.getByDocumentHeaderId(annexDoc.getDocumentNumber());
		//assertTrue(annexDoc.getDocumentHeader().getWorkflowDocument().stateIsSaved());
		//assertNotNull(annexDoc.getAnnex());
		//assertNotNull(annexDoc.getAnnex().getAnnexDocumentNumber());
		docSvc.routeDocument(annexDoc, "ok!", null);
		annexDoc = (AnnexDocument) docSvc.getByDocumentHeaderId(annexDoc.getDocumentNumber());
		assertNotNull(annexDoc.getAnnex());
		assertNotNull(annexDoc.getAnnex().getAnnexDocumentNumber());
		assertTrue(annexDoc.getDocumentHeader().getWorkflowDocument().isFinal());
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("annexDocumentNumber", annexDoc.getDocumentNumber());
		@SuppressWarnings("rawtypes")
		List annexes = (List) boSvc.findMatching(Annex.class, props);
		assertNotNull(annexes);
		assertEquals(1, annexes.size());
		anx = (Annex) annexes.get(0);
		assertNotNull(anx.getTypeId());
		assertNotNull(anx.getAnnexType());
		assertEquals("Test Type", anx.getAnnexType().getValue());
	}
	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.test.KEWTestCase#setUpAfterDataLoad()
	 */
	@Override
	public void setUpInternal() throws Exception {
		super.setUpInternal();
		docSvc = KRADServiceLocatorWeb.getDocumentService();
		boSvc = KRADServiceLocator.getBusinessObjectService();
	}
	
}
