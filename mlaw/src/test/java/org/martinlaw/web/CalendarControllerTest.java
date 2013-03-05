/**
 * 
 */
package org.martinlaw.web;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * unit tests for {@link org.martinlaw.web.CalendarController
 * @author mugo
 *
 */
public class CalendarControllerTest {

	private CalendarController controller;
	/**
	 * Test method for {@link org.martinlaw.web.CalendarController#uidMatchesPattern(java.lang.String)}.
	 */
	@Test
	public void testUidMatchesPattern() {
		assertTrue("pattern should have matched", 
				controller.uidMatchesPattern("1001-org.martinlaw.bo.courtcase.Event@mlaw.co.ke"));
		assertFalse("pattern should not have matched", 
				controller.uidMatchesPattern("1001+org.martinlaw.bo.courtcase.Event@mlaw.co.ke"));
		assertFalse("pattern should not have matched", 
				controller.uidMatchesPattern("1001-org.martinlaw.bo.courtcase.Event"));
	}
	
	@Before
	public void setup() {
		controller = new CalendarController();
	}
}
