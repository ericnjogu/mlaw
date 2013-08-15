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

package org.martinlaw.bo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
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
	
	/**
	 * tests {@link org.martinlaw.bo.MatterEvent#toHtml()}
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testToHtml() throws InstantiationException, IllegalAccessException {
		// use court case event as concrete class
		TestUtils utils = new TestUtils();
		Event event = utils.getTestMatterEventUnt(Event.class);
		SimpleDateFormat sdf = new SimpleDateFormat(MartinlawConstants.DEFAULT_TIMESTAMP_FORMAT);
		final String formattedDate = sdf.format(event.getStartDate());
		
		String expected = "<b>" + event.getType().getName() + "</b>:&nbsp;" + formattedDate + "<br/>" + event.getLocation();
		assertEquals("html output differs", expected, event.toHtml());
	}

}
