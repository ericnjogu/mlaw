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
import org.martinlaw.bo.contract.ContractAssignee;
import org.martinlaw.bo.contract.ContractAssignment;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link ContractAssignment}
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
		ContractAssignment contractAssignment = new ContractAssignment();
		getBoSvc().save(contractAssignment);
	}

	@Test
	/**
	 * test that the ContractAssignment is loaded into the data dictionary
	 */
	public void testContractAssignmentAttributes() {
		testBoAttributesPresent(ContractAssignment.class.getCanonicalName());
		Class<ContractAssignment> dataObjectClass = ContractAssignment.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from date inserted via sql
	 */
	public void testContractAssignmentRetrieve() {
		// retrieve object populated via sql script
		ContractAssignment contractAssignment = getBoSvc().findBySinglePrimaryKey(
				ContractAssignment.class, 1001l);
		assertNotNull(contractAssignment);
		assertEquals("number of assignees did not match", 2, contractAssignment.getAssignees().size());
		assertEquals("assignee principal name did not match", "lawyer1", contractAssignment.getAssignees().get(0).getPrincipalName());
		assertEquals("assignee principal name did not match", "clerk1", contractAssignment.getAssignees().get(1).getPrincipalName());
		assertEquals("contract id did not match", new Long(1001), contractAssignment.getContractId());
	}

	@Test
	/**
	 * test CRUD for {@link ContractAssignment}
	 */
	public void testContractAssignmentCRUD() {
		// C
		ContractAssignment contractAssignment = getTestUtils().getTestContractAssignment();
		
		getBoSvc().save(contractAssignment);
		// R
		contractAssignment.refresh();
		getTestUtils().testContractAssignmentFields(contractAssignment);
		// U
		// TODO new collection items do not appear to be persisted when refresh is called
		/*ContractAssignee assignee3 = new ContractAssignee();
		String name3 = "hw";
		assignee.setPrincipalName(name3);
		contractAssignment.getAssignees().add(assignee3);
		contractAssignment.refresh();
		assertEquals("number of assignees does not match", 3, contractAssignment.getAssignees().size());
		assertEquals("assignee principal name did not match", name3, contractAssignment.getAssignees().get(2).getPrincipalName());*/
		// D
		getBoSvc().delete(contractAssignment);
		assertNull("contract assignment should have been deleted", getBoSvc().findBySinglePrimaryKey(ContractAssignment.class,	contractAssignment.getId()));
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("contractAssignmentId", contractAssignment.getId());
		assertEquals("assignees should have been deleted", 0, getBoSvc().findMatching(ContractAssignee.class, criteria).size());
	}
}
