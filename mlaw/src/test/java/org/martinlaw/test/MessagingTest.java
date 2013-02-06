/**
 * 
 */
package org.martinlaw.test;

import static org.junit.Assert.assertFalse;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.messages.MessageService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.martinlaw.Constants;

/**
 * @author mugo
 *
 */
public class MessagingTest extends MartinlawTestsBase {
	
	@Test
	/**
	 * tests that {@link Constants.MessageKeys.ERROR_NOT_ASSIGNED} is found
	 */
	public void testNotAssigneeMsg() {
		MessageService messageService = KRADServiceLocatorWeb.getMessageService();
        // find message by key
        String message = messageService.getMessageText(Constants.MODULE_NAMESPACE_CODE, null,
        		Constants.MessageKeys.ERROR_NOT_ASSIGNED);
        assertFalse("message should not be null", StringUtils.isEmpty(message));
	}
	
	@Test
	/**
	 * tests that {@link RiceKeyConstants.ERROR_EXISTENCE} is found
	 */
	public void testNotExistingMsg() {
		MessageService messageService = KRADServiceLocatorWeb.getMessageService();
        // find message by key
        String message = messageService.getMessageText(Constants.MODULE_NAMESPACE_CODE, null,
        		RiceKeyConstants.ERROR_EXISTENCE);
        assertFalse("message should not be null", StringUtils.isEmpty(message));
	}
}