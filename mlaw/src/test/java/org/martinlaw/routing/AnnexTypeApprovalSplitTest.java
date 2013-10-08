/**
 * 
 */
package org.martinlaw.routing;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.node.SplitResult;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.MatterAnnexType;
import org.martinlaw.bo.MatterWork;

import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.when;

/**
 *  Tests {@link org.martinlaw.routing.AnnexTypeApprovalSplit}
 * @author mugo
 *
 */
public class AnnexTypeApprovalSplitTest {

	private AnnexTypeApprovalSplit annexTypeApprovalSplit;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		annexTypeApprovalSplit = new AnnexTypeApprovalSplit();
	}

	/**
	 * Test method for {@link org.martinlaw.routing.AnnexTypeApprovalSplit#process(org.kuali.rice.kew.engine.RouteContext, org.kuali.rice.kew.engine.RouteHelper)}.
	 * @throws Exception 
	 */
	@Test
	public void testProcess() throws Exception {
		SplitResult result = annexTypeApprovalSplit.process(null, null);
		assertNotNull("split result should not be null", result);
		assertTrue("branch list should be empty", result.getBranchNames().isEmpty());
		
		BusinessObjectService boSvc = mock(BusinessObjectService.class);
		annexTypeApprovalSplit.setBusinessObjectService(boSvc);
		
		RouteContext context = mock(RouteContext.class);
		DocumentRouteHeaderValue docHdr = mock(DocumentRouteHeaderValue.class);
		when(context.getDocument()).thenReturn(docHdr);
		
		MatterWork work = mock(MatterWork.class);
		MatterAnnexType annexType = new MatterAnnexType();
		when(work.getAnnexType()).thenReturn(annexType);
		
		final String documentId = "2013";
		when(docHdr.getDocumentId()).thenReturn(documentId);
		when(boSvc.findBySinglePrimaryKey(same(MatterWork.class), same(documentId))).thenReturn(work);
		assertTrue("branch list should be empty since requiresApproval is by default false",  
				annexTypeApprovalSplit.process(context, null).getBranchNames().isEmpty());
		
		annexType.setRequiresApproval(true);
		final List<String> branchNames = annexTypeApprovalSplit.process(context, null).getBranchNames();
		assertFalse("branch list should not be empty since requiresApproval is set to true", branchNames.isEmpty());
		assertEquals("branch name differs", MartinlawConstants.RoutingBranches.ANNEX_TYPE_APPROVAL, branchNames.get(0));
		
		when(work.getAnnexType()).thenReturn(null);
		assertTrue("branch list should be empty since annex type is null",  
				annexTypeApprovalSplit.process(context, null).getBranchNames().isEmpty());
		
		when(docHdr.getDocumentId()).thenReturn("none existent doc id");
		assertTrue("branch list should be empty since document does not exist",  
				annexTypeApprovalSplit.process(context, null).getBranchNames().isEmpty());
	}

}
