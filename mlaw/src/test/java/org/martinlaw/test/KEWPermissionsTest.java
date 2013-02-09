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
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.martinlaw.bo.Status;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.conveyance.ConveyanceType;
import org.martinlaw.bo.courtcase.CourtCase;

/**
 * holds various permissions tests
 * 
 * @author mugo
 * 
 */
public class KEWPermissionsTest extends KewTestsBase {


	@Test
	/**
	 * test that a conveyance maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testConveyanceMaintDocPerms() {
		testCreateMaintain(Conveyance.class, "ConveyanceMaintenanceDocument");
	}
	
	@Test
	/**
	 * test that a case maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testCaseMaintDocPerms() {
		String docType = "CourtCaseMaintenanceDocument";
		testCreateMaintain(CourtCase.class, docType);
		assertTrue("docType should allow new and copy", 
				KRADServiceLocatorWeb.getDocumentDictionaryService().getAllowsNewOrCopy(docType));
	}
	
	@Test
	/**
	 * test that a conveyance type maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testConvTypeMaintDocPerms() {
		testCreateMaintain(ConveyanceType.class, "ConveyanceTypeMaintenanceDocument");
	}
	
	@Test
	/**
	 * test that a status maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testStatusMaintDocPerms() {
		testCreateMaintain(Status.class, "StatusMaintenanceDocument");
	}
	
	@Test
	/**
	 * test that users in the idmgr group have the relevant permission
	 */
	public void testIdMgrPermissions() {
		Map<String, Boolean> authUsers = getTestUtils().getAuthUsers();
		for (String principalName: authUsers.keySet()) {
			String principalId = getPrincipalIdForName(principalName);
			boolean auth = getPermissionService().isAuthorized(
					principalId,
					KimConstants.NAMESPACE_CODE,
					KimConstants.PermissionNames.MODIFY_ENTITY,
					Collections.singletonMap(KimConstants.AttributeConstants.PRINCIPAL_ID, principalId));
			assertEquals(principalName + " authorization for identity manager is " + authUsers.get(principalName).booleanValue(),
					authUsers.get(principalName).booleanValue(), auth);
		}
	}

}
