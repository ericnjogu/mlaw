/**
 * 
 */
package org.martinlaw.test.contract;

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
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.martinlaw.bo.contract.ContractType;
import org.martinlaw.test.KewTestsBase;

/**
 * tests routing for {@link ContractType}
 * @author mugo
 *
 */
public class ContractTypeRoutingTest extends KewTestsBase {
	private Log log = LogFactory.getLog(getClass());

	@Test
	/**
	 * test that ConveyanceAnnexTypeDocument routes to final on submit
	 */
	public void testContractTypeRouting() {
		ContractType contractType = new ContractType();
		String name = "resale agreement";
		contractType.setName(name);
		try {
			testMaintenanceRoutingInitToFinal("ContractTypeMaintenanceDocument", contractType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log .error("test failed", e);
			fail("test routing ContractTypeMaintenanceDocument caused an exception");
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		Collection<ContractType> result = getBoSvc().findMatching(ContractType.class, params);
		assertEquals(1, result.size());
	}
	
	@Test
	/**
	 * test that a conveyance maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testContractTypeMaintDocPerms() {
		testCreateMaintain(ContractType.class, "ContractTypeMaintenanceDocument");
	}
}
