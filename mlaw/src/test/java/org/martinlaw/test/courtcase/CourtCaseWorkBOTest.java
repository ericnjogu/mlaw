/**
 * 
 */
package org.martinlaw.test.courtcase;


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
	
	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-work-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-assignment-test-data.sql", ";").runSql();
	}
	
	/* (non-Javadoc)
	 * @see org.martinlaw.test.MartinlawTestsBase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		setWork(new Work());
		setWorkClass(Work.class);
		setDocType(Constants.DocTypes.COURTCASE_WORK);
	}
}
