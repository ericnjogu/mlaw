/**
 * 
 */
package org.martinlaw.keyvalues;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.kuali.rice.core.api.util.KeyValue;
import org.martinlaw.bo.Status;

/**
 * tests {@link org.martinlaw.keyvalues.StatusTypeKeyValues}
 * @author mugo
 *
 */
public class StatusTypeKeyValuesTest {

	/**
	 * Test method for {@link org.martinlaw.keyvalues.StatusTypeKeyValues#getKeyValues()}.
	 */
	@Test
	public void testGetKeyValues() {
		StatusTypeKeyValues kv = new StatusTypeKeyValues();
		List<KeyValue> keyValues = kv.getKeyValues();
		assertNotNull("key values should not be null", keyValues);
		assertFalse("key values size should not be zero", keyValues.size() == 0);
		assertTrue("does not contain expected value", keyValues.contains(Status.ANY_TYPE));
		assertTrue("does not contain expected value", keyValues.contains(Status.CONTRACT_TYPE));
	}

}
