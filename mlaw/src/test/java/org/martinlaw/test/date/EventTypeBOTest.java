/**
 * 
 */
package org.martinlaw.test.date;

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
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.martinlaw.bo.EventType;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test various BO ops for {@link EventType}
 * 
 * @author mugo
 * 
 */
public class EventTypeBOTest extends MartinlawTestsBase {

	@Test(expected = DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testDateTypeNullableFields() {
		EventType eventType = new EventType();
		getBoSvc().save(eventType);
	}

	@Test
	/**
	 * test that the EventType is loaded into the data dictionary
	 */
	public void testDateTypeAttributes() {
		testBoAttributesPresent(EventType.class.getCanonicalName());
		Class<EventType> dataObjectClass = EventType.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from date inserted via sql
	 */
	public void testDateTypeRetrieve() {
		// retrieve object populated via sql script
		EventType eventType = getBoSvc().findBySinglePrimaryKey(
				EventType.class, 1003l);
		assertNotNull(eventType);
		assertEquals("Mention", eventType.getName());
	}

	@Test
	/**
	 * test CRUD for {@link EventType}
	 */
	public void testDateTypeCRUD() {
		// C
		EventType eventType = new EventType();
		String name = "judgment";
		eventType.setName(name);
		getBoSvc().save(eventType);
		// R
		eventType.refresh();
		assertEquals("date type name does not match", name, eventType.getName());
		// U
		eventType.setDescription("When the judgement is delivered");
		eventType.refresh();
		assertNotNull("date type description should not be null", eventType.getDescription());
		// D
		getBoSvc().delete(eventType);
		assertNull(getBoSvc().findBySinglePrimaryKey(EventType.class,
				eventType.getId()));
	}
}
