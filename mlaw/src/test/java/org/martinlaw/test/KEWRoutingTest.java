/**
 * 
 */
package org.martinlaw.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
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
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.bo.Conveyance;
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
	/**
	 * a common method to test clerk - lawyer routing for transactional docs
	 * a document cannot be routed if there is no 
	 * @param docType
	 * @throws WorkflowException 
	 */
	@SuppressWarnings("unused")
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
		//getBoSvc().delete((List)getBoSvc().findAll(CourtCaseStatus.class));
		Status status = new Status();
		status.setStatus("Testing");
		status.setType(Status.ANY_TYPE.getKey());
		getBoSvc().save(status);
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
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCaseStatusMaintenanceRouting() throws WorkflowException {
		//testTransactionalRouting("CaseStatusMaintenanceDocument");
		Status status = new Status();
		String statusText = "pending";
		status.setStatus(statusText);
		status.setType(Status.ANY_TYPE.getKey());
		int existingStatus = getBoSvc().findAll(Status.class).size();
		try {
			testMaintenanceRoutingInitToFinal("StatusMaintenanceDocument", status);
			Collection<Status> allStatus = KRADServiceLocator.getBusinessObjectService().findAll(Status.class);
			assertEquals(existingStatus+1, allStatus.size());
			@SuppressWarnings("rawtypes")
			Map map = new HashMap(1);
			map.put("status", statusText);
			@SuppressWarnings("rawtypes")
			Collection result = getBoSvc().findMatching(Status.class, map);
			assertNotNull(result);
			assertEquals(1, result.size());
			
		} catch (Exception e) {
			log.error("test failed", e);
			fail("test failed");
		}
	}
	

	@Test
	@Ignore("to be maintained as part of conveyance type")
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
		Collection<ConveyanceAnnexType> result = getBoSvc().findMatching(ConveyanceAnnexType.class, params);
		assertEquals(1, result.size());
	}
	
	@Test
	/**
	 * test that ConveyanceTypeDocument is routed to lawyer on submit
	 */
	public void testConveyanceTypeRouting() {
		ConveyanceType convType = new ConveyanceType();
		String name = "auction";
		convType.setName(name);
		// set annex types
		List<ConveyanceAnnexType> annexTypes = new ArrayList<ConveyanceAnnexType>();
		ConveyanceAnnexType convAnnexType = new ConveyanceAnnexType();
		convAnnexType.setName("signed affidavit");
		annexTypes.add(convAnnexType);
		convAnnexType = new ConveyanceAnnexType();
		convAnnexType.setName("title deed");
		annexTypes.add(convAnnexType);
		convType.setAnnexTypes(annexTypes);
		// get number of annex types before adding new
		int existingAnnexTypes = getBoSvc().findAll(ConveyanceAnnexType.class).size();
		try {
			testMaintenanceRouting("ConveyanceTypeMaintenanceDocument", convType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("test failed", e);
			fail("testConveyanceTypeRouting caused an exception");
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		Collection<ConveyanceType> result = getBoSvc().findMatching(ConveyanceType.class, params);
		assertEquals(1, result.size());
		assertEquals(2 + existingAnnexTypes, getBoSvc().findAll(ConveyanceAnnexType.class).size());
	}
	
	@Test
	/**
	 * test that a conveyance document is routed to the lawyer
	 */
	public void testConveyanceRouting() {
		int existingConveyances = getBoSvc().findAll(Conveyance.class).size();
		Conveyance conv = TestUtils.getTestConveyance();
		// add a conveyance type to avoid data integrity exceptions
		ConveyanceType convType = new ConveyanceType();
		convType.setName("test type");
		convType.setId(conv.getTypeId());
		getBoSvc().save(convType);
		// add a status type to avoid data integrity exceptions
		Status status =  new Status();
		status.setStatus("test status");
		status.setId(conv.getStatusId());
		status.setType("test type");
		getBoSvc().save(status);
		try {
			testMaintenanceRouting("ConveyanceMaintenanceDocument", conv);
		} catch (Exception e) {
			log.error("error in testConveyanceRouting", e);
			fail(e.getMessage());
		}
		// confirm BO was saved
		assertEquals(existingConveyances + 1, getBoSvc().findAll(Conveyance.class).size());
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", conv.getName());
		Collection<Conveyance> result = getBoSvc().findMatching(Conveyance.class, params);
		assertEquals(1, result.size());
		
	}
}
