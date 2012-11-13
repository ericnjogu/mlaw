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
	
	@Test
	/**
	 * requires the ldap server to be running to fetch principal details
	 */
	public void testGroupsAndUsers() {
		//clerk
		testPrincipal("clerk1", 1, "org.martinlaw.clerk");
		//witness
		testPrincipal("witness1", 1, "org.martinlaw.witness");
		//client
		testPrincipal("client1", 1, "org.martinlaw.client");
		//lawyer
		testPrincipal("lawyer1", 1, "org.martinlaw.lawyer");
	}

	/**
	 * convenience method to test a principal
	 * @param principalName - the principal name
	 * @param numOfGroups - number of groups the principal is in
	 * @param grpId - a group id the principal should belong to TODO - assumes only one group
	 */
	protected void testPrincipal(String principalName, int numOfGroups, String grpId) {
		Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName);
		assertNotNull("principal should not be null", principal);
		assertEquals("principal id does not match", principalName, principal.getPrincipalId());
		List<Group> grpInfo = KimApiServiceLocator.getGroupService().getGroupsByPrincipalIdAndNamespaceCode(principal.getPrincipalId(), "MARTINLAW");
		assertNotNull("group info should not be null", grpInfo);
		assertEquals("number of groups differs", numOfGroups, grpInfo.size());
		assertEquals("group id differs", grpId, grpInfo.get(0).getId());
		assertTrue(principalName + " should belong to group", KimApiServiceLocator.getGroupService().isMemberOfGroup(principal.getPrincipalId(), grpInfo.get(0).getId()));
	}
}
