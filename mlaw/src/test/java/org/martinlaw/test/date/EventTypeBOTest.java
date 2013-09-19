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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.martinlaw.bo.EventType;
import org.martinlaw.bo.EventTypeScope;
import org.martinlaw.bo.contract.Contract;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.keyvalues.ScopedKeyValuesUif;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;
import static org.mockito.Mockito.when;

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
	public void testEventTypeNullableFields() {
		EventType eventType = new EventType();
		getBoSvc().save(eventType);
	}

	@Test
	/**
	 * test that the EventType is loaded into the data dictionary
	 */
	public void testEventTypeAttributes() {
		testBoAttributesPresent(EventType.class.getCanonicalName());
		Class<EventType> dataObjectClass = EventType.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * tests retrieving from date inserted via sql
	 */
	public void testEventTypeRetrieve() {
		// retrieve object populated via sql script
		EventType eventType = getBoSvc().findBySinglePrimaryKey(
				EventType.class, 1003l);
		assertNotNull("event type should not be null", eventType);
		assertEquals("event type name differs", "Mention", eventType.getName());
		assertNotNull("scope should not be null", eventType.getScope());
		assertEquals("scope size differs", 1, eventType.getScope().size());
		assertEquals("simple class name differs", CourtCase.class.getSimpleName(), eventType.getScope().get(0).getSimpleClassName());
	}

	@Test
	/**
	 * test CRUD for {@link EventType}
	 */
	public void testEventTypeCRUD() {
		// C
		EventType eventType = new EventType();
		String name = "judgment";
		eventType.setName(name);
		
		EventTypeScope scope1 = new EventTypeScope();
		scope1.setQualifiedClassName(CourtCase.class.getCanonicalName());
		eventType.getScope().add(scope1);
		
		EventTypeScope scope2 = new EventTypeScope();
		scope2.setQualifiedClassName(Contract.class.getCanonicalName());
		eventType.getScope().add(scope2);
		
		getBoSvc().save(eventType);
		// R
		eventType.refresh();
		assertEquals("event type name does not match", name, eventType.getName());
		assertNotNull("scope should not be null", eventType.getScope());
		assertEquals("scope size differs", 2, eventType.getScope().size());
		assertNull("description not set", eventType.getDescription());
		// U
		eventType.setDescription("When the judgement is delivered");
		eventType.getScope().remove(1);
		getBoSvc().save(eventType);
		assertNotNull("event type description should not be null", eventType.getDescription());
		assertEquals("scope size differs", 1, eventType.getScope().size());
		assertEquals("simple class name differs", CourtCase.class.getSimpleName(), eventType.getScope().get(0).getSimpleClassName());
		// D
		getBoSvc().delete(eventType);
		assertNull("event type should have been deleted", getBoSvc().findBySinglePrimaryKey(EventType.class,
				eventType.getId()));
		Map<String, String> criteria = new HashMap<String, String>();
		criteria.put("eventTypeId", String.valueOf(eventType.getId()));
		assertTrue("event type scopes should have been deleted", getBoSvc().findMatching(EventTypeScope.class, criteria).isEmpty());
	}
	
	@Test
	/**
	 * test that event type key values returns the correct number
	 */
	public void testMatterStatusKeyValues() {
		ScopedKeyValuesUif kv = new ScopedKeyValuesUif();
		kv.setScopedClass(EventType.class);
		
		MaintenanceDocumentForm form = getTestUtils().createMockMaintenanceDocForm();
		Maintainable newMaintainableObject = form.getDocument().getNewMaintainableObject();
		
		when(newMaintainableObject.getDataObject()).thenReturn(new CourtCase());
		String comment = "expected 2 event types with court case scope and one that apply to all (empty)";
		assertEquals(comment, 3, kv.getKeyValues(form).size());
		
		comment = "expected 1 that applies to all (empty)";
		when(newMaintainableObject.getDataObject()).thenReturn(new Contract());
		assertEquals(comment, 1, kv.getKeyValues(form).size());
		
		/*comment = "expected 1 that applies to all (empty), plus a blank one";
		getTestUtils().testMatterStatusKeyValues(new OpinionEventTypeKeyValues(), comment, 2);*/
		
		comment = "expected one status with conveyance scope, one that applies to all (empty)";
		when(newMaintainableObject.getDataObject()).thenReturn(new Conveyance());
		assertEquals(comment, 2, kv.getKeyValues(form).size());
	}
}
