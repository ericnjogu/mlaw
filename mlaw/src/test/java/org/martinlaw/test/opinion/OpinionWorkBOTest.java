/**
 * 
 */
package org.martinlaw.test.opinion;


import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.Constants;
import org.martinlaw.bo.opinion.Work;
import org.martinlaw.test.WorkBOTestBase;

/**
 * tests CRUD and data dictionary of {@link Work}
 * 
 * @author mugo
 *
 */
public class OpinionWorkBOTest extends WorkBOTestBase {
	
	@Test
	/**
	 * test that {@link Work} is loaded into the data dictionary
	 */
	public void testOpinionWorkDD() {
		testWorkDD(Constants.DocTypes.OPINION_WORK, Work.class);
	}
	
	/**
	 * test retrieving a {@link Work} that has been created through sql
	 */
	@Test
	public void testOpinionWorkRetrieve() {
		testWorkRetrieve(Work.class);
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-work-test-data.sql", ";").runSql();
	}
	
	@Test
	/**
	 * tests {@link org.martinlaw.bo.MatterWork#isMatterIdValid()}
	 */
	public void testMatterIdValidity() {
		Work work = new Work();
		testMatterIdValidity(work);
	}
	
}
