/**
 * 
 */
package org.martinlaw.bo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.martinlaw.MartinlawConstants;
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
		String template = readAll("vcalendar-template.txt");
		
		// using court case date as matter date is abstract
		TestUtils utils = new TestUtils();
		Event caseDate = (Event) utils.getTestMatterEventForStringTemplates();
		
		String actualResult = caseDate.toIcalendar(template);
		
		// load expected result
		String expectedResult = readAll("vcalendar-expected-output.txt");
		assertEquals("expected vcalendar output differs", expectedResult, actualResult);
	}

	/**
	 * read the entire contents of the file
	 * @return the file contents as string
	 * @throws IOException
	 */
	public String readAll(String filePath) throws IOException {
		// copied from org.kuali.rice.ken.services.impl.SendNotificationServiceImplTest#testSendNotificationAsBo_validInput
		return IOUtils.toString(getClass().getResourceAsStream(filePath));
	}
	
	@Test
	/**
	 * tests {@link org.martinlaw.bo.MatterEvent#toNotificationXML(String, String, String, String)}
	 */
	public void testToNotificationXML() throws IOException {
		TestUtils utils = new TestUtils();
		String template = readAll("event-notfn-template.xml");
		String expected = readAll("event-notfn-expected.xml");
		Event caseDate = (Event) utils.getTestMatterEventForStringTemplates();
		assertEquals("expected output differs", expected, 
				caseDate.toNotificationXML(template, MartinlawConstants.NotificationTemplatePlaceholders.CALENDAR_CHANNEL_NAME, 
						MartinlawConstants.NotificationTemplatePlaceholders.CALENDAR_PRODUCER_NAME, 
						"May you prosper and be in good health."));
	}

}
