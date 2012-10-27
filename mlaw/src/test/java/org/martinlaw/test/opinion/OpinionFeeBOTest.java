/**
 * 
 */
package org.martinlaw.test.opinion;



import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.Constants;
import org.martinlaw.bo.opinion.ClientFee;
import org.martinlaw.bo.opinion.Fee;
import org.martinlaw.test.MatterFeeBOTest;

/**
 * tests DD and CRUD for {@link ClientFee}
 * @author mugo
 *
 */
public class OpinionFeeBOTest extends MatterFeeBOTest {
	
	/**
	 * default constructor
	 */
	public OpinionFeeBOTest() {
		setDocType(Constants.DocTypes.OPINION_FEE);
		setDocumentClass(ClientFee.class);
		setViewId(Constants.ViewIds.OPINION_FEE);
		setFeeClass(Fee.class);
	}
	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-fee-test-data.sql", ";").runSql();
	}
	
	
}
