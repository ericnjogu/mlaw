/**
 * 
 */
package org.martinlaw.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.kuali.rice.kim.bo.ui.PersonDocumentPhone;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ErrorMessage;
import org.kuali.rice.krad.util.GlobalVariables;
import org.martinlaw.bo.StatusScope;

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
			log.error(regexPattern + ":" + KRADServiceLocator.getKualiConfigurationService().getPropertyValueAsString(regexPattern));
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
	 * test that NoWhitespacePatternConstraint is working ok
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testScope_validation()
	throws InstantiationException, IllegalAccessException {
		StatusScope statusScope = new StatusScope();
		statusScope.setQualifiedClassName("");
		final String attributeName = "qualifiedClassName";
		getTestUtils().validate(statusScope, 1, attributeName);
		
		statusScope.setQualifiedClassName("org.martinlaw.Aclass");
		getTestUtils().validate(statusScope, 0, attributeName);
	}
}
