/**
 * 
 */
package org.martinlaw.test.courtcase;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012,2013 Eric Njogu (kunadawa@gmail.com)
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




import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.junit.Test;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.ken.api.KenApiConstants;
import org.kuali.rice.ken.api.notification.NotificationResponse;
import org.kuali.rice.ken.api.service.SendNotificationService;
import org.kuali.rice.ken.util.NotificationConstants;
import org.martinlaw.bo.MatterEvent;
import org.martinlaw.bo.courtcase.Event;
import org.martinlaw.test.MatterEventRoutingTest;
import org.martinlaw.util.TestUtils;

/**
 * tests routing for {@link Event}
 * @author mugo
 *
 */


public class CourtCaseEventRoutingTest extends MatterEventRoutingTest {
	@Test
	/**
	 * tests sending a notification
	 * @see org.kuali.rice.ken.services.impl.NotificationServiceImplTest#testSendNotificationAsXml_validInput
	 * @throws RiceIllegalArgumentException
	 * @throws IOException
	 */
	public void testSendEventNotification() throws RiceIllegalArgumentException, IOException {
		try {
			TestUtils utils = new TestUtils();
			// send notification
			final SendNotificationService sendNotificationService = (SendNotificationService) GlobalResourceLoader.getService(
					new QName(KenApiConstants.Namespaces.KEN_NAMESPACE_2_0, "sendNotificationService"));
			NotificationResponse response = sendNotificationService.invoke(utils.getTestNotificationXml());

			assertEquals("response status is not success", NotificationConstants.RESPONSE_STATUSES.SUCCESS, response.getStatus());
		} catch (Exception e) {
			log.error("exception occured", e);
			fail("exception occured");
		}
		
	}

	@Override
	public String getDocType() {
		return "CourtCaseEventMaintenanceDocument";
	}

	@Override
	public Class<? extends MatterEvent> getDataObjectClass() {
		return Event.class;
	}
}
