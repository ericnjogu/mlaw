package org.martinlaw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.martinlaw.bo.MatterConsideration;
import org.springframework.dao.DataIntegrityViolationException;
/**
 * holds common BO tests for classes inheriting from {@link MatterConsideration}
 * @author mugo
 *
 */
public abstract class MatterConsiderationBOTestBase extends MartinlawTestsBase {
	/**
	 * test that the null values are validated by the db
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testMatterConsiderationNullableFields() throws InstantiationException, IllegalAccessException {
		MatterConsideration consideration = getDataObjectClass().newInstance();
		getBoSvc().save(consideration);
	}

	/**
	 * test data dictionary info
	 */
	@Test
	public void testMatterConsiderationAttributes() {
		testBoAttributesPresent(getDataObjectClass().getCanonicalName());
		verifyMaintDocDataDictEntries(getDataObjectClass());
	}

	/**
	 * tests that the consideration inserted via sql can be retrieved ok
	 */
	@Test
	public void testMatterConsiderationRetrieve() {
		// retrieve object populated via sql script
		MatterConsideration consideration = getBoSvc().findBySinglePrimaryKey(
				getDataObjectClass(), 1001l);
		getTestUtils().testRetrievedConsiderationFields(consideration);
	}

	/**
	 * tests CRUD operations
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testMatterConsiderationCRUD()
			throws InstantiationException, IllegalAccessException {
		// CR
		MatterConsideration consideration = getTestUtils().getTestConsideration(getDataObjectClass());
		getBoSvc().save(consideration);
		//getTestUtils().testConsiderationCRUD(consideration);
		// U
		consideration.refresh();
		final String currency = "ETB";
		consideration.setCurrency(currency);
		final String description = "lapset contract fees";
		consideration.setDescription(description);
		getBoSvc().save(consideration);
		consideration = getBoSvc().findBySinglePrimaryKey(getDataObjectClass(), consideration.getId());
		assertEquals("currency differs", currency, consideration.getCurrency());
		assertEquals("description differs", description, consideration.getDescription());
		//D
		getBoSvc().delete(consideration);
		assertNull("consideration should have been deleted", 
				getBoSvc().findBySinglePrimaryKey(getDataObjectClass(), consideration.getId()));
	}
	
	/**
	 * 
	 * @return the class to be tested
	 */
	public abstract Class<? extends MatterConsideration> getDataObjectClass();

}