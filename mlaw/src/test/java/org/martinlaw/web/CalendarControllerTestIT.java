/**
 * 
 */
package org.martinlaw.web;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.martinlaw.bo.MatterEvent;
import org.martinlaw.test.MartinlawTestsBase;

/**
 * integration tests for {@link org.martinlaw.web.CalendarController} 
 * @author mugo
 *
 */
public class CalendarControllerTestIT extends MartinlawTestsBase{

	private CalendarController controller;

	/**
	 * Test method for {@link org.martinlaw.web.CalendarController#getMatterDate(java.lang.String)}.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testGetMatterDate() {
		String uid1 = "1001-org.martinlaw.bo.courtcase.Event@mlaw.co.ke";
		MatterEvent matterEvent = controller.getMatterDate(uid1);
		assertNotNull("matter date should not be null", matterEvent);
		assertEquals("matter id differs", new Long(1001), matterEvent.getId());
	}
	
	@Before
	public void setup() {
		controller = new CalendarController();
	}
}
