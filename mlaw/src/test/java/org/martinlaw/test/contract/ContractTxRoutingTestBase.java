package org.martinlaw.test.contract;

import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.test.TxRoutingTestBase;
/**
 * base class for testing transactional documents that reference {@link Contract}
 * 
 * @author mugo
 *
 */
public class ContractTxRoutingTestBase extends TxRoutingTestBase {

	public ContractTxRoutingTestBase() {
		super();
	}

	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		GlobalVariables.setUserSession(new UserSession("clerk1"));
	}

	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-type-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-test-data.sql", ";").runSql();
	}

}