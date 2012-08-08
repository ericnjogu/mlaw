/**
 * 
 */
package org.martinlaw.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.DataObjectAuthorizationService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.Conveyance;
import org.martinlaw.bo.ConveyanceType;
import org.martinlaw.bo.CourtCase;
import org.martinlaw.bo.Status;

/**
 * holds various permissions tests
 * 
 * @author mugo
 * 
 */
public class KEWPermissionsTest extends KewTestsBase {

	private DataObjectAuthorizationService dataObjAuthSvc;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/perms-roles.sql",
				";").runSql();
		new SQLDataLoader(
				"classpath:org/martinlaw/scripts/test-perms-roles.sql", ";")
				.runSql();
	}

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
		testCreateMaintain(CourtCase.class, "CaseMaintenanceDocument");
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

	/**
	 * a common method to test create and maintain permissions
	 * 
	 * @param klass - the data object class
	 * @param docType - the document type name
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 */
	protected void testCreateMaintain(Class<?> klass, String docType) {
		String[] authPrincipalNames = { "clerk1", "lawyer1"};
		for (String principalName : authPrincipalNames) {
			assertTrue(principalName + " has permission to create", dataObjAuthSvc.canCreate(klass, 
					KimApiServiceLocator.getPersonService().getPersonByPrincipalName(principalName), docType));
			
			try {
				assertTrue(principalName + " has permission to maintain", dataObjAuthSvc.canMaintain(klass.newInstance(), 
						KimApiServiceLocator.getPersonService().getPersonByPrincipalName(principalName), docType));
			} catch (InstantiationException e) {
				fail(e.getMessage());
			} catch (IllegalAccessException e) {
				fail(e.getMessage());
			}
		}
		
		String[] nonAuthPrincipalNames = { "witness1", "client1" };
		for (String principalName : nonAuthPrincipalNames) {
			assertFalse(principalName + " has no permission to create", dataObjAuthSvc.canCreate(klass,
					KimApiServiceLocator.getPersonService()
							.getPersonByPrincipalName(principalName), docType));
			
			try {
				assertFalse(principalName + " has no permission to maintain", dataObjAuthSvc.canMaintain(klass.newInstance(), 
						KimApiServiceLocator.getPersonService().getPersonByPrincipalName(principalName), docType));
			} catch (InstantiationException e) {
				fail(e.getMessage());
			} catch (IllegalAccessException e) {
				fail(e.getMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kuali.test.KRADTestCase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		dataObjAuthSvc = KRADServiceLocatorWeb
				.getDataObjectAuthorizationService();
	}

}
