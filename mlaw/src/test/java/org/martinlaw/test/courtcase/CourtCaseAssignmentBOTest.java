/**
 * 
 */
package org.martinlaw.test.courtcase;

import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.courtcase.Assignee;
import org.martinlaw.bo.courtcase.Assignment;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link Assignment}
 * 
 * @author mugo
 * 
 */
// @BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
public class CourtCaseAssignmentBOTest extends MartinlawTestsBase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		// default-data and courtcase-test-data are needed since they hold the courtcase and the dependent data
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-assignment-test-data.sql", ";").runSql();
	}

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testCourtCaseAssignmentNullableFields() {
		Assignment assignment = new Assignment();
		getBoSvc().save(assignment);
	}

	@Test
	/**
	 * test that the Assignment is loaded into the data dictionary
	 */
	public void testCourtCaseAssignmentAttributes() {
		testBoAttributesPresent(Assignment.class.getCanonicalName());
		Class<Assignment> dataObjectClass = Assignment.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from date inserted via sql
	 */
	public void testCourtCaseAssignmentRetrieve() {
		// retrieve object populated via sql script
		Assignment assignment = getBoSvc().findBySinglePrimaryKey(
				Assignment.class, 1001l);
		getTestUtils().testAssignmentFields(assignment);
	}

	@Test
	/**
	 * test CRUD for {@link Assignment}
	 */
	public void testCourtCaseAssignmentCRUD() throws InstantiationException, IllegalAccessException {
		// C
		Assignment assignment = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		
		getTestUtils().testAssignmentCRUD(assignment);
	}
}
