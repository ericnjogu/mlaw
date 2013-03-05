/**
 * 
 */
package org.martinlaw.test.courtcase;

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


import static org.junit.Assert.assertFalse;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.courtcase.Event;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link Event}
 * 
 * @author mugo
 * 
 */
//@BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
public class CourtCaseEventBOTest extends MartinlawTestsBase {

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testCourtCaseDateNullableFields() {
		Event date = new Event();
		getBoSvc().save(date);
	}

	@Test
	/**
	 * test that the date is loaded into the data dictionary
	 */
	public void testCourtCaseDateAttributes() {
		testBoAttributesPresent(Event.class.getCanonicalName());
		Class<Event> dataObjectClass = Event.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from data inserted via sql
	 */
	public void testCourtCaseDateRetrieve() {
		// retrieve object populated via sql script
		Event date = getBoSvc().findBySinglePrimaryKey(
				Event.class, 1001l);
		getTestUtils().testRetrievedMatterEventFields(date);
	}

	@Test
	/**
	 * test CRUD for {@link Date}
	 */
	public void testCourtCaseDateCRUD() throws InstantiationException, IllegalAccessException {
		Event date = getTestUtils().<Event>getTestMatterEvent(Event.class);
		
		getTestUtils().testMatterEventCRUD(date, Event.class);
	}
	
	/**
	 * confirm that the set label can be retrieved
	 */
	@Test
	public void testMatterIdLabel() {
		assertFalse("label should not be blank", 
				StringUtils.isEmpty(KRADServiceLocatorWeb.getDataDictionaryService().getAttributeLabel(
						Event.class, MartinlawConstants.PropertyNames.MATTER_ID)));
	}
}
