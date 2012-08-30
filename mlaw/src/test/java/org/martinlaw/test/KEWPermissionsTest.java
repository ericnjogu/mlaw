/**
 * 
 */
package org.martinlaw.test;


import org.junit.Test;
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

}
