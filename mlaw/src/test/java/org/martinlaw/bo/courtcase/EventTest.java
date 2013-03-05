/**
 * 
 */
package org.martinlaw.bo.courtcase;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.datetime.DateTimeService;
import org.kuali.rice.krad.datadictionary.AttributeDefinition;
import org.kuali.rice.krad.datadictionary.validation.ErrorLevel;
import org.kuali.rice.krad.datadictionary.validation.SingleAttributeValueReader;
import org.kuali.rice.krad.datadictionary.validation.constraint.DatePatternConstraint;
import org.kuali.rice.krad.datadictionary.validation.processor.ValidCharactersConstraintProcessor;
import org.kuali.rice.krad.datadictionary.validation.result.ConstraintValidationResult;
import org.kuali.rice.krad.datadictionary.validation.result.DictionaryValidationResult;

/**
 * @author mugo
 * 
 */
public class EventTest {
	private Log log = LogFactory.getLog(getClass());

	@Test
	/**
	 * tests the date time format configured for the date time picker
	 * <p>code adapted from {@link org.kuali.rice.krad.datadictionary.validation.constraint.DatePatternConstraintTest}
	 */
	public void testDateTimeFormatValidation() {
		final String attributeName = "startDate";
		AttributeDefinition dateDefinition = new AttributeDefinition();
		dateDefinition.setName(attributeName);
		//String date = "28 Feb 2013 03:50 PM";
		Timestamp date = new Timestamp(System.currentTimeMillis());
		SingleAttributeValueReader attributeValueReader = new SingleAttributeValueReader(
				date, Event.class.getCanonicalName(), attributeName,
				dateDefinition);
		DictionaryValidationResult dictionaryValidationResult = new DictionaryValidationResult();
		dictionaryValidationResult.setErrorLevel(ErrorLevel.NOCONSTRAINT);

		ValidCharactersConstraintProcessor processor = new ValidCharactersConstraintProcessor();
		// setup mocks
		Config config = mock(Config.class);
		final String dtFormat = "dd MMM yyyy hh:mm a";
		when(config.getProperty(CoreConstants.STRING_TO_DATE_FORMATS))
				.thenReturn(dtFormat);
		ConfigContext.overrideConfig(Thread.currentThread()
				.getContextClassLoader(), config);
		DateTimeService dtSvc = mock(DateTimeService.class);
		SimpleDateFormat sdf = new SimpleDateFormat(dtFormat);
		when(dtSvc.toDateTimeString(date)).thenReturn(sdf.format(date));
		attributeValueReader.setDateTimeService(dtSvc);
		// create allowed formats
		List<String> allowedFormats = new ArrayList<String>();
		allowedFormats.add(dtFormat);
		allowedFormats.add("d MMM yyyy hh:mm a");
		allowedFormats.add("dd MMM yyyy");
		allowedFormats.add("d MMM yyyy");
		// carry out validation
		DatePatternConstraint constraint = new DatePatternConstraint();
		constraint.setAllowedFormats(allowedFormats);
		ConstraintValidationResult result = processor.process(
				dictionaryValidationResult, date, constraint,
				attributeValueReader).getFirstConstraintValidationResult();
		// assertions
		log.info(ToStringBuilder.reflectionToString(dictionaryValidationResult));
		assertEquals("number of warnings differs", 0,
				dictionaryValidationResult.getNumberOfWarnings());
		assertEquals("number of errors differs", 0,
				dictionaryValidationResult.getNumberOfErrors());
		assertEquals("error level differs", ErrorLevel.OK, result.getStatus());
		assertEquals("constraint name differs",
				new ValidCharactersConstraintProcessor().getName(),
				result.getConstraintName());
	}

}
