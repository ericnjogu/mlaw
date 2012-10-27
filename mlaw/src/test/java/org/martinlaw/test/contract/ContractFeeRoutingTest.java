/**
 * 
 */
package org.martinlaw.test.contract;

import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.martinlaw.Constants;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.bo.contract.ClientFee;

/**
 * tests routing for {@link ClientFee}
 * @author mugo
 *
 */
public class ContractFeeRoutingTest extends ContractTxRoutingTestBase {
	/* (non-Javadoc)
	 * @see org.martinlaw.test.MartinlawTestsBase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		setDocType(Constants.DocTypes.CONTRACT_FEE);
		setWorkDoc((MatterTxDocBase) KRADServiceLocatorWeb.getDocumentService().getNewDocument(getDocType()));
	}

}
