/**
 * 
 */
package org.martinlaw.keyvalues;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.martinlaw.bo.contract.Contract;

/**
 * @author mugo
 *
 */
public class MatterScopeKeyValuesTest {

	private MatterScopeKeyValues skv;


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		skv = new MatterScopeKeyValues();
	}

	/**
	 * Test method for {@link org.martinlaw.keyvalues.MatterScopeKeyValues#getKeyValues()}.
	 */
	@Test
	public void testGetKeyValues() {
		assertNotNull("key values should not be null", skv.getKeyValues());
		assertEquals("should be 4 matters, one blank", 5, skv.getKeyValues().size());
		assertEquals("label differs", Contract.class.getSimpleName(), skv.getKeyLabel(Contract.class.getCanonicalName()));
	}
	

	/**
	 * Test method for {@link org.martinlaw.keyvalues.MatterScopeKeyValues#getKeyValues()}.
	 */
	@Test
	public void testGetKeyValues_none_existent_pkg() {
		skv.setBasePackage("org.none_existent");
		assertEquals("should be one blank entry", 1, skv.getKeyValues().size());
	}
	
	/**
	 * Test method for {@link org.martinlaw.keyvalues.MatterScopeKeyValues#getKeyValues()}.
	 */
	@Test
	public void testGetKeyValues_no_matters_pkg() {
		skv.setBasePackage("org.junit");
		assertEquals("should be one blank entry", 1, skv.getKeyValues().size());
	}
}
