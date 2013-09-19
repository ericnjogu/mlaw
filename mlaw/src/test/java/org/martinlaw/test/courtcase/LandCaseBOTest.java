/**
 * 
 */
package org.martinlaw.test.courtcase;

import static org.junit.Assert.assertEquals;

import org.martinlaw.bo.Matter;
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
	public Class<? extends Matter> getDataObjectClass() {
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
	 * @see org.martinlaw.test.courtcase.CourtCaseBoTestBase#getTestCourtCase(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected CourtCase getTestMatter(String localReference,
			String statusText, String matterName) throws InstantiationException,
			IllegalAccessException {
		LandCase kase = (LandCase) super.getTestMatter(localReference, statusText, matterName);
		kase.setLandReference(LAND_REF);
		
		return kase;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.CourtCaseBoTestBase#additionalTestsForCreatedCourtCase(org.martinlaw.bo.courtcase.CourtCase)
	 */
	@Override
	public void additionalTestsForCreatedMatter(Matter kase) {
		super.additionalTestsForCreatedMatter(kase);
		assertEquals("land ref differs", LAND_REF, ((LandCase)kase).getLandReference());
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.CourtCaseBoTestBase#testRetrievedMatterFields(org.martinlaw.bo.Matter)
	 */
	@Override
	public void testRetrievedMatterFields(Matter matter) {
		super.testRetrievedMatterFields(matter);
		assertEquals("land ref differs", "LR JOHN 3/16", ((LandCase)matter).getLandReference());
	}
}
