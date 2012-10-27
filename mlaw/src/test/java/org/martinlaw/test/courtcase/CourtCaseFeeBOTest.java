/**
 * 
 */
package org.martinlaw.test.courtcase;



import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.Constants;
import org.martinlaw.bo.courtcase.ClientFee;
import org.martinlaw.bo.courtcase.Fee;
import org.martinlaw.test.MatterFeeBOTest;

/**
 * tests DD and CRUD for {@link ClientFee}
 * @author mugo
 *
 */
public class CourtCaseFeeBOTest extends MatterFeeBOTest {
	
	/**
	 * default constructor
	 */
	public CourtCaseFeeBOTest() {
		setDocType(Constants.DocTypes.COURTCASE_FEE);
		setDocumentClass(ClientFee.class);
		setViewId(Constants.ViewIds.COURTCASE_FEE);
		setFeeClass(Fee.class);
	}
	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-fee-test-data.sql", ";").runSql();
	}
	
	
}
