/**
 * 
 */
package org.martinlaw.test.opinion;


import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.Constants;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.test.WorkRoutingTestBase;

/**
 * tests routing and perms for {@link Work}
 * 
 * @author mugo
 *
 */
public class OpinionWorkRoutingTest extends WorkRoutingTestBase {
	/* (non-Javadoc)
	 * @see org.martinlaw.test.MartinlawTestsBase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		GlobalVariables.setUserSession(new UserSession("clerk1"));
		docType = Constants.DocTypes.OPINION_WORK;
		workDoc = (MatterWork) KRADServiceLocatorWeb.getDocumentService().getNewDocument(docType);
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#loadSuiteTestData()
	 */
	/**
	 * needed to check for matter id validity
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-test-data.sql", ";").runSql();
	}
}
