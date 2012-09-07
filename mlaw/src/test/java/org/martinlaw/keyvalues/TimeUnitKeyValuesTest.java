/**
 * 
 */
package org.martinlaw.keyvalues;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author mugo
 *
 */
public class TimeUnitKeyValuesTest {

	/**
	 * Test method for {@link org.martinlaw.keyvalues.TimeUnitKeyValues#getKeyValues(java.lang.String)}.
	 */
	@Test
	public void testGetKeyValuesString() {
		TimeUnitKeyValues kv = new TimeUnitKeyValues();
		assertNotNull("key values should not be null", kv.getKeyValues());
		int expectSize = 5; // including blank
		assertEquals("expected " + expectSize + " key values", expectSize, kv.getKeyValues().size());
	}

}
