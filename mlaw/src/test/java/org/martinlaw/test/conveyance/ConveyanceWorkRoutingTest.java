/**
 * 
 */
package org.martinlaw.test.conveyance;


import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.Constants;
import org.martinlaw.bo.conveyance.Work;
import org.martinlaw.test.TxRoutingTestBase;

/**
 * tests routing and perms for {@link Work}
 * 
 * @author mugo
 *
 */
public class ConveyanceWorkRoutingTest extends TxRoutingTestBase {
	/* (non-Javadoc)
	 * @see org.martinlaw.test.MartinlawTestsBase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		GlobalVariables.setUserSession(new UserSession("clerk1"));
		setDocType(Constants.DocTypes.CONVEYANCE_WORK);
		Work newDocument = (Work) KRADServiceLocatorWeb.getDocumentService().getNewDocument(getDocType());
		newDocument.setConveyanceAnnexTypeId(1001l);
		setWorkDoc(newDocument);
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
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-assignment-test-data.sql", ";").runSql();
	}
}
