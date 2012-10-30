/**
 * 
 */
package org.martinlaw.test.date;

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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.test.SQLDataLoader;
import org.kuali.rice.test.lifecycles.KEWXmlDataLoaderLifecycle;
import org.martinlaw.bo.DateType;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link DateType}
 * 
 * @author mugo
 * 
 */
public class DateTypeBOTest extends MartinlawTestsBase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader(
				"classpath:org/martinlaw/scripts/date-type-default-data.sql",
				";").runSql();
	}

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testDateTypeNullableFields() {
		DateType dateType = new DateType();
		getBoSvc().save(dateType);
	}

	@Test
	/**
	 * test that the DateType is loaded into the data dictionary
	 */
	public void testDateTypeAttributes() {
		testBoAttributesPresent(DateType.class.getCanonicalName());
		Class<DateType> dataObjectClass = DateType.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from date inserted via sql
	 */
	public void testDateTypeRetrieve() {
		// retrieve object populated via sql script
		DateType dateType = getBoSvc().findBySinglePrimaryKey(
				DateType.class, 1003l);
		assertNotNull(dateType);
		assertEquals("Mention", dateType.getName());
	}

	@Test
	/**
	 * test CRUD for {@link DateType}
	 */
	public void testDateTypeCRUD() {
		// C
		DateType dateType = new DateType();
		String name = "judgment";
		dateType.setName(name);
		getBoSvc().save(dateType);
		// R
		dateType.refresh();
		assertEquals("date type name does not match", name, dateType.getName());
		// U
		dateType.setDescription("When the judgement is delivered");
		dateType.refresh();
		assertNotNull("date type description should not be null", dateType.getDescription());
		// D
		getBoSvc().delete(dateType);
		assertNull(getBoSvc().findBySinglePrimaryKey(DateType.class,
				dateType.getId()));
	}
	
	@Test
	/**
	 * tests that the document type is loaded ok
	 */
	public void testContractTypeDocType() {
		assertNotNull("document type should not be null", getDocTypeSvc().findByName("DateTypeMaintenanceDocument"));
	}

	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#getSuiteLifecycles()
	 */
	/**
	 * provide the document type definition file and the supporting files groups > users.
	 */
	@Override
	protected List<Lifecycle> getSuiteLifecycles() {
		List<Lifecycle> suiteLifecycles = super.getSuiteLifecycles();
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/kim/users.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/kim/groups.xml"));
		suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/doctype/dateType.xml"));
		return suiteLifecycles;
	}

}
