package org.martinlaw.test.courtcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.bo.courtcase.Client;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.test.KewTestsBase;
import org.martinlaw.util.SearchTestCriteria;

public abstract class CourtCaseRoutingTestBase extends KewTestsBase {

	private Logger log = Logger.getLogger(getClass());
	final String localReference = "LOCAL-REF-1";
	final String courtReference = "Kisii High Court Petition No. 6 of 2013";

	public CourtCaseRoutingTestBase() {
		super();
	}

	@Test
	public void testCaseMaintenanceRouting() throws WorkflowException {
		try {
			// with custom doc searching enabled, saving the document first introduces errors in which the kr users is recorded as routing the doc
			CourtCase testCourtCase = getTestCourtCase();
			testCourtCase.setClientPrincipalName("Emma Njau");
			testMaintenanceRoutingInitToFinal(getDocTypeName(), testCourtCase);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("localReference", localReference);
			Collection<CourtCase> cases = KRADServiceLocator.getBusinessObjectService().findMatching(CourtCase.class, params);
			assertEquals("Should have found one case", 1, cases.size());
			CourtCase cse = cases.iterator().next();
			assertEquals("local reference differs", localReference, cse.getLocalReference());
			assertEquals("court reference differs", courtReference, cse.getCourtReference());
			assertNotNull("status should not be null", cse.getStatus());
			testCreatedCase(cse);
			log.info("created status with id " + cse.getStatus().getId());
			
			String msg = "expected principal name differs";
			assertEquals(msg, "emma_njau", cse.getClientPrincipalName());
			// the names are blank since they are saved via bosvc rather than identity svc
			// assertEquals("main client first name differs", "Emma", cse.getClient().getFirstName());
			
			assertEquals("number of clients expected differs", 2, cse.getClients().size());
			assertEquals(msg, "joseph_ndungu", cse.getClients().get(0).getPrincipalName());
			assertEquals(msg, "joseph_thube", cse.getClients().get(1).getPrincipalName());
			
			assertEquals("number of witnesses expected differs", 1, cse.getWitnesses().size());
			assertEquals(msg, "thomas_kaberi_gitau", cse.getWitnesses().get(0).getPrincipalName());
		} catch (Exception e) {
			log.error("test failed", e);
			fail("test failed due to an exception - " + e.getClass());
		}
	}

	/**
	 * a convenience method to modify a test object if need be
	 * @return the modified object
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected CourtCase getTestCourtCase() throws InstantiationException,
			IllegalAccessException {
		return getTestUtils().getTestCourtCase(localReference, courtReference, getDataObjectClass());
	}
	
	/**
	 * make additional assertions
	 * @param kase - a test object
	 */
	protected void testCreatedCase(CourtCase kase) {
		// default does nothing
	}

	/**
	 * verifies that the collection required fields are validated on route not save
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws WorkflowException
	 */
	@Test
	public void testCourtCaseRouting_required_collection_validated_onroute()
			throws InstantiationException, WorkflowException,
			IllegalAccessException {
				CourtCase courtCase = getTestCourtCase();
				// create client with blank name and add to collection
				Client client = new Client();
				client.setPrincipalName(null);
				courtCase.getClients().add(client);
				//initiate as the clerk
				Document doc = getPopulatedMaintenanceDocument(getDocTypeName(), courtCase, "clerk1");
				testRouting_required_validated_onroute(doc);
			}

	/**
	 * verifies that a required field is validated on route not save
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws WorkflowException
	 */
	@Test
	public void testCourtCaseRouting_required_field_validated_onroute()
			throws InstantiationException, WorkflowException,
			IllegalAccessException {
				CourtCase courtCase = getTestCourtCase();
				courtCase.setLocalReference(null);
				//initiate as the clerk
				Document doc = getPopulatedMaintenanceDocument(getDocTypeName(), courtCase, "clerk1");
				testRouting_required_validated_onroute(doc);
			}

	@Test
	public void testCourtCase_doc_search() throws WorkflowException {
		try {
			// route 2 docs first
			final String docType = getDocTypeName();
			CourtCase testCourtCase = getTestCourtCase();
			final String caseName1 = "Bingu Vs Nchi";
			testCourtCase.setName(caseName1);
			testMaintenanceRoutingInitToFinal(docType, testCourtCase);
			final String localRef = "LOCALREF1";
			final String courtRef = "courtRef1";
			CourtCase testCourtCase2 = getTestUtils().getTestCourtCase(localRef, courtRef, null);
			testCourtCase2.setName("Moto vs Maji");
			testMaintenanceRoutingInitToFinal(docType, testCourtCase2);
			
			SearchTestCriteria crit1 = new SearchTestCriteria();
			crit1.setExpectedDocuments(2);
			// search for local reference
			SearchTestCriteria crit2 = new SearchTestCriteria();
			crit2.setExpectedDocuments(1);
			crit2.getFieldNamesToSearchValues().put("localReference", localRef);
			// search for local reference
			SearchTestCriteria crit3 = new SearchTestCriteria();
			crit3.setExpectedDocuments(1);
			crit3.getFieldNamesToSearchValues().put("name", "Bingu*");
			// search for main client
			SearchTestCriteria crit4 = new SearchTestCriteria();
			crit4.setExpectedDocuments(2);
			crit4.getFieldNamesToSearchValues().put("clientPrincipalName", "client1");
			
			List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
			crits.add(crit1);
			crits.add(crit2);
			crits.add(crit3);
			crits.add(crit4);
			getTestUtils().runDocumentSearch(crits, docType);
	        
		} catch (Exception e) {
			log.error("test failed", e);
			fail("test failed due to an exception - " + e.getMessage());
		}
	}

	/**
	 * allow instantiation of related classes from tests derived from this class
	 * @return the class under test
	 */
	public abstract Class<? extends CourtCase> getDataObjectClass();

}