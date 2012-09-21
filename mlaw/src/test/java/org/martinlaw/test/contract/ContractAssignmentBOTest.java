/**
 * 
 */
package org.martinlaw.test.contract;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader(
				"classpath:org/martinlaw/scripts/contract-assignment-test-data.sql",
				";").runSql();
	}

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
		assertNotNull(assignment);
		assertEquals("number of assignees did not match", 2, assignment.getAssignees().size());
		assertEquals("assignee principal name did not match", "lawyer1", assignment.getAssignees().get(0).getPrincipalName());
		assertEquals("assignee principal name did not match", "clerk1", assignment.getAssignees().get(1).getPrincipalName());
		assertEquals("contract id did not match", new Long(1001), assignment.getContractId());
	}

	@Test
	/**
	 * test CRUD for {@link Assignment}
	 */
	public void testContractAssignmentCRUD() {
		// C
		Assignment assignment = getTestUtils().getTestContractAssignment();
		
		getBoSvc().save(assignment);
		// R
		assignment.refresh();
		getTestUtils().testContractAssignmentFields(assignment);
		// U
		// TODO new collection items do not appear to be persisted when refresh is called
		/*Assignee assignee3 = new Assignee();
		String name3 = "hw";
		assignee.setPrincipalName(name3);
		contractAssignment.getAssignees().add(assignee3);
		contractAssignment.refresh();
		assertEquals("number of assignees does not match", 3, contractAssignment.getAssignees().size());
		assertEquals("assignee principal name did not match", name3, contractAssignment.getAssignees().get(2).getPrincipalName());*/
		// D
		getBoSvc().delete(assignment);
		assertNull("contract assignment should have been deleted", getBoSvc().findBySinglePrimaryKey(Assignment.class,	assignment.getId()));
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("assignmentId", assignment.getId());
		assertEquals("assignees should have been deleted", 0, getBoSvc().findMatching(Assignee.class, criteria).size());
	}
}
