/**
 * 
 */
package org.martinlaw.test.conveyance;



import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.Constants;
import org.martinlaw.bo.conveyance.ClientFee;
import org.martinlaw.bo.conveyance.Fee;
import org.martinlaw.test.MatterFeeBOTest;

/**
 * tests DD and CRUD for {@link ClientFee}
 * @author mugo
 *
 */
public class ConveyanceFeeBOTest extends MatterFeeBOTest {
	
	/**
	 * default constructor
	 */
	public ConveyanceFeeBOTest() {
		setDocType(Constants.DocTypes.CONVEYANCE_FEE);
		setDocumentClass(ClientFee.class);
		setViewId(Constants.ViewIds.CONVEYANCE_FEE);
		setFeeClass(Fee.class);
	}
	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-fee-test-data.sql", ";").runSql();
	}
	
	
}
