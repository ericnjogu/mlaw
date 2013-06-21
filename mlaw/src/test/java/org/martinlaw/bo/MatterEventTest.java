/**
 * 
 */
package org.martinlaw.bo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.martinlaw.bo.courtcase.Event;
import org.martinlaw.util.TestUtils;

/**
 * @author mugo
 *
 */
public class MatterEventTest {

	/**
	 * Test method for {@link org.martinlaw.bo.MatterEvent#toIcalendar(java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	public void testToIcalendar() throws IOException {
		// load template
		String template =IOUtils.toString(MatterEventTest.class.getResourceAsStream("vcalendar-template.txt"));
		
		// using court case date as matter date is abstract
		TestUtils utils = new TestUtils();
		Event caseDate = (Event) utils.getTestMatterEventForStringTemplates();
		
		String actualResult = caseDate.toIcalendar(template);
		
		// load expected result
		String expectedResult = IOUtils.toString(MatterEventTest.class.getResourceAsStream("vcalendar-expected-output.txt"));
		assertEquals("expected vcalendar output differs", expectedResult, actualResult);
	}
	
	@Test
	@Ignore("intend to rework notifications into sync/push")
	/**
	 * tests {@link org.martinlaw.bo.MatterEvent#toNotificationXML(String, String, String, String)}
	 */
	public void testToNotificationXML() throws IOException {
		TestUtils utils = new TestUtils();
		final String notificationXML = utils.getTestNotificationXml();
		String expected = IOUtils.toString(MatterEventTest.class.getResourceAsStream("event-notfn-expected.xml"));
		assertEquals("expected output differs", expected, notificationXML);
	}

}
