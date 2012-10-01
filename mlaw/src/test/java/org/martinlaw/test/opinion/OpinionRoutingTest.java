/**
 * 
 */
package org.martinlaw.test.opinion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.opinion.Opinion;
import org.martinlaw.test.KewTestsBase;

/**
 * tests routing for {@link Opinion}
 * @author mugo
 *
 */
public class OpinionRoutingTest extends KewTestsBase {
	private Log log = LogFactory.getLog(getClass());

	@Test
	/**
	 * test that OpinionMaintenanceDocument routes to clerk then lawyer on submit
	 */
	public void testOpinionRouting() {
		Opinion testOpinion = getTestUtils().getTestOpinion();
		try {
			testMaintenanceRouting("OpinionMaintenanceDocument", testOpinion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("test failed", e);
			fail("test routing OpinionMaintenanceDocument caused an exception");
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("localReference", getTestUtils().getTestOpinionLocalReference());
		Collection<Opinion> result = getBoSvc().findMatching(Opinion.class, params);
		assertEquals(1, result.size());
		for (Opinion opinion: result) {
			getTestUtils().testOpinionFields(opinion);
		}
	}
	
	@Test
	/**
	 * test that a opinion maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testOpinionTypeMaintDocPerms() {
		testCreateMaintain(Opinion.class, "OpinionMaintenanceDocument");
	}
	
	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-perms-roles.sql", ";").runSql();
		// for the status id
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
	}
}
