/**
 * 
 */
package org.martinlaw.test.contract;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012, 2013 Eric Njogu (kunadawa@gmail.com)
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
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.bo.MatterWork.DummyWorkFlowDocument;
import org.martinlaw.bo.contract.Work;
import org.martinlaw.test.WorkRoutingTestBase;



/**
 * tests routing and perms for {@link Work}
 * 
 * @author mugo
 *
 */
public class ContractWorkRoutingTest extends WorkRoutingTestBase {

	@Override
	public MatterTxDocBase getTxDoc() throws WorkflowException {
		return getTestUtils().populateMatterWork((MatterWork) KRADServiceLocatorWeb.getDocumentService().getNewDocument(getDocType()));
	}

	@Override
	public String getDocType() {
		return MartinlawConstants.DocTypes.CONTRACT_WORK;
	}
	
	@Override
	public void testDocSearch() {
		// TODO not yet impl as doc search is not activated for matter work
	}
	
	/**
	 * tests that getting work flow doc from newly instantiated bo returns the custom null work flow doc
	 */
	@Test
	public void testWorkFlowDocument_newBO() {
		GlobalVariables.setUserSession(new UserSession("clerk1"));
		Work work = new Work();
		WorkflowDocument wfd = work.getDocumentHeader().getWorkflowDocument();
		assertTrue("work flow document differs", wfd instanceof DummyWorkFlowDocument);
	}
	
	/*@Test
	*//**
	 * tests {@link org.martinlaw.bo.MatterWork#getPeriodToCompletion()}
	 *//*
	public void testGetPeriodToCompletion() {
		
	}*/
}
