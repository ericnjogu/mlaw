/**
 * 
 */
package org.martinlaw.test.conveyance;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012 Eric Njogu (kunadawa@gmail.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */



import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.martinlaw.MartinlawConstants;
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
		setDocType(MartinlawConstants.DocTypes.CONVEYANCE_WORK);
		Work newDocument = (Work) KRADServiceLocatorWeb.getDocumentService().getNewDocument(getDocType());
		newDocument.setConveyanceAnnexTypeId(1001l);
		setWorkDoc(getTestUtils().populateMatterWork(newDocument));
	}
	
	/**
	 * confirms that the documents that are routed/submitted directly while using 
	 * {@link org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor} do not have an ojb error
	 */
	@Test
	
	public void testInitiateToRouteDirectly() throws WorkflowException {
		/*KRADServiceLocatorWeb.getDocumentService().saveDocument(getWorkDoc());
		Work doc = (Work) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(getWorkDoc().getDocumentNumber());
		assertNotNull("annex type id should not be null", doc.getConveyanceAnnexTypeId());
		assertFalse("the status should not be final", doc.getStatusIsFinal());*/
		//getBoSvc().save(doc);
		KRADServiceLocatorWeb.getDocumentService().routeDocument(getWorkDoc(), "submitted", null);
		//retrieve again to confirm status
		Work doc = (Work) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(getWorkDoc().getDocumentNumber());
		assertTrue("document should be final", doc.getDocumentHeader().getWorkflowDocument().isEnroute());
	}
}
