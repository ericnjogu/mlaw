/**
 * 
 */
package org.martinlaw.test;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012 Eric Njogu (kunadawa@gmail.com)
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

/**
 * @author mugo
 *
 */
public class KIMTest extends KewTestsBase {

	public void setUp() throws Exception {
		super.setUp();
		//groupService = (GroupServiceImpl)GlobalResourceLoader.getService(new QName("KIM", "kimGroupService"));
		//groupUpdateService = (GroupUpdateServiceImpl)GlobalResourceLoader.getService(new QName("KIM", "kimGroupUpdateService"));
	}
	
	@Test
	public void testGroupsAndUsers() {
		//clerk
		Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName("clerk1");
		assertNotNull(principal);
		assertEquals("ml.p2",principal.getPrincipalId());
		List<Group> grpInfo = KimApiServiceLocator.getGroupService().getGroupsByPrincipalIdAndNamespaceCode(principal.getPrincipalId(), "MARTINLAW");
		assertNotNull(grpInfo);
		assertEquals(1, grpInfo.size());
		assertEquals("org.martinlaw.clerk", grpInfo.get(0).getId());
		assertTrue(KimApiServiceLocator.getGroupService().isMemberOfGroup(principal.getPrincipalId(), grpInfo.get(0).getId()));
		//witness
		principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName("witness1");
		assertNotNull(principal);
		assertEquals("ml.p3",principal.getPrincipalId());
		grpInfo = KimApiServiceLocator.getGroupService().getGroupsByPrincipalIdAndNamespaceCode(principal.getPrincipalId(), "MARTINLAW");
		assertNotNull(grpInfo);
		assertEquals("org.martinlaw.witness",grpInfo.get(0).getId());
		assertTrue(KimApiServiceLocator.getGroupService().isMemberOfGroup(principal.getPrincipalId(), grpInfo.get(0).getId()));
		//client
		principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName("client1");
		assertNotNull(principal);
		assertEquals("ml.p1",principal.getPrincipalId());
		grpInfo = KimApiServiceLocator.getGroupService().getGroupsByPrincipalIdAndNamespaceCode(principal.getPrincipalId(), "MARTINLAW");
		assertNotNull(grpInfo);
		assertEquals("org.martinlaw.client",grpInfo.get(0).getId());
		assertTrue(KimApiServiceLocator.getGroupService().isMemberOfGroup(principal.getPrincipalId(), grpInfo.get(0).getId()));
		//lawyer
		principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName("lawyer1");
		assertNotNull(principal);
		assertEquals("ml.p4",principal.getPrincipalId());
		grpInfo = KimApiServiceLocator.getGroupService().getGroupsByPrincipalIdAndNamespaceCode(principal.getPrincipalId(), "MARTINLAW");
		assertNotNull(grpInfo);
		assertEquals("org.martinlaw.lawyer",grpInfo.get(0).getId());
		assertTrue(KimApiServiceLocator.getGroupService().isMemberOfGroup(principal.getPrincipalId(), grpInfo.get(0).getId()));
	}
}
