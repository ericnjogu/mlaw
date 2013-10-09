/**
 * 
 */
package org.martinlaw.test.type;

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


import static org.junit.Assert.fail;

import org.kuali.rice.kew.api.exception.WorkflowException;
import org.martinlaw.bo.Type;
import org.martinlaw.bo.MatterType;

/**
 * tests routing for {@link MatterType}
 * @author mugo
 *
 */
public class MatterTypeRoutingTest extends TypeRoutingTestBase {

	@Override
	public Class<? extends Type> getDataObjectClass() {
		return MatterType.class;
	}

	@Override
	public String getDocTypeName() {
		return "MatterTypeMaintenanceDocument";
	}
	
	/**
	 * a copy of {@link org.martinlaw.test.KewTestsBase#testInitiatorFYI()} since the parent class does nothing
	 */
	public void testInitiatorFYI() {
		try {
			testWorkflowRoutingOnly_initiator_FYI(getDocTypeName() + "Test");
		} catch (WorkflowException e) {
			fail(e.getMessage());
		}
	}
	
}
