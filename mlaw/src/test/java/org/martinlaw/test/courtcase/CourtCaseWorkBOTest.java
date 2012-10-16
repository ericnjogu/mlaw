/**
 * 
 */
package org.martinlaw.test.courtcase;


import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.Constants;
import org.martinlaw.bo.courtcase.Work;
import org.martinlaw.test.WorkBOTestBase;

/**
 * tests CRUD and data dictionary of {@link Work}
 * 
 * @author mugo
 *
 */
public class CourtCaseWorkBOTest extends WorkBOTestBase {
	
	@Test
	/**
	 * test that {@link Work} is loaded into the data dictionary
	 */
	public void testCourtCaseWorkDD() {
		testWorkDD(Constants.DocTypes.COURTCASE_WORK, Work.class);
	}
	
	/**
	 * test retrieving a {@link Work} that has been created through sql
	 */
	@Test
	public void testCourtCaseWorkRetrieve() {
		testWorkRetrieve(Work.class);
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts//court-case-work-test-data.sql", ";").runSql();
	}
	
	@Test
	/**
	 * tests {@link org.martinlaw.bo.MatterWork#isMatterIdValid()}
	 */
	public void testMatterIdValidity() {
		Work courtcaseWork = new Work();
		testMatterIdValidity(courtcaseWork);
	}
	
}
