/**
 * 
 */
package org.martinlaw.test.contract;

import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.Constants;
import org.martinlaw.bo.MatterTxDocBase;



/**
 * tests routing and perms for {@link Work}
 * 
 * @author mugo
 *
 */
public class ContractWorkRoutingTest extends ContractTxRoutingTestBase {

	/* (non-Javadoc)
	 * @see org.martinlaw.test.contract.ContractTxRoutingTestBase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		setDocType(Constants.DocTypes.CONTRACT_WORK);
		setWorkDoc((MatterTxDocBase) KRADServiceLocatorWeb.getDocumentService().getNewDocument(getDocType()));
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.contract.ContractTxRoutingTestBase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-assignment-test-data.sql", ";").runSql();
	}
}
