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




import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.exception.ValidationException;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.martinlaw.bo.courtcase.Event;
import org.martinlaw.test.KewTestsBase;

/**
 * tests routing for {@link Event}
 * @author mugo
 *
 */


public class CourtCaseEventRoutingTest extends KewTestsBase {
	@Test
	/**
	 * test that a CourtCase Date maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testCourtCaseDateMaintDocPerms() {
		testCreateMaintain(Event.class, "CourtCaseEventMaintenanceDocument");
	}
	
	/**
	 * tests CourtCase Date maintenance doc routing
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws WorkflowException 
	 * 
	 */
	@Test
	public void testCourtCaseDateRouting() throws InstantiationException, IllegalAccessException, WorkflowException {
		Event testDate = getTestUtils().getTestMatterEvent(Event.class);
		super.testMaintenanceRouting("CourtCaseEventMaintenanceDocument", testDate);
	}
	
	/**
	 * verifies that the business rules create an error for a non existent court case id on save
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws WorkflowException
	 */
	@Test(expected=ValidationException.class)
	public void testCourtCaseDateRouting_InvalidMatterId() throws InstantiationException, IllegalAccessException, WorkflowException {
		Event testDate = getTestUtils().getTestMatterEvent(Event.class);
		testDate.setMatterId(3000l);
		
		//initiate as the clerk
		Document doc = getPopulatedMaintenanceDocument("CourtCaseEventMaintenanceDocument", testDate, "clerk1");
		KRADServiceLocatorWeb.getDocumentService().saveDocument(doc);
	}
	
	/**
	 * verifies that the required fields are validated on route not save
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws WorkflowException
	 */
	@Test
	public void testCourtCaseDateRouting_required_validated_onroute() throws InstantiationException, WorkflowException, IllegalAccessException {
		Event testDate = getTestUtils().getTestMatterEvent(Event.class);
		// required on route
		testDate.setStartDate(null);
		testDate.setTypeId(null);
		
		//initiate as the clerk
		Document doc = getPopulatedMaintenanceDocument("CourtCaseEventMaintenanceDocument", testDate, "clerk1");
		testRouting_required_validated_onroute(doc);
	}
}
