/**
 * 
 */
package org.martinlaw.test.type;

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
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.kuali.rice.krad.bo.BusinessObject;
import org.martinlaw.bo.Type;
import org.martinlaw.bo.EventType;
import org.martinlaw.bo.courtcase.CourtCase;

/**
 * test various BO ops for {@link EventType}
 * 
 * @author mugo
 * 
 */
public class EventTypeBOTest extends TypeBoTestBase {
	
	@Test
	/**
	 * test that event type key values returns the correct number
	 */
	public void testEVentTypeKeyValues() {
		final String dataObjectName = "event type(s)";
		final int expectedCourtCaseScopeCount = 2;
		final int expectedContractScopeCount = 0;
		final int expectedConveyanceScopeCount = 1;
		final int expectedEmptyScopeCount = 1;
		final int expectedMatterScopeCount = 0;
		final int expectedLandCaseScopeCount = 0;
		final Class<? extends BusinessObject> scopedClass = EventType.class;
		
		getTestUtils().testScopeKeyValues(dataObjectName, expectedCourtCaseScopeCount,
				expectedContractScopeCount, expectedConveyanceScopeCount,
				expectedEmptyScopeCount, expectedMatterScopeCount,
				expectedLandCaseScopeCount, scopedClass);
	}

	@Override
	public Class<? extends Type> getDataObjectClass() {
		return EventType.class;
	}

	@Override
	public String getDocTypeName() {
		return "EventTypeMaintenanceDocument";
	}

	@Override
	protected void additionalTestsForRetrievedObject(Type type) {
		assertNotNull("scope should not be null", type.getScope());
		assertEquals("scope size differs", 1, type.getScope().size());
		assertEquals("simple class name differs", CourtCase.class.getSimpleName(), type.getScope().get(0).getSimpleClassName());
		
	}

	@Override
	protected void testCrudCreated(Type type) {
		// DO nothing
		
	}

	@Override
	protected void testCrudDeleted(Type type) {
		// DO nothing
		
	}

	@Override
	protected void populateAdditionalFieldsForCrud(Type type) {
		// DO nothing
		
	}

	@Override
	public Type getExpectedOnRetrieve() {
		EventType eventType = new EventType();
		eventType.setId(10031l);
		eventType.setName("Mention");
		
		return eventType;
	}
}
