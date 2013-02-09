/**
 * 
 */
package org.martinlaw.keyvalues;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012 Eric Njogu (kunadawa@gmail.com)
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
		assertTrue("ANY_TYPE is expected as the first value", keyValues.get(0).getKey().equalsIgnoreCase("ANY_TYPE"));
	}
}
