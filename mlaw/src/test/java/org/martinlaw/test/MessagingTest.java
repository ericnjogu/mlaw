/**
 * 
 */
package org.martinlaw.test;

import static org.junit.Assert.assertFalse;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.kuali.rice.krad.messages.MessageService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.martinlaw.Constants;

/**
 * @author mugo
 *
 */
public class MessagingTest extends MartinlawTestsBase {
	
	@Test
	public void testNotAssigneeMsg() {
		MessageService messageService = KRADServiceLocatorWeb.getMessageService();
        // find message by key
        String message = messageService.getMessageText(Constants.MODULE_NAMESPACE_CODE, null,
        		Constants.MessageKeys.ERROR_NOT_ASSIGNED);
        assertFalse(StringUtils.isEmpty(message));
	}
}
