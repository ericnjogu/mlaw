package org.martinlaw.test.contract;

import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.test.MartinlawTestsBase;
/**
 * acts as a base class for {@link Contract} supporting objects
 * 
 * @author mugo
 *
 */
public abstract class ContractBoTestBase extends MartinlawTestsBase {

	public ContractBoTestBase() {
		super();
	}

	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-type-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-test-data.sql", ";").runSql();
	}

}