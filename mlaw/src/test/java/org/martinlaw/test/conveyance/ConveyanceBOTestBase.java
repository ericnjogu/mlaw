package org.martinlaw.test.conveyance;

import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.test.MartinlawTestsBase;
/**
 * holds the common configurations for Conveyance related BO tests
 * 
 * @author mugo
 *
 */
public abstract class ConveyanceBOTestBase extends MartinlawTestsBase {

	public ConveyanceBOTestBase() {
		super();
	}

	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-test-data.sql", ";").runSql();
	}

}