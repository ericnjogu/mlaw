/**
 * 
 */
package org.martinlaw.test.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.test.SQLDataLoader;
import org.kuali.rice.test.lifecycles.KEWXmlDataLoaderLifecycle;
import org.martinlaw.bo.DateType;
import org.martinlaw.test.KewTestsBase;

/**
 * tests routing for {@link DateType}
 * @author mugo
 *
 */
public class DateTypeRoutingTest extends KewTestsBase {
	@Test
	/**
	 * test that ConveyanceAnnexTypeDocument routes to final on submit
	 */
	public void testDateTypeRouting() {
		DateType dateType = new DateType();
		String name = "submissions";
		dateType.setName(name);
		try {
			testMaintenanceRoutingInitToFinal("DateTypeMaintenanceDocument", dateType);
		} catch (Exception e) {
			log.error("test failed", e);
			fail("test routing DateTypeMaintenanceDocument caused an exception");
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		Collection<DateType> result = getBoSvc().findMatching(DateType.class, params);
		assertEquals(1, result.size());
	}
	
	@Test
	/**
	 * test that a conveyance maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testDateTypeMaintDocPerms() {
		testCreateMaintain(DateType.class, "DateTypeMaintenanceDocument");
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#getSuiteLifecycles()
	 */
	/**
	 * provide the document type definition file.
	 */
	@Override
	protected List<Lifecycle> getSuiteLifecycles() {
		List<Lifecycle> suiteLifecycles = super.getSuiteLifecycles();
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/dateType.xml"));
		return suiteLifecycles;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/date-type-perms-roles.sql", ";").runSql();
	}
}
