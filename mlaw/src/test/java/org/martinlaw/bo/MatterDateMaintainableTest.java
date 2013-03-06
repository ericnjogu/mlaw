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
public class MatterDateMaintainableTest {

	/**
	 * Test method for {@link org.martinlaw.bo.MatterEventMaintainable#createNotificationMessage(String, String, org.martinlaw.bo.MatterEvent, String)}.
	 * @throws IOException 
	 */
	@Test
	public void testCreateNotificationMessage() throws IOException {
		TestUtils utils = new TestUtils();
		MatterEventMaintainable dateMaint = new MatterEventMaintainable();
		String template = IOUtils.toString(getClass().getResourceAsStream("notfn-msg-template.txt"));
		String expected = IOUtils.toString(getClass().getResourceAsStream("notfn-msg-expected.txt"));
		assertEquals("expected output differs", expected, 
				dateMaint.createNotificationMessage(KRADConstants.MAINTENANCE_EDIT_ACTION, "1200", 
						utils.getTestMatterEventForStringTemplates(), template));
	}

}
