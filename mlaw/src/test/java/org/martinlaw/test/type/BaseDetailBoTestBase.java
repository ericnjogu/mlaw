package org.martinlaw.test.type;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2013 Eric Njogu (kunadawa@gmail.com)
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

import org.junit.Test;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.martinlaw.ScopedKeyValue;
import org.martinlaw.bo.BaseDetail;
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.Scope;
import org.martinlaw.test.MartinlawTestsBase;
import org.martinlaw.test.TestBoInfo;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * holds common tests for children of {@link BaseDetail}
 * @author mugo
 *
 */
public abstract class BaseDetailBoTestBase extends MartinlawTestsBase implements TestBoInfo {

	public BaseDetailBoTestBase() {
		super();
	}

	/**
	 * tests that non nullable fields are validated by the db
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testTypeNullableFields() throws InstantiationException, IllegalAccessException {
		BaseDetail type = getDataObjectClass().newInstance();
		getBoSvc().save(type);
	}

	/**
	 * tests data dictionary entries
	 */
	@Test
	public void testBaseDetailAttributes() {
		testBoAttributesPresent(getDataObjectClass().getCanonicalName());
		verifyMaintDocDataDictEntries(getDataObjectClass());
	}
	
	/**
	 * tests scope (if applicable) dictionary entries
	 */
	@Test
	public void testScopeAttributes() {
		testBoAttributesPresent(getScopeClass().getCanonicalName());
	}

	public abstract Class<? extends Scope> getScopeClass();

	/**
	 * tests retrieving a BO
	 */
	@Test
	public void testBaseDetailRetrieve() {
		// retrieve object populated via sql script
		BaseDetail type = getBoSvc().findBySinglePrimaryKey(
				getDataObjectClass(), getExpectedOnRetrieve().getId());
		assertNotNull("retrieved '" + getDataObjectClass() + "' should not be null", type);
		assertEquals("name differs", getExpectedOnRetrieve().getName(), type.getName());
		assertEquals("description differs", getExpectedOnRetrieve().getDescription(), type.getDescription());
		additionalTestsForRetrievedObject(type);
	}

	protected abstract void additionalTestsForRetrievedObject(BaseDetail type);

	/**
	 * tests CRUD for the bo class provided in {@link #getDataObjectClass()}
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testBaseDetailCRUD() throws InstantiationException, IllegalAccessException {
		// C
		BaseDetail type = getDataObjectClass().newInstance();
		String name = "test type";
		type.setName(name);
		populateAdditionalFieldsForCrud(type);
		getBoSvc().save(type);
		
		// R
		type.refresh();
		assertEquals("name does not match", name, type.getName());
		// save scope and see if it can be seen from the base detail/type side
		if (ScopedKeyValue.class.isAssignableFrom(getDataObjectClass())) {
			Scope scope = getScopeClass().newInstance();
			final String qualifiedClassName = Matter.class.getCanonicalName();
			scope.setQualifiedClassName(qualifiedClassName);
			scope.setTypeId(type.getId());
			getBoSvc().save(scope);
			
			type.refresh();
			ScopedKeyValue scoped = (ScopedKeyValue)type;
			assertNotNull("scope should not be null", scoped.getScope());
			assertFalse("scope should not be empty", scoped.getScope().isEmpty());
			assertEquals("qualified class name differs", qualifiedClassName, scoped.getScope().get(0).getQualifiedClassName());
		}
		testCrudCreated(type);
		
		// U
		type.setDescription("test description");
		type.refresh();
		assertNotNull("description should not be null", type.getDescription());
		// D
		getBoSvc().delete(type);
		assertNull(getBoSvc().findBySinglePrimaryKey(getDataObjectClass(),	type.getId()));
		testCrudDeleted(type);
	}

	/**
	 * additional tests for an object created while doing CRUD tests
	 * @param type - the object to test
	 */
	protected abstract void testCrudCreated(BaseDetail type);
	
	/**
	 * additional tests for an object deleted while doing CRUD tests
	 * @param type - the object to test
	 */
	protected abstract void testCrudDeleted(BaseDetail type);

	/**
	 * populate additional fields - in addition to name and description if any
	 * @param type - the object to populate
	 */
	protected abstract void populateAdditionalFieldsForCrud(BaseDetail type);

	/**
	 * tests that the document type name can be found
	 */
	@Test
	public void testBaseDetailDocType() {
		assertNotNull("document type should not be null", getDocTypeSvc().findByName(getDocTypeName()));
	}
	
	/**
	 * 
	 * @return a bo containing the expected values to be compared to the bo retrieved from the db 
	 */
	public abstract BaseDetail getExpectedOnRetrieve();
	
	/**
	 * verify that the collection definition has been defined
	 * <p>to be overridden by tests for BOs which do not have a scope</p>
	 */
	@Test
	public void testScopeCollectionDD() {
		String label = KRADServiceLocatorWeb.getDataDictionaryService().getCollectionLabel(getDataObjectClass(), "scope");
		assertEquals("scope collection label differs", "scope", label);
	}
}