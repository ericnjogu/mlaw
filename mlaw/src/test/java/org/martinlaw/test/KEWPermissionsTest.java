/**
 * 
 */
package org.martinlaw.test;

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



import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.document.IdentityManagementPersonDocument;
import org.kuali.rice.kim.document.authorization.IdentityManagementKimDocumentAuthorizer;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.util.GlobalVariables;
import org.martinlaw.MartinlawConstants;

/**
 * holds various permissions tests
 * 
 * @author mugo
 * 
 */
public class KEWPermissionsTest extends KewTestsBase {

	@Test
	/**
	 * test that users in the idmgr group have the relevant permissions
	 */
	public void testIdMgrPermissions_modifyEntity() {
		Map<String, Boolean> authUsers = getTestUtils().getAuthUsers();
		// create/edit users
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
	
	@Test
	/**
	 * test that users in the idmgr group have the relevant permissions
	 */
	public void testIdMgrPermissions_populateGroup() throws WorkflowException {
		Map<String, Boolean> authUsers = getTestUtils().getAuthUsers();
		GlobalVariables.setUserSession(new UserSession("admin"));
		//Document document = KRADServiceLocatorWeb.getDocumentService().getNewDocument(IdentityManagementPersonDocument.class);
		Document document = mock(IdentityManagementPersonDocument.class);
		DocumentHeader header = mock(DocumentHeader.class);
		WorkflowDocument wd = mock(WorkflowDocument.class);
		when(document.getDocumentHeader()).thenReturn(header);
		when(header.getWorkflowDocument()).thenReturn(wd);
		when(document.getDocumentNumber()).thenReturn("1001");
		when(wd.getDocumentTypeName()).thenReturn("IdentityManagementPersonDocument");
		when(wd.isInitiated()).thenReturn(true);
		when(wd.getStatus()).thenReturn(DocumentStatus.fromCode("I"));
		// assign users to groups
		// adapted from org.kuali.rice.kim.rules.ui.GroupDocumentMemberRule#validAssignGroup
		for (String principalName: authUsers.keySet()) {
			String principalId = getPrincipalIdForName(principalName);
			Map<String,String> roleDetails = new HashMap<String,String>();
			roleDetails.put(KimConstants.AttributeConstants.NAMESPACE_CODE, MartinlawConstants.MODULE_NAMESPACE_CODE);
			roleDetails.put(KimConstants.AttributeConstants.GROUP_NAME, "org.martinlaw.client");
			boolean auth = new IdentityManagementKimDocumentAuthorizer().isAuthorizedByTemplate(
					document, 
					KimConstants.NAMESPACE_CODE, 
					KimConstants.PermissionTemplateNames.POPULATE_GROUP,
					principalId, 
					roleDetails, null);
			assertEquals(principalName + " authorization for identity manager is " + authUsers.get(principalName).booleanValue(),
					authUsers.get(principalName).booleanValue(), auth);
		}
	}

	public String getDocTypeName() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#testInitiatorFYI()
	 */
	/**
	 * no particular document is represented by this test class
	 */
	@Override
	public void testInitiatorFYI() {
		// do nothing
	}

	@Override
	public Class<?> getDataObjectClass() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#testCreateMaintain(java.lang.Class, java.lang.String)
	 */
	@Override
	protected void testCreateMaintain(Class<?> klass, String docType) {
		// do nothing
	}

}
