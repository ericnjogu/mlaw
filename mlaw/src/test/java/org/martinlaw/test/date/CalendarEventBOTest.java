/**
 * 
 */
package org.martinlaw.test.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.CalendarEvent;
import org.martinlaw.bo.contract.ContractParty;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link ContractParty}
 * 
 * @author mugo
 * 
 */
public class CalendarEventBOTest extends MartinlawTestsBase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/calendar-event-test-data.sql", ";").runSql();
	}

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testCalendarEventNullableFields() {
		CalendarEvent calendarEvent = new CalendarEvent();
		getBoSvc().save(calendarEvent);
	}

	@Test
	@Ignore("does not have a data dictionary entry")
	/**
	 * test that the CalendarEvent is loaded into the data dictionary
	 */
	public void testCalendarEventAttributes() {
		testBoAttributesPresent(CalendarEvent.class.getCanonicalName());
	}

	@Test
	/**
	 * tests retrieving from date inserted via sql
	 */
	public void testCalendarEventRetrieve() {
		// retrieve object populated via sql script
		CalendarEvent calendarEvent = getBoSvc().findBySinglePrimaryKey(
				CalendarEvent.class, 1001l);
		assertNotNull(calendarEvent);
		assertEquals(new Long(1l), calendarEvent.getDateVersionNumber());
	}

	@Test
	/**
	 * test CRUD for {@link CalendarEvent}
	 */
	public void testCalendarEventCRUD() {
		// C
		String url = "http://cal.yahoo.com/?eventId=56";
		Long dateVersionNumber = 2l;
		CalendarEvent calendarEvent = new CalendarEvent(url, dateVersionNumber);
		getBoSvc().save(calendarEvent);
		// R
		calendarEvent.refresh();
		assertEquals("url does not match", url, calendarEvent.getUrl());
		assertEquals("date ver nbr does not match", dateVersionNumber, calendarEvent.getDateVersionNumber());
		// U
		dateVersionNumber = 3l;
		calendarEvent.setDateVersionNumber(dateVersionNumber);
		calendarEvent.refresh();
		assertEquals("date ver nbr does not match",  dateVersionNumber, calendarEvent.getDateVersionNumber());
		// D
		getBoSvc().delete(calendarEvent);
		assertNull(getBoSvc().findBySinglePrimaryKey(CalendarEvent.class,
				calendarEvent.getId()));
	}
}
