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
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.martinlaw.bo.MatterConsideration;
import org.martinlaw.bo.MatterTransactionDoc;
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
		MatterConsideration<?> consideration = getDataObjectClass().newInstance();
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
		MatterConsideration<?> consideration = getBoSvc().findBySinglePrimaryKey(
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
		MatterConsideration<? extends MatterTransactionDoc> consideration = getTestUtils().getTestConsideration(getDataObjectClass());
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
	public abstract Class<? extends MatterConsideration<?>> getDataObjectClass();
	
	/*@Test
	*//**
	 * verify that the relationships are as expected
	 *//*
	public void testRelationshipDefs() throws InstantiationException, IllegalAccessException {
		DataObjectMetaDataService svc = KRADServiceLocatorWeb.getDataObjectMetaDataService();
		DataObjectRelationship reln1 = svc.getDataObjectRelationship(getDataObjectClass().newInstance(), getDataObjectClass(), "matterId", null, false, true, true);
		assertEquals("related class differs", Contract.class, reln1.getRelatedClass());
		assertEquals("number of relationships differs", 2, svc.getDataObjectRelationships(getDataObjectClass()).size());
	}*/

}