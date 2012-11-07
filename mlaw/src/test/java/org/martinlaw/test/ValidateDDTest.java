/**
 * 
 */
package org.martinlaw.test;

import org.junit.Test;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

/**
 * validate the DD
 * @author mugo
 *
 */
public class ValidateDDTest extends MartinlawTestsBase {

	@Test
	public void validateDataDictionary() {
		KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().validateDD(true);
	}
}
