/**
 * 
 */
package org.martinlaw.test;

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
