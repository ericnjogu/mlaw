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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import org.junit.Test;
import org.kuali.rice.kim.impl.identity.external.EntityExternalIdentifierBo;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.martinlaw.MartinlawConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author mugo
 *
 */
public class OpenidUserDetailsServiceMockTest {
	
	/**
	 * Test method for {@link org.martinlaw.auth.OpenidUserDetailsService#loadUserByUsername(java.lang.String)}.
	 */
	@Test
	public void testLoadUserByUsername_existing_principal_no_openid() {
		
		BusinessObjectService boSvc = mock(BusinessObjectService.class);
		// simulate not finding any openid url in the db
		when(boSvc.findMatching(same(EntityExternalIdentifierBo.class), anyMapOf(String.class, String.class))).thenReturn(
				Collections.<EntityExternalIdentifierBo> emptyList());
		
		OpenidUserDetailsService usrDetSvc = new OpenidUserDetailsService();
		usrDetSvc.setBusinessObjectService(boSvc);
		final String openidUrl = "http://localhost/karani-openid.html";
		
		// the user should be found in need of open id activation
		UserDetails user = usrDetSvc.loadUserByUsername(openidUrl);
		assertEquals("principal name differs", MartinlawConstants.OPENID_UNACTIVATED_USERNAME, user.getUsername());
		assertFalse("user should be disabled", user.isEnabled());
		GrantedAuthority[] auths = new GrantedAuthority[1];
		user.getAuthorities().toArray(auths);
		assertEquals("granted authority differs", MartinlawConstants.OPENID_ACTIVATE_ROLE, auths[0].getAuthority());
	}
}
