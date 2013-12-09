/**
 * 
 */
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.kim.bo.ui.PersonDocumentPhone;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.datadictionary.AttributeDefinition;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.control.TextAreaControl;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.util.ErrorMessage;
import org.kuali.rice.krad.util.GlobalVariables;
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.Scope;
import org.martinlaw.bo.courtcase.CourtCase;

/**
 * various validation tests
 * @author mugo
 *
 */
@SuppressWarnings("deprecation")
public class ValidationTests extends MartinlawTestsBase {

	/**
	 * validate the DD
	 */
	@Test
	public void validateDataDictionary() {
		KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().validateDD(true);
	}
	
	/**
	 * test that phone validation on the KNS new/edit user screen can accept a custom format
	 * <p>Only passes when a custom properties file with modified regex, e.g. org/martinlaw/mlaw.properties is added to 
	 * the default list of rice.struts.message.resources in the local config file 
	 * i.e. the value is  copied as-is from common-config-defaults.xml and the custom properties file appended to 
	 * the end of the list so that it can override the default regex pattern</p>
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testMatterEvent_phone_validation()
	throws InstantiationException, IllegalAccessException {
		try {
			String regexPattern = "validationPatternRegex.phoneNumber";
			log.error(regexPattern + ":" + CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString(regexPattern));
			KNSServiceLocator.getKNSDictionaryValidationService().validateAttributeFormat(
					PersonDocumentPhone.class.getCanonicalName(), "phoneNumber", "0722-123-456", "phoneNumber");
			if (!GlobalVariables.getMessageMap().hasNoErrors()) {
				for (String key: GlobalVariables.getMessageMap().getErrorMessages().keySet()) {
					for (ErrorMessage item: GlobalVariables.getMessageMap().getErrorMessages().get(key)) {
						log.error(key + ":" + item.toString());
					}
				}
			}
			assertTrue("there should be no errors", GlobalVariables.getMessageMap().hasNoErrors());
		} catch (Exception e) {
			log.error("exception occured", e);
			fail("exception occured");
		}
	}
	
	/**
	 * test that java class constraint is working ok
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testScope_validation()
	throws InstantiationException, IllegalAccessException {
		Scope statusScope = new Scope();
		statusScope.setQualifiedClassName("");
		final String attributeName = "qualifiedClassName";
		getTestUtils().validate(statusScope, 1, attributeName);
		
		statusScope.setQualifiedClassName("org.martinlaw.Aclass");
		getTestUtils().validate(statusScope, 0, attributeName);
	}
	
	/**
	 * test that Constraint is working ok
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testLocalReference_validation()
	throws InstantiationException, IllegalAccessException {
		Matter kase = new CourtCase();
		kase.setLocalReference("my/firm/ cases/2013");
		final String attributeName = "localReference";
		getTestUtils().validate(kase, 1, attributeName);
		
		kase.setLocalReference("MY/FIRM/cases/2013");
		getTestUtils().validate(kase, 1, attributeName);
		
		kase.setLocalReference("MY/FIRM/CASES/2013");
		getTestUtils().validate(kase, 0, attributeName);
		
		kase.setLocalReference("MY/FIRM/CASES(5)/2013");
		getTestUtils().validate(kase, 0, attributeName);
	}
	
	/**
	 * check whether the additional files override the intended beans for krad
	 * @see org/martinlaw/rice-overrides/kr.xml
	 */
	@Test
	public void testAdditionalFiles_krad() {		
		InputField desc = (InputField) KRADServiceLocatorWeb.getDataDictionaryService().getDictionaryObject(
				"Uif-DocumentDescription");
		// see org.kuali.rice.krad.uif.control.TextControl#setWatermarkText for why the additional space is added
		assertEquals("instructional msg differs", "A brief statement of the action you are taking", 
				desc.getInstructionalText());
		
		AttributeDefinition expln = (AttributeDefinition) KRADServiceLocatorWeb.getDataDictionaryService().getDictionaryObject(
				"DocumentHeader-explanation");
		assertNotNull("watermark should not be null", ((TextAreaControl)expln.getControlField()).getWatermarkText());
	}
}
