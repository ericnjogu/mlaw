package org.martinlaw.test.courtcase;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kew.api.exception.WorkflowException;
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.test.MatterRoutingTest;
import org.martinlaw.util.SearchTestCriteria;

/**
 * base test class for {@link CourtCase} and children
 * @author mugo
 *
 */

public abstract class CourtCaseRoutingTestBase extends MatterRoutingTest {

	final String courtReference = "Kisii High Court Petition No. 6 of 2013";

	public CourtCaseRoutingTestBase() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.MatterRoutingTest#testCreatedCase(org.martinlaw.bo.courtcase.CourtCase)
	 */
	@Override
	protected void testCreatedMatter(Matter kase) {
		super.testCreatedMatter(kase);
		CourtCase kase1 = (CourtCase)kase;
		assertEquals("court reference differs", courtReference, kase1.getCourtReference());
		assertEquals("number of witnesses expected differs", 1, kase1.getWitnesses().size());
		String msg = "expected principal name differs";
		assertEquals(msg, "thomas_kaberi_gitau", kase1.getWitnesses().get(0).getPrincipalName());
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.MatterRoutingTest#getTestMatter()
	 */
	@Override
	protected Matter getTestMatter() throws InstantiationException,
			IllegalAccessException {
		return getTestUtils().getTestCourtCase(getLocalReference(), courtReference, getDataObjectClass());
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.MatterRoutingTest#getDataObjectClass()
	 */
	@Override
	public abstract Class<? extends CourtCase> getDataObjectClass();

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.MatterRoutingTest#testCourtCase_doc_search()
	 */
	@Override
	public void testMatter_doc_search() throws WorkflowException, InstantiationException, IllegalAccessException {
		super.testMatter_doc_search();
		
		final String courtRef = "courtRef1";
		CourtCase kase = (CourtCase) getTestMatter();
		kase.setCourtReference(courtRef);
		testMaintenanceRoutingInitToFinal(getDocTypeName(), kase);
		
		SearchTestCriteria crit1 = new SearchTestCriteria();
		crit1.setExpectedDocuments(1);
		crit1.getFieldNamesToSearchValues().put("courtReference", courtRef);
		List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
		crits.add(crit1);
		getTestUtils().runDocumentSearch(crits, getDocTypeName());
	}
}