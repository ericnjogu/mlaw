/**
 * 
 */
package org.martinlaw.web;

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
public class DownloadControllerTestIT extends MartinlawTestsBase{

	private DownloadController controller;

	/**
	 * Test method for {@link org.martinlaw.web.CalendarController#getMatterDate(java.lang.String)}.
	 */
	@Test
	public void testGetMatterDate() {
		String uid1 = "1001-org.martinlaw.bo.courtcase.Event@mlaw.co.ke";
		MatterEvent matterEvent = controller.getMatterDate(uid1);
		assertNotNull("matter date should not be null", matterEvent);
		assertEquals("matter id differs", new Long(1001), matterEvent.getId());
	}
	
	@Before
	public void setup() {
		controller = new DownloadController();
	}
}
