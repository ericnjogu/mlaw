package org.martinlaw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.exception.ValidationException;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.DummyWorkFlowDocument;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.util.SearchTestCriteria;

/**
 * holds common tests for {@link org.martinlaw.bo.MatterWork}
 * @author mugo
 *
 */
public class MatterWorkRoutingTest extends TxRoutingTestBase {

	public MatterWorkRoutingTest() {
		super();
	}
	
	/**
	 * confirms that the custom workflow document field is populated when documents are retrieved from the document service
	 * <p>also confirms that org.martinlaw.routing.AnnexTypeApprovalSplit works since the default annex type requires routing</p>
	 * <p>tests the code in {@link org.martinlaw.bo.MatterWork#setDocumentHeader(DocumentHeader)}
	 */
	@Test
	public void testWorkFlowDocument() throws WorkflowException {
		try {
			// save
			String initiator = "clerk1";
			GlobalVariables.setUserSession(new UserSession(initiator));
			MatterWork work = getTestUtils().populateMatterWork((MatterWork) KRADServiceLocatorWeb.getDocumentService().getNewDocument(
					getDocTypeName() + "Test"));
			// bo does not exist until it is saved - initiated docs will not show up on the inquiry
			/*MatterWork bo = getBoSvc().findBySinglePrimaryKey(work.getClass(), work.getDocumentHeader());
			assertNotNull("for initiated doc, workflow document should not be null", bo.getDocumentHeader().getWorkflowDocument());*/
			
			work = (MatterWork)KRADServiceLocatorWeb.getDocumentService().saveDocument(work);
			MatterWork bo = getBoSvc().findBySinglePrimaryKey(work.getClass(), work.getDocumentNumber());
			assertNotNull("for saved doc, workflow document should not be null", bo.getDocumentHeader().getWorkflowDocument());
			assertEquals("principal name differs", initiator, bo.getDocumentInitiatorNetworkId());
			work = (MatterWork) KRADServiceLocatorWeb.getDocumentService().routeDocument(work, "submit", null);
			WorkflowDocument workflowDoc = WorkflowDocumentFactory.loadDocument(getPrincipalIdForName(initiator), work.getDocumentNumber());
			assertTrue("work should be enroute", workflowDoc.isEnroute());
			
			String approver = "lawyer1";
			GlobalVariables.setUserSession(new UserSession(approver));
			work = (MatterWork) KRADServiceLocatorWeb.getDocumentService().approveDocument(work, "approved", null);
			//workflowDoc = WorkflowDocumentFactory.loadDocument(getPrincipalIdForName(initiator), work.getDocumentNumber());
			// assertTrue("work should be final", work.getDocumentHeader().getWorkflowDocument().isFinal()); FAIL - is it because the test bo does not have an ojb config entry?
			
			//retrieve again to confirm status
			bo = getBoSvc().findBySinglePrimaryKey(work.getClass(), work.getDocumentNumber());
			assertNotNull("for final doc, workflow document should not be null", bo.getDocumentHeader().getWorkflowDocument());
			assertEquals("principal name differs", initiator, bo.getDocumentInitiatorNetworkId());
			assertFalse("completion period should not be empty", StringUtils.isEmpty( bo.getPeriodToCompletion()));
			assertFalse("approval period should not be empty", StringUtils.isEmpty( bo.getPeriodToApprove()));
			
			work.refreshNonUpdateableReferences();
			assertNotNull("annex type should not be null", work.getAnnexType());
			assertEquals("default annex type differs", MartinlawConstants.DEFAULT_ANNEX_TYPE_ID, work.getAnnexTypeId());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	/**
	 * tests that a none-existent matter id will cause a validation exception on routing
	 */
	@Test(expected = ValidationException.class)
	public void testWorkFlowDocument_matterId_validation() throws WorkflowException {
		// save
		String initiator = "clerk1";
		GlobalVariables.setUserSession(new UserSession(initiator));
		MatterWork work = (MatterWork) getTxDoc();
		work.setMatterId(5001l);//non-existent

		work = (MatterWork) KRADServiceLocatorWeb.getDocumentService().routeDocument(work, "approved", null);
	}

	@Override
	public MatterTxDocBase getTxDoc() throws WorkflowException {
		return getTestUtils().populateMatterWork((MatterWork) KRADServiceLocatorWeb.getDocumentService().getNewDocument(
				getDocTypeName()));
	}

	@Override
	public String getDocTypeName() {
		return MartinlawConstants.DocTypes.MATTER_WORK;
	}

	@Override
	@Test
	public void testDocSearch() {
		Long annexTypeId = 10024l; // does not require approval
		Long matterId = 1002l;
		// search before routing docs
		SearchTestCriteria crit2 = new SearchTestCriteria();
		crit2.setExpectedDocuments(0);
		crit2.getFieldNamesToSearchValues().put("annexTypeId", String.valueOf(annexTypeId));
		// search for comment wildcard
		SearchTestCriteria crit3 = new SearchTestCriteria();
		crit3.setExpectedDocuments(0);
		crit3.getFieldNamesToSearchValues().put("matterId", String.valueOf(matterId));
		List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
		crits.add(crit2);
		crits.add(crit3);
		getTestUtils().runDocumentSearch(crits, getDocTypeName());
		
		try {
			MatterWork work1;
			work1 = getTestUtils().populateMatterWork((MatterWork) KRADServiceLocatorWeb.getDocumentService().getNewDocument(
					getDocTypeName()));
			work1.setAnnexTypeId(annexTypeId );
			getTestUtils().testTransactionalRoutingInitToFinal(work1);
			
			MatterWork work2 = getTestUtils().populateMatterWork((MatterWork) KRADServiceLocatorWeb.getDocumentService().getNewDocument(
					getDocTypeName()));
			work2.setMatterId(matterId );
			getTestUtils().testTransactionalRoutingInitToFinal(work2);
			
			crit2.setExpectedDocuments(1);
			crit3.setExpectedDocuments(1);
			getTestUtils().runDocumentSearch(crits, getDocTypeName());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

	/**
	 * tests that getting work flow doc from newly instantiated bo returns the custom null work flow doc
	 */
	@Test
	public void testWorkFlowDocument_newBO() {
		GlobalVariables.setUserSession(new UserSession("clerk1"));
		MatterWork work = new MatterWork();
		WorkflowDocument wfd = work.getDocumentHeader().getWorkflowDocument();
		assertTrue("work flow document differs", wfd instanceof DummyWorkFlowDocument);
	}

	@Override
	public Class<?> getDataObjectClass() {
		return MatterWork.class;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#testInitiatorFYI()
	 
	@Override
	*//**
	 * does nothing, check {@link #testWorkFlowDocument()}
	 *//*
	public void testInitiatorFYI() {
		String initiator = "clerk1";
		String approver = "lawyer1";
		workflowDoc = WorkflowDocumentFactory.loadDocument(getPrincipalIdForName(initiator), work.getDocumentNumber());
		assertTrue("initiator and approver are different", workflowDoc.isFYIRequested());
	}*/
}