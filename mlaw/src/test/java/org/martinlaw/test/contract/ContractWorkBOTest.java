/**
 * 
 */
package org.martinlaw.test.contract;


import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.Constants;
import org.martinlaw.bo.contract.Work;
import org.martinlaw.test.WorkBOTestBase;

/**
 * tests CRUD and data dictionary of {@link Work}
 * 
 * @author mugo
 *
 */
public class ContractWorkBOTest extends WorkBOTestBase {
	
	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		// since not derived from ContractBoTestBase, all dependent data needs to be included here
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-type-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-assignment-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-work-test-data.sql", ";").runSql();
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.MartinlawTestsBase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		setWork(new Work());
		setWorkClass(Work.class);
		setDocType(Constants.DocTypes.CONTRACT_WORK);
	}
	
	
	
}
