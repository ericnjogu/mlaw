/**
 * 
 */
package org.martinlaw.test;

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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.martinlaw.bo.MatterAssignee;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link MatterAssignee}
 * 
 * @author mugo
 * 
 */
// @BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
public class MatterAssigneeBOTest extends MartinlawTestsBase {

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testMatterMatterAssigneeNullableFields() {
		MatterAssignee assignment = new MatterAssignee();
		getBoSvc().save(assignment);
	}

	@Test
	/**
	 * test that the MatterAssignee is loaded into the data dictionary
	 */
	public void testMatterMatterAssigneeAttributes() {
		testBoAttributesPresent(MatterAssignee.class.getCanonicalName());
		Class<MatterAssignee> dataObjectClass = MatterAssignee.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from db - for data inserted via sql
	 */
	public void testMatterMatterAssigneeRetrieve() {
		// retrieve object populated via sql script
		MatterAssignee assignee = getBoSvc().findBySinglePrimaryKey(MatterAssignee.class, 1001l);
		assertNotNull("retrieved assignee should not be null", assignee);
		assertEquals("pauline_njogu", assignee.getPrincipalName());
		assertNotNull("matter should not be null", assignee.getMatter());
		
		assignee = getBoSvc().findBySinglePrimaryKey(MatterAssignee.class, 1002l);
		assertFalse("assignee is inactive", assignee.getActive());
		assertFalse("assignee has no physical file", assignee.getHasPhysicalFile());
		
		assignee = getBoSvc().findBySinglePrimaryKey(MatterAssignee.class, 1003l);
		assertTrue("assignee is active", assignee.getActive());
		assertFalse("assignee has no physical file", assignee.getHasPhysicalFile());
	}

	/**
	 * convenience method to test matter assignment CRUD
	 * 
	 * @param assignment - the BO to perform CRUD with
	 */
	public void testAssigneeCRUD(MatterAssignee assignee) {
		// C
		getBoSvc().save(getTestUtils().getTestAssignee());
		// R
		assignee = getBoSvc().findBySinglePrimaryKey(MatterAssignee.class,	1004l);
		getTestUtils().testAssigneeFields(assignee);
		
		// D
		getBoSvc().delete(assignee);
		assertNull("assignee should have been deleted", getBoSvc().findBySinglePrimaryKey(MatterAssignee.class,	assignee.getId()));
	}

}
