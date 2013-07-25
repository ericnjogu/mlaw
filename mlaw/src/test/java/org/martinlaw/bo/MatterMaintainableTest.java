/**
 * 
 */
package org.martinlaw.bo;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2013 Eric Njogu (kunadawa@gmail.com)
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

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * tests {@link org.martinlaw.bo.MatterMaintainable}
 * @author mugo
 */
public class MatterMaintainableTest {

	private MatterMaintainable maintainable;
	
	@Test
	/**
	 * Test method for {@link org.martinlaw.bo.MatterMaintainable#createPrincipalName(java.lang.String)}.
	 */
	public void testCreatePrincipalName() {
		String msg = "principal name differs";
		assertEquals(msg, "peter_kimani", maintainable.createPrincipalName("Peter  Kimani 	"));
		assertEquals(msg, "muchiri", maintainable.createPrincipalName(" MUCHIRI			"));
		assertEquals(msg, "peter_ngugi_kambo", maintainable.createPrincipalName("Peter	 Ngugi Kambo "));
	}

	@Before
	public void setUp() {
		maintainable = new MatterMaintainable();
	}
	
	@Test
	/**
	 * tests {@link MatterMaintainable#getFirstName(java.lang.String)
	 */
	public void testGetFirstName() {
		String msg = "first name differs";
		assertEquals(msg, "Robert", maintainable.getFirstName("	 Robert 	Gakuo "));
		assertEquals(msg, "Scott", maintainable.getFirstName("	 Scott	 "));
	}
	
	@Test
	/**
	 * tests {@link MatterMaintainable#getFirstName(java.lang.String)
	 */
	public void testGetMiddleName() {
		String msg = "middle name differs";
		assertEquals(msg, "Muturi", maintainable.getMiddleName("	 Jeremy Muturi	 Hanson	 "));
		assertEquals(msg, "", maintainable.getMiddleName("	 Moses	 Mungai	 "));
		assertEquals(msg, "Wanjiru Kanyiri", maintainable.getMiddleName("	 Hellen Wanjiru  Kanyiri 	Njogu	 "));
	}
	
	@Test
	/**
	 * tests {@link MatterMaintainable#getFirstName(java.lang.String)
	 */
	public void testGetLastName() {
		String msg = "first name differs";
		assertEquals(msg, "Mugo", maintainable.getLastName("Simon Mugo"));
		assertEquals(msg, "Kamau", maintainable.getLastName(" Samwel 	Boxer	 Kamau"));
		assertEquals(msg, "", maintainable.getLastName("Waititu"));
	}
}
