package org.martinlaw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.directory.shared.ldap.util.ReflectionToStringBuilder;
import org.junit.Test;
import org.kuali.rice.krad.datadictionary.validation.result.ConstraintValidationResult;
import org.kuali.rice.krad.datadictionary.validation.result.DictionaryValidationResult;
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

	private Log log = LogFactory.getLog(getClass());

	public MatterEventBOTest() {
		super();
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testMatterEventNullableFields() throws InstantiationException, IllegalAccessException {
		getBoSvc().save(getDataObjectClass().newInstance());
	}

	@Test
	public void testMatterEventAttributes() {
		testBoAttributesPresent(getDataObjectClass().getCanonicalName());
		verifyMaintDocDataDictEntries(getDataObjectClass());
	}

	@Test
	public void testMatterEventRetrieve() {
		// retrieve object populated via sql script
		getTestUtils().testRetrievedMatterEventFields(getBoSvc().findBySinglePrimaryKey(getDataObjectClass(), 1001l));
	}

	@Test
	public void testMatterEventCRUD() throws InstantiationException, IllegalAccessException {
		testMatterEventCRUD(getTestUtils().getTestMatterEvent(getDataObjectClass()), getDataObjectClass());
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

	@Test
	public void testMatterEvent_date_validation()
	throws InstantiationException, IllegalAccessException {
		try {
			DictionaryValidationResult result = KRADServiceLocatorWeb.getDictionaryValidationService().validate(
					getTestUtils().getTestMatterEvent(getDataObjectClass()), getDataObjectClass().getCanonicalName(), "endDate", true);
			final Iterator<ConstraintValidationResult> iterator = result.iterator();
			while (iterator.hasNext()) {
				final ConstraintValidationResult validationResult = iterator.next();
				// using error level to avoid having to configure logging
				log.error(ReflectionToStringBuilder.toString(validationResult));
			}
			assertEquals("expected no errors", 0, result.getNumberOfErrors());
		} catch (Exception e) {
			log.error("exception occured", e);
			fail("exception occured");
		}
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