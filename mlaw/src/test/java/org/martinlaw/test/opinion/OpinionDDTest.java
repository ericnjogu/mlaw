/**
 * 
 */
package org.martinlaw.test.opinion;


import org.junit.Test;
import org.martinlaw.bo.opinion.Opinion;
import org.martinlaw.bo.opinion.Client;
import org.martinlaw.bo.opinion.Fee;
import org.martinlaw.test.MartinlawTestsBase;

/**
 * tests data dictionary of {@link Opinion}, {@link Fee} and {@link Client}
 * 
 * @author mugo
 *
 */
public class OpinionDDTest extends MartinlawTestsBase {
	@Test
	/**
	 * test that {@link Client} is loaded into the data dictionary
	 */
	public void testOpinionClientAttributes() {
		super.testBoAttributesPresent(Client.class.getCanonicalName());
	}
	
	@Test
	/**
	 * test that {@link Client} is loaded into the data dictionary
	 */
	public void testOpinionFeeAttributes() {
		super.testBoAttributesPresent(Fee.class.getCanonicalName());
	}
	
	@Test
	/**
	 * test that {@link Opinion} is loaded into the data dictionary
	 */
	public void testOpinionAttributes() {
		super.testBoAttributesPresent(Opinion.class.getCanonicalName());
		Class<Opinion> dataObjectClass = Opinion.class;
		super.verifyMaintDocDataDictEntries(dataObjectClass);
	}
}
