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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link org.martinlaw.keyvalues.MatterScopeKeyValues#getKeyValues()}.
	 */
	@Test
	public void testGetKeyValues() {
		MatterScopeKeyValues skv = new MatterScopeKeyValues();
		assertNotNull("key values should not be null", skv.getKeyValues());
		assertEquals("should be 4 matters, one blank", 5, skv.getKeyValues().size());
		assertEquals("label differs", Contract.class.getSimpleName(), skv.getKeyLabel(Contract.class.getCanonicalName()));
	}
}
