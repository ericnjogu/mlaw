/**
 * 
 */
package org.martinlaw.test.courtcase;

import static org.junit.Assert.assertEquals;

import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.bo.courtcase.LandCase;

/**
 * tests {@link LandCase}
 * @author mugo
 *
 */
public class LandCaseBOTest extends CourtCaseBoTestBase {

	private static final String LAND_REF = "LR GEN 1/1";

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.CourtCaseBoTestBase#getDataObjectClass()
	 */
	@Override
	public Class<? extends CourtCase> getDataObjectClass() {
		return LandCase.class;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.CourtCaseBoTestBase#getDataObjectPrimaryKey()
	 */
	@Override
	public Long getDataObjectPrimaryKey() {
		return 1004l;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.CourtCaseBoTestBase#getExpectedLookupCount()
	 */
	@Override
	public int getExpectedLookupCount() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.CourtCaseBoTestBase#testCourtCaseFields(org.martinlaw.bo.courtcase.CourtCase)
	 */
	@Override
	protected void testRetrievedCourtCaseFields(CourtCase kase) {
		super.testRetrievedCourtCaseFields(kase);
		assertEquals("land ref differs", "LR JOHN 3/16", ((LandCase)kase).getLandReference());
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.CourtCaseBoTestBase#getTestCourtCase(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected CourtCase getTestCourtCase(String localReference,
			String statusText, String caseTypeName, String courtReference,
			String parties) throws InstantiationException,
			IllegalAccessException {
		LandCase kase = (LandCase) super.getTestCourtCase(localReference, statusText, caseTypeName,
				courtReference, parties);
		kase.setLandReference(LAND_REF);
		
		return kase;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.CourtCaseBoTestBase#additionalTestsForCreatedCourtCase(org.martinlaw.bo.courtcase.CourtCase)
	 */
	@Override
	public void additionalTestsForCreatedCourtCase(CourtCase kase) {
		super.additionalTestsForCreatedCourtCase(kase);
		assertEquals("land ref differs", LAND_REF, ((LandCase)kase).getLandReference());
	}

}
