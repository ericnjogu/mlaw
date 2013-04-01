/**
 * 
 */
package org.martinlaw.bo;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.kuali.rice.krad.util.KRADConstants;
import org.martinlaw.util.TestUtils;

/**
 * @author mugo
 *
 */
public class MatterEventMaintainableTest {

	/**
	 * Test method for {@link org.martinlaw.bo.MatterEventMaintainable#createNotificationMessage(String, String, org.martinlaw.bo.MatterEvent, String, String)}.
	 * @throws IOException 
	 */
	@Test
	public void testCreateNotificationMessage() throws IOException {
		TestUtils utils = new TestUtils();
		MatterEventMaintainable eventMaint = new MatterEventMaintainable();
		String template = IOUtils.toString(MatterEventMaintainableTest.class.getResourceAsStream("notfn-msg-template.txt"));
		final String notificationMessage = eventMaint.createNotificationMessage(KRADConstants.MAINTENANCE_EDIT_ACTION, "1200", 
				utils.getTestMatterEventForStringTemplates(), template, "http://localhost");
		String expected = IOUtils.toString(MatterEventMaintainableTest.class.getResourceAsStream("notfn-msg-expected.txt"));
		assertEquals("expected output differs", expected, 
				notificationMessage);
	}

}
