/**
 * 
 */
package org.martinlaw.test.opinion;

import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.opinion.Assignee;
import org.martinlaw.bo.opinion.Assignment;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link Assignment}
 * 
 * @author mugo
 * 
 */
// @BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
public class OpinionAssignmentBOTest extends MartinlawTestsBase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		// needed since they hold the status, opinion and the dependent data
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-assignment-test-data.sql", ";").runSql();
	}

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testOpinionAssignmentNullableFields() {
		Assignment assignment = new Assignment();
		getBoSvc().save(assignment);
	}

	@Test
	/**
	 * test that the Assignment is loaded into the data dictionary
	 */
	public void testOpinionAssignmentAttributes() {
		testBoAttributesPresent(Assignment.class.getCanonicalName());
		Class<Assignment> dataObjectClass = Assignment.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from date inserted via sql
	 */
	public void testOpinionAssignmentRetrieve() {
		// retrieve object populated via sql script
		Assignment assignment = getBoSvc().findBySinglePrimaryKey(
				Assignment.class, 1001l);
		getTestUtils().testAssignmentFields(assignment);
	}

	@Test
	/**
	 * test CRUD for {@link Assignment}
	 */
	public void testOpinionAssignmentCRUD() throws InstantiationException, IllegalAccessException {
		// C
		Assignment assignment = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		
		getTestUtils().testAssignmentCRUD(assignment);
	}
}
