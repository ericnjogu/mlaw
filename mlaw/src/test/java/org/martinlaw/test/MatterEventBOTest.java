package org.martinlaw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Iterator;

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
	public void testMatterEventCRUD() throws InstantiationException,
			IllegalAccessException {
				getTestUtils().testMatterEventCRUD(getTestUtils().getTestMatterEvent(getDataObjectClass()), getDataObjectClass());
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