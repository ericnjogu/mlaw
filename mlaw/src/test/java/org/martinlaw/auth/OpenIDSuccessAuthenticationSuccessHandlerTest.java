/**
 * 
 */
package org.martinlaw.auth;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2013 Eric Njogu (kunadawa@gmail.com)
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

import org.junit.Test;
import org.kuali.rice.kim.api.identity.entity.EntityContract;
import org.martinlaw.test.MartinlawTestsBase;

/**
 * integration tests for {@link OpenIDSuccessAuthenticationSuccessHandler}
 * @author mugo
 *
 */
public class OpenIDSuccessAuthenticationSuccessHandlerTest extends MartinlawTestsBase{

	@Test
	/**
	 * Test method for {@link org.martinlaw.auth.OpenIDSuccessAuthenticationSuccessHandler#EntityInfoService#getByEmail(String)}
	 */
	public void testGetByEmail() {
		OpenIDSuccessAuthenticationSuccessHandler successHandler = new OpenIDSuccessAuthenticationSuccessHandler();
		EntityContract entity = successHandler.getEntityInfoService().getEntityByEmail("clerk1@localhost");
		assertNotNull("entity should not be null", entity);
		assertEquals("principal name differs", "clerk1", entity.getPrincipals().get(0).getPrincipalName());
		entity = successHandler.getEntityInfoService().getEntityByEmail("clerk21@localhost");
		assertEquals("entity should be null", null, entity);
	}

}
