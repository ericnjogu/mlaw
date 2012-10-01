/**
 * 
 */
package org.martinlaw.test.conveyance;

import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.conveyance.Assignee;
import org.martinlaw.bo.conveyance.Assignment;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link Assignment}
 * 
 * @author mugo
 * 
 */
// @BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
public class ConveyanceAssignmentBOTest extends MartinlawTestsBase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		// default-data and conveyance-test-data are needed since they hold the conveyance and the dependent data
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-assignment-test-data.sql", ";").runSql();
	}

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testConveyanceAssignmentNullableFields() {
		Assignment assignment = new Assignment();
		getBoSvc().save(assignment);
	}

	@Test
	/**
	 * test that the Assignment is loaded into the data dictionary
	 */
	public void testConveyanceAssignmentAttributes() {
		testBoAttributesPresent(Assignment.class.getCanonicalName());
		Class<Assignment> dataObjectClass = Assignment.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from date inserted via sql
	 */
	public void testConveyanceAssignmentRetrieve() {
		// retrieve object populated via sql script
		Assignment assignment = getBoSvc().findBySinglePrimaryKey(
				Assignment.class, 1001l);
		getTestUtils().testAssignmentFields(assignment);
		
	}

	@Test
	/**
	 * test CRUD for {@link Assignment}
	 */
	public void testConveyanceAssignmentCRUD() throws InstantiationException, IllegalAccessException {
		// C
		Assignment assignment = getTestUtils().<Assignment, Assignee>getTestAssignment(Assignment.class, Assignee.class);
		getTestUtils().testAssignmentCRUD(assignment);
	}
}
