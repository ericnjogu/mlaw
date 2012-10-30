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
