/**
 * 
 */
package org.martinlaw.test.contract;


import org.junit.Test;
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
	
	@Test
	/**
	 * test that {@link Work} is loaded into the data dictionary
	 */
	public void testContractWorkDD() {
		testWorkDD(Constants.DocTypes.CONTRACT_WORK, Work.class);
	}
	
	/**
	 * test retrieving a {@link Work} that has been created through sql
	 */
	@Test
	public void testContractWorkRetrieve() {
		testWorkRetrieve(Work.class);
	}
	
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
	
	@Test
	/**
	 * tests {@link org.martinlaw.bo.MatterWork#isMatterIdValid()}
	 */
	public void testMatterIdValidity() {
		Work contractWork = new Work();
		testMatterIdValidity(contractWork);
	}
	
}
