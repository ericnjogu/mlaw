package org.martinlaw.test;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.MatterEvent;
import org.springframework.dao.DataIntegrityViolationException;
/**
 * holds common BO tests for children of {@link MatterEvent} 
 * @author mugo
 *
 */
public abstract class MatterEventBOTest extends MartinlawTestsBase {

	public MatterEventBOTest() {
		super();
	}

	/**
	 * test that nullable fields are checked by the db
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testMatterEventNullableFields() throws InstantiationException, IllegalAccessException {
		getBoSvc().save(getDataObjectClass().newInstance());
	}

	/**
	 * verify DD attributes
	 */
	@Test
	public void testMatterEventAttributes() {
		testBoAttributesPresent(getDataObjectClass().getCanonicalName());
		verifyMaintDocDataDictEntries(getDataObjectClass());
	}

	/**
	 * test retrieving an event inserted via sql
	 */
	@Test
	public void testMatterEventRetrieve() {
		// retrieve object populated via sql script
		getTestUtils().testRetrievedMatterEventFields(getBoSvc().findBySinglePrimaryKey(getDataObjectClass(), 1001l));
	}

	/**
	 * test CRUD ops
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testMatterEventCRUD() throws InstantiationException, IllegalAccessException {
		testMatterEventCRUD(getTestUtils().getTestMatterEventIT(getDataObjectClass()), getDataObjectClass());
	}
	
	/**
	 * tests CRUD for descendants of {@link MatterEvent}
	 * @param event - the object to test with
	 * @param matterEvent - the type of {@code MatterEvent} for use in fetching from {@code #getBoSvc()}
	 */
	public <D extends MatterEvent> void testMatterEventCRUD(MatterEvent event, Class<D> matterEvent) {
		// C
		final Timestamp ts1 = new Timestamp(System.currentTimeMillis());
		event.setDateModified(ts1);
		getBoSvc().save(event);
		// R
		event.refresh(); // get the created pk
		// for some reason, the date modified remains null even after a refresh, so re-fetch
		event = getBoSvc().findBySinglePrimaryKey(event.getClass(), event.getId());
		assertEquals("comment differs", "must attend", event.getComment());
		SimpleDateFormat sdf =  new SimpleDateFormat("dd-MM-yy");
		assertEquals(sdf.format(Calendar.getInstance().getTime()), sdf.format(event.getStartDate()));
		// test for default date values
		assertNotNull("date created should have a default value", event.getDateCreated());
		assertNotNull("date modified should have a default value", event.getDateModified());
		try {
			Thread.sleep(10);//ensure that some time lapses before we update
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// U
		final String comment = "must attend - dept heads only";
		event.setComment(comment);
		final Timestamp ts2 = new Timestamp(System.currentTimeMillis());
		event.setDateModified(ts2);
		assertTrue("for the update test to work, the initial and subsequent timestamps should be different", ts2.after(ts1));
		//date.refresh();
		getBoSvc().save(event);
		event = getBoSvc().findBySinglePrimaryKey(event.getClass(), event.getId());
		assertEquals("comment differs", comment, event.getComment());
		//confirm that date modified has changed
		// for some reason, the date modification test fails
		assertTrue("date should have been updated", event.getDateModified().after(ts1));
		// D
		getBoSvc().delete(event);
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("comment", comment);
		assertEquals("date should have been deleted", 0, getBoSvc().findMatching(matterEvent, map).size());
	}

	/**
	 * confirm that the set label can be retrieved
	 */
	@Test
	public void testMatterIdLabel() {
		final String attributeLabel = KRADServiceLocatorWeb.getDataDictionaryService().getAttributeLabel(
						getDataObjectClass(),
						MartinlawConstants.PropertyNames.MATTER_ID);
		assertFalse("label should not be blank", StringUtils.isEmpty(attributeLabel));
		assertEquals("label not the expected value", getMatterIdLabel(), attributeLabel);
	}
	
	/**
	 * test that date validation is working ok
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testMatterEvent_date_validation()
	throws InstantiationException, IllegalAccessException {
		getTestUtils().validate(getTestUtils().getTestMatterEventIT(getDataObjectClass()), 0, "endDate");
	}
	
	/**
	 * 
	 * @return the data object (BO) class
	 */
	public abstract Class<? extends MatterEvent> getDataObjectClass();
	
	/**
	 * @return the expected label
	 */
	public abstract String getMatterIdLabel();

}