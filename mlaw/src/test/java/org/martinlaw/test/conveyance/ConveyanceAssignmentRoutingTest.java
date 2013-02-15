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




import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.conveyance.Assignee;
import org.martinlaw.bo.conveyance.Assignment;
import org.martinlaw.test.BaseAssignmentRoutingTest;
import org.kuali.rice.test.BaselineTestCase;

/**
 * tests routing for {@link Assignment}
 * @author mugo
 *
 */
// @BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
@SuppressWarnings("unused")
public class ConveyanceAssignmentRoutingTest extends BaseAssignmentRoutingTest {
	@Test
	/**
	 * test that a conveyance assignment maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testConveyanceAssignmentMaintDocPerms() {
		testCreateMaintain(Assignment.class, "ConveyanceAssignmentMaintenanceDocument");
	}
	
	/**
	 * tests Conveyance assignment maintenance doc routing
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	@Test
	public void testConveyanceAssignmentRouting() throws InstantiationException, IllegalAccessException {
		Assignment testAssignment = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		super.testAssignmentRouting(testAssignment, "ConveyanceAssignmentMaintenanceDocument");
	}
	
	/**
	 * tests CourtCase assignment maintenance doc search
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	@Test
	public void testConveyanceAssignmentDocSearch() throws InstantiationException, IllegalAccessException {
		Assignment testAssignment = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		final String docType = "ConveyanceAssignmentMaintenanceDocument";
		super.testAssignmentRouting(testAssignment, docType);
		
		Assignment testAssignment2 = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		testAssignment2.setMatterId(1003l);
		super.testAssignmentRouting(testAssignment2, docType);
		
		runAssignmentDocumentSearch(docType, "c2", "transfer*");
	}
}
