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
import org.martinlaw.bo.ConveyanceAnnexType;
import org.martinlaw.bo.ConveyanceType;
import org.martinlaw.bo.CourtCase;
import org.martinlaw.bo.Status;

/**
 * @author mugo
 *
 */
public class KEWRoutingTest extends KewTestsBase {
	private Logger log = Logger.getLogger(getClass());
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
	 * creates a new maintenance document for the given doc type and business object
	 * 
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
		Status status = new Status();
		status.setStatus("Testing");
		status.setType(Status.ANY_TYPE.getKey());
		boSvc.save(status);
		status.refresh();
		assertNotNull(status.getId());
		//create new case bo
		CourtCase caseBo = new CourtCase();
		caseBo.setLocalReference("local-ref-1");
		caseBo.setCourtReference("high-court-211");
		caseBo.setName("Flesh Vs Spirit (Lifetime)");
		caseBo.setStatus(status);
		// side step validation error - error.required
		caseBo.setStatusId(status.getId());
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
		Status status = new Status();
		String statusText = "pending";
		status.setStatus(statusText);
		status.setType(Status.ANY_TYPE.getKey());
		int existingStatus = boSvc.findAll(Status.class).size();
		try {
			testMaintenanceRoutingInitToFinal("StatusMaintenanceDocument", status);
			Collection<Status> allStatus = KRADServiceLocator.getBusinessObjectService().findAll(Status.class);
			assertEquals(existingStatus+1, allStatus.size());
			@SuppressWarnings("rawtypes")
			Map map = new HashMap(1);
			map.put("status", statusText);
			@SuppressWarnings("rawtypes")
			Collection result = boSvc.findMatching(Status.class, map);
			assertNotNull(result);
			assertEquals(1, result.size());
			
		} catch (Exception e) {
			log.error("test failed", e);
			fail("test failed");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.test.KEWTestCase#setUpAfterDataLoad()
	 */
	@Override
	public void setUpInternal() throws Exception {
		super.setUpInternal();
		KRADServiceLocatorWeb.getDocumentService();
		boSvc = KRADServiceLocator.getBusinessObjectService();
	}
	
	@Test
	/**
	 * test that ConveyanceAnnexTypeDocument routes to final on submit
	 */
	public void testConveyanceAnnexTypeRouting() {
		ConveyanceAnnexType convAnnexType = new ConveyanceAnnexType();
		String name = "city council approval";
		convAnnexType.setName(name);
		try {
			testMaintenanceRoutingInitToFinal("ConveyanceAnnexTypeDocument", convAnnexType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("test failed", e);
			fail("testConveyanceAnnexTypeRouting caused an exception");
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		Collection<ConveyanceAnnexType> result = boSvc.findMatching(ConveyanceAnnexType.class, params);
		assertEquals(1, result.size());
	}
	
	@Test
	/**
	 * test that ConveyanceAnnexTypeDocument routes to final on submit
	 */
	public void testConveyanceTypeRouting() {
		ConveyanceType convType = new ConveyanceType();
		String name = "auction";
		convType.setName(name);
		try {
			testMaintenanceRouting("ConveyanceTypeDocument", convType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("test failed", e);
			fail("testConveyanceTypeRouting caused an exception");
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		Collection<ConveyanceType> result = boSvc.findMatching(ConveyanceType.class, params);
		assertEquals(1, result.size());
	}
}
