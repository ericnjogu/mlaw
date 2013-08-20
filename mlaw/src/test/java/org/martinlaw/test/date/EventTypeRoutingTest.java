/**
 * 
 */
package org.martinlaw.test.date;

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


import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.martinlaw.bo.BaseDetail;
import org.martinlaw.bo.EventType;
import org.martinlaw.test.type.BaseDetailRoutingTestBase;

/**
 * tests routing for {@link EventType}
 * @author mugo
 *
 */
public class EventTypeRoutingTest extends BaseDetailRoutingTestBase {
	
	@Test
	/**
	 * test that a conveyance maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testDateTypeMaintDocPerms() {
		testCreateMaintain(EventType.class, getDocTypeName());
	}
	
	@Test
	/**
	 * tests that the document type is loaded ok
	 */
	public void testContractTypeDocType() {
		assertNotNull("document type should not be null", getDocTypeSvc().findByName(getDocTypeName()));
	}

	public String getDocTypeName() {
		return "EventTypeMaintenanceDocument";
	}

	@Override
	public Class<? extends BaseDetail> getDataObjectClass() {
		return EventType.class;
	}
}
