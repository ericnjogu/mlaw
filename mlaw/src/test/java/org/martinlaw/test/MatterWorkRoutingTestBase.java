package org.martinlaw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.kuali.rice.kew.api.WorkflowDocument;
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

/**
 * holds common tests for {@link org.martinlaw.bo.MatterWork}
 * @author mugo
 *
 */
public abstract class MatterWorkRoutingTestBase extends TxRoutingTestBase {

	public MatterWorkRoutingTestBase() {
		super();
	}
	
	/**
	 * confirms that the custom workflow document field is populated when documents are retrieved from the document service
	 * <p>tests the code in {@link org.martinlaw.bo.MatterWork#setDocumentHeader(DocumentHeader)}
	 */
	@Test
	public void testWorkFlowDocument() throws WorkflowException {
		// save
		String initiator = "clerk1";
		GlobalVariables.setUserSession(new UserSession(initiator));
		MatterWork work = (MatterWork) getTxDoc();
		// bo does not exist until it is saved - initiated docs will not show up on the inquiry
		/*MatterWork bo = getBoSvc().findBySinglePrimaryKey(work.getClass(), work.getDocumentHeader());
		assertNotNull("for initiated doc, workflow document should not be null", bo.getDocumentHeader().getWorkflowDocument());*/
		
		work = (MatterWork)KRADServiceLocatorWeb.getDocumentService().saveDocument(work);
		MatterWork bo = getBoSvc().findBySinglePrimaryKey(work.getClass(), work.getDocumentNumber());
		assertNotNull("for saved doc, workflow document should not be null", bo.getDocumentHeader().getWorkflowDocument());
		assertEquals("principal name differs", initiator, bo.getDocumentInitiatorNetworkId());
		work = (MatterWork) KRADServiceLocatorWeb.getDocumentService().routeDocument(work, "approved", null);
		
		String approver = "lawyer1";
		GlobalVariables.setUserSession(new UserSession(approver));
		KRADServiceLocatorWeb.getDocumentService().approveDocument(work, "approved", null);
		
		//retrieve again to confirm status
		bo = getBoSvc().findBySinglePrimaryKey(work.getClass(), work.getDocumentNumber());
		assertNotNull("for final doc, workflow document should not be null", bo.getDocumentHeader().getWorkflowDocument());
		assertEquals("principal name differs", initiator, bo.getDocumentInitiatorNetworkId());
		assertFalse("completion period should not be empty", StringUtils.isEmpty( bo.getPeriodToCompletion()));
		assertFalse("approval period should not be empty", StringUtils.isEmpty( bo.getPeriodToApprove()));
		
		work.refreshNonUpdateableReferences();
		assertNotNull("work type should not be null", work.getAnnexType());
		assertEquals("default annex type differs", MartinlawConstants.DEFAULT_ANNEX_TYPE_ID, work.getAnnexTypeId());
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
	public void testDocSearch() {
		// TODO not yet impl as doc search is not activated for matter work
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
}