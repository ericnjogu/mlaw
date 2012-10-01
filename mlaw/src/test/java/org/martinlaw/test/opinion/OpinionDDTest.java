/**
 * 
 */
package org.martinlaw.test.opinion;


import org.junit.Test;
import org.martinlaw.bo.opinion.Opinion;
import org.martinlaw.bo.opinion.OpinionClient;
import org.martinlaw.bo.opinion.OpinionFee;
import org.martinlaw.test.MartinlawTestsBase;

/**
 * tests data dictionary of {@link Opinion}, {@link OpinionFee} and {@link OpinionClient}
 * 
 * @author mugo
 *
 */
public class OpinionDDTest extends MartinlawTestsBase {
	@Test
	/**
	 * test that {@link OpinionClient} is loaded into the data dictionary
	 */
	public void testOpinionClientAttributes() {
		super.testBoAttributesPresent(OpinionClient.class.getCanonicalName());
	}
	
	@Test
	/**
	 * test that {@link OpinionClient} is loaded into the data dictionary
	 */
	public void testOpinionFeeAttributes() {
		super.testBoAttributesPresent(OpinionFee.class.getCanonicalName());
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
