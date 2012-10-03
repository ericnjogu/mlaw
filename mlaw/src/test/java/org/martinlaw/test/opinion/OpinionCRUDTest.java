/**
 * 
 */
package org.martinlaw.test.opinion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.opinion.Opinion;
import org.martinlaw.bo.opinion.OpinionClient;
import org.martinlaw.bo.opinion.OpinionFee;
import org.martinlaw.test.MartinlawTestsBase;

/**
 * various CRUD tests for {@link Opinion} and dependent objects
 * @author mugo
 *
 */
public class OpinionCRUDTest extends MartinlawTestsBase {

	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-test-data.sql", ";").runSql();
	}
	
	/**
	 * tests that the data inserted via sql can be retrieved ok
	 */
	@Test
	public void testOpinionRetrieve() {
		Opinion opinion = getBoSvc().findBySinglePrimaryKey(Opinion.class, 1001l);
		assertNotNull("opinion should have been found", opinion);
		assertEquals("opinion name differs", "legal opinion regarding the sale of brown elephant", opinion.getName());
		
		assertNotNull("opinion status should not be null", opinion.getStatus());
		
		assertNotNull("opinion clients should not be null", opinion.getClients());
		assertEquals("opinion clients not the expected number", 2, opinion.getClients().size());
		assertEquals("opinion client name not the expected value", "jnn", opinion.getClients().get(0).getPrincipalName());
		
		assertNotNull("opinion fees should not be null", opinion.getFees());
		assertEquals("opinion fees not the expected number", 2, opinion.getFees().size());
		assertEquals("opinion fee name not the expected value", "received from znm", opinion.getFees().get(0).getDescription());
		getTestUtils().testAssignees(opinion.getAssignees());
	}
	
	/**
	 * tests  {@link Opinion} CRUD
	 */
	@Test
	public void testOpinionCRUD() {
		// C
		Opinion opinion = getTestUtils().getTestOpinion();
		
		getBoSvc().save(opinion);
		
		// R
		opinion.refresh();
		getTestUtils().testOpinionFields(opinion);
		
		// U
		String summary = "see attached file";
		opinion.setSummary(summary);
		opinion.refresh();
		assertEquals("opinion summary differs", summary, opinion.getSummary());
		
		// D
		getBoSvc().delete(opinion);
		assertNull("opinion should not exist", getBoSvc().findBySinglePrimaryKey(Opinion.class, opinion.getId()));
		Map<String, Object> criteria = new HashMap<String, Object>(1);
		criteria.put("matterId", opinion.getId());
		assertEquals("opinion clients should have been deleted", 0, getBoSvc().findMatching(OpinionClient.class, criteria).size());
		assertEquals("opinion fees should have been deleted", 0, getBoSvc().findMatching(OpinionFee.class, criteria).size());
	}
	
}
