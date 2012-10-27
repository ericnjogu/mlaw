/**
 * 
 */
package org.martinlaw.test.contract;



import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.Constants;
import org.martinlaw.bo.contract.ClientFee;
import org.martinlaw.bo.contract.Fee;
import org.martinlaw.test.MatterFeeBOTest;

/**
 * tests DD and CRUD for {@link ClientFee}
 * @author mugo
 *
 */
public class ContractFeeBOTest extends MatterFeeBOTest {
	
	/**
	 * default constructor
	 */
	public ContractFeeBOTest() {
		setDocType(Constants.DocTypes.CONTRACT_FEE);
		setDocumentClass(ClientFee.class);
		setViewId(Constants.ViewIds.CONTRACT_FEE);
		setFeeClass(Fee.class);
	}
	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-type-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-fee-test-data.sql", ";").runSql();
	}
	
	
}
