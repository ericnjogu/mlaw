/**
 * 
 */
package org.martinlaw.test.contract;

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
import org.martinlaw.bo.contract.Assignee;
import org.martinlaw.bo.contract.Assignment;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link Assignment}
 * 
 * @author mugo
 * 
 */
// @BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
public class ContractAssignmentBOTest extends ContractBoTestBase {

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testContractAssignmentNullableFields() {
		Assignment assignment = new Assignment();
		getBoSvc().save(assignment);
	}

	@Test
	/**
	 * test that the Assignment is loaded into the data dictionary
	 */
	public void testContractAssignmentAttributes() {
		testBoAttributesPresent(Assignment.class.getCanonicalName());
		Class<Assignment> dataObjectClass = Assignment.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from date inserted via sql
	 */
	public void testContractAssignmentRetrieve() {
		// retrieve object populated via sql script
		Assignment assignment = getBoSvc().findBySinglePrimaryKey(
				Assignment.class, 1001l);
		getTestUtils().testAssignmentFields(assignment);
	}

	@Test
	/**
	 * test CRUD for {@link Assignment}
	 */
	public void testContractAssignmentCRUD() throws InstantiationException, IllegalAccessException {
		// C
		Assignment assignment = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		getTestUtils().testAssignmentCRUD(assignment);
	}
}
