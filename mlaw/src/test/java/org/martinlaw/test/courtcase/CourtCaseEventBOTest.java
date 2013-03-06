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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.directory.shared.ldap.util.ReflectionToStringBuilder;
import org.junit.Test;
import org.kuali.rice.krad.datadictionary.validation.result.ConstraintValidationResult;
import org.kuali.rice.krad.datadictionary.validation.result.DictionaryValidationResult;
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
// @BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
public class CourtCaseEventBOTest extends MartinlawTestsBase {
	private Log log = LogFactory.getLog(getClass());

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testCourtCaseDateNullableFields() {
		Event event = new Event();
		getBoSvc().save(event);
	}

	@Test
	/**
	 * test that the date is loaded into the data dictionary
	 */
	public void testCourtCaseEventAttributes() {
		testBoAttributesPresent(Event.class.getCanonicalName());
		Class<Event> dataObjectClass = Event.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from data inserted via sql
	 */
	public void testCourtCaseEventRetrieve() {
		// retrieve object populated via sql script
		Event event = getBoSvc().findBySinglePrimaryKey(Event.class, 1001l);
		getTestUtils().testRetrievedMatterEventFields(event);
	}

	@Test
	/**
	 * test CRUD for {@link Date}
	 */
	public void testCourtCaseEventCRUD() throws InstantiationException,
			IllegalAccessException {
		Event event = getTestUtils().<Event> getTestMatterEvent(Event.class);

		getTestUtils().testMatterEventCRUD(event, Event.class);
	}

	/**
	 * confirm that the set label can be retrieved
	 */
	@Test
	public void testMatterIdLabel() {
		assertFalse("label should not be blank",
				StringUtils.isEmpty(KRADServiceLocatorWeb
						.getDataDictionaryService().getAttributeLabel(
								Event.class,
								MartinlawConstants.PropertyNames.MATTER_ID)));
	}

	@Test
	/**
	 * test validation for end date which is not required but has a valid characters constraint (date pattern)
	 */
	public void testCourtCaseEvent_date_validation() throws InstantiationException, IllegalAccessException {
		try {
			Event event = getTestUtils().<Event>getTestMatterEvent(Event.class);
			DictionaryValidationResult result = KRADServiceLocatorWeb.getDictionaryValidationService().validate(
					event, event.getClass().getCanonicalName(), "endDate", true);
			final Iterator<ConstraintValidationResult> iterator = result.iterator();
			while (iterator.hasNext()) {
				final ConstraintValidationResult validationResult = iterator.next();
				// using error level to avoid having to configure logging
				log.error(ReflectionToStringBuilder.toString(validationResult));
			}
			assertEquals("expected no errors", 0, result.getNumberOfErrors());
		} catch (Exception e) {
			log.error("exception occured", e);
			fail("exception occured");
		}
	}
}
