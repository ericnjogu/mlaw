/**
 * 
 */
package org.martinlaw.test.courtcase;

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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.exception.ValidationException;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.martinlaw.bo.courtcase.Client;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.test.KewTestsBase;

/**
 * @author mugo
 *
 */
public class CourtCaseRoutingTest extends KewTestsBase {
	private Logger log = Logger.getLogger(getClass());
	final String localReference = "local-ref-1";
	final String courtReference = "high-court-211";
	
	@Test
	/**
	 * test that routing a court case maintenance document works as expected
	 * 
	 * @throws WorkflowException
	 */
	public void testCaseMaintenanceRouting() throws WorkflowException {
		try {
			testMaintenanceRouting("CaseMaintenanceDocument", getTestUtils().getTestCourtCase(localReference, courtReference));
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("localReference", localReference);
			Collection<CourtCase> cases = KRADServiceLocator.getBusinessObjectService().findMatching(CourtCase.class, params);
			assertEquals(1, cases.size());
			for (CourtCase cse: cases) {
				assertEquals(localReference,cse.getLocalReference());
				assertEquals(courtReference,cse.getCourtReference());
				assertNotNull(cse.getStatus());
				log.info("created status with id " + cse.getStatus().getId());
			}
		} catch (Exception e) {
			log.error("test failed", e);
			fail("test failed");
		}
	}
	
	/**
	 * verifies that the collection required fields are validated on route not save
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws WorkflowException
	 */
	@Test
	public void testCourtCaseRouting_required_collection_validated_onroute() throws InstantiationException, WorkflowException, IllegalAccessException {
		CourtCase courtCase = getTestUtils().getTestCourtCase(localReference, courtReference);
		// create client with blank name and add to collection
		Client client = new Client();
		client.setPrincipalName(null);
		courtCase.getClients().add(client);
		GlobalVariables.getMessageMap().clearErrorMessages();
		//initiate as the clerk
		Document doc = getPopulatedMaintenanceDocument("CaseMaintenanceDocument", courtCase);
		testRouting_required_validated_onroute(doc);
	}
	
	/**
	 * verifies that the collection required fields are validated on route not save
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws WorkflowException
	 */
	@Test
	public void testCourtCaseRouting_required_field_validated_onroute() throws InstantiationException, WorkflowException, IllegalAccessException {
		CourtCase courtCase = getTestUtils().getTestCourtCase(localReference, courtReference);
		courtCase.setLocalReference(null);
		//initiate as the clerk
		Document doc = getPopulatedMaintenanceDocument("CaseMaintenanceDocument", courtCase);
		testRouting_required_validated_onroute(doc);
	}
	
	/**
	 * common method to verify that required fields are validated on route not save
	 * @param doc - the document to save and route
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws WorkflowException
	 */
	public void testRouting_required_validated_onroute(Document doc) throws InstantiationException, WorkflowException, IllegalAccessException {
		try {
			KRADServiceLocatorWeb.getDocumentService().saveDocument(doc);
		} catch (ValidationException ve) {
			fail("should not have thrown a validation exception on save");
		}
		try {
			KRADServiceLocatorWeb.getDocumentService().routeDocument(doc, "submitted", null);
			fail("should have thrown validation exception on route");
		} catch (ValidationException e) {
			// test succeeded
		}
	}
	
	@After
	/**
	 * cleans up after test
	 */
	public void cleanup() {
		GlobalVariables.getMessageMap().clearErrorMessages();
	}
}
