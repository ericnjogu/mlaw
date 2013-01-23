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
import org.kuali.rice.krad.exception.ValidationException;
import org.martinlaw.bo.courtcase.MyDate;
import org.martinlaw.test.KewTestsBase;

/**
 * tests routing for {@link MyDate}
 * @author mugo
 *
 */


public class CourtCaseDateRoutingTest extends KewTestsBase {
	@Test
	/**
	 * test that a CourtCase Date maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testCourtCaseDateMaintDocPerms() {
		testCreateMaintain(MyDate.class, "CourtCaseDateMaintenanceDocument");
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
		MyDate testDate = getTestUtils().getTestMatterDate(MyDate.class);
		super.testMaintenanceRouting("CourtCaseDateMaintenanceDocument", testDate);
	}
	
	/**
	 * verifies that the business rules create an error for a non existent court case id 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws WorkflowException
	 */
	@Test(expected=ValidationException.class)
	public void testCourtCaseDateRouting_InvalidMatterId() throws InstantiationException, IllegalAccessException, WorkflowException {
		MyDate testDate = getTestUtils().getTestMatterDate(MyDate.class);
		testDate.setMatterId(3000l);
		super.testMaintenanceRouting("CourtCaseDateMaintenanceDocument", testDate);
	}
}
