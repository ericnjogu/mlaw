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
import org.martinlaw.bo.courtcase.Assignee;
import org.martinlaw.bo.courtcase.Assignment;
import org.martinlaw.test.BaseAssignmentRoutingTest;

/**
 * tests routing for {@link Assignment}
 * @author mugo
 *
 */

public class CourtCaseAssignmentRoutingTest extends BaseAssignmentRoutingTest {
	@Test
	/**
	 * test that a CourtCase assignment maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testCourtCaseAssignmentMaintDocPerms() {
		testCreateMaintain(Assignment.class, "CourtCaseAssignmentMaintenanceDocument");
	}
	
	/**
	 * tests CourtCase assignment maintenance doc routing
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	@Test
	public void testCourtCaseAssignmentRouting() throws InstantiationException, IllegalAccessException {
		Assignment testAssignment = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		super.testAssignmentRouting(testAssignment, "CourtCaseAssignmentMaintenanceDocument");
	}
	
	/**
	 * tests CourtCase assignment maintenance doc search
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	@Test
	public void testCourtCaseAssignmentDocSearch() throws InstantiationException, IllegalAccessException {
		Assignment testAssignment = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		final String docType = "CourtCaseAssignmentMaintenanceDocument";
		super.testAssignmentRouting(testAssignment, docType);
		
		Assignment testAssignment2 = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		testAssignment2.setMatterId(1003l);
		super.testAssignmentRouting(testAssignment2, docType);
		
		runDocumentSearch(docType, "l2", "love*", "matter.localReference", "matter.name");
	}
}
