/**
 * 
 */
package org.martinlaw.web;

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
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.krad.web.form.LookupForm;

/**
 * @author mugo
 *
 */
public class WildcardLookupableImplTest {

	private WildcardLookupableImpl lookupable;
	private String propName;
	private String propLocation;
	private String propComment;
	private String propFile;
	private StringBuffer wildCardPropertyNames;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		lookupable = new WildcardLookupableImpl();
		wildCardPropertyNames = new StringBuffer();
		propName = "name";
		propLocation = "location";
		wildCardPropertyNames.append(propLocation);
		propComment = "comment";
		wildCardPropertyNames.append(",");
		wildCardPropertyNames.append(propComment);
		propFile = "file";
		wildCardPropertyNames.append(",");
		wildCardPropertyNames.append(propFile);
	}

	/**
	 * Test method for {@link org.martinlaw.web.WildcardLookupableImpl#processSearchCriteria(org.kuali.rice.krad.web.form.LookupForm, java.util.Map)}.
	 */
	@Test
	public void testProcessSearchCriteria() {
		Map<String, String> searchCriteria = new HashMap<String, String>();
		final String name = "alice";
		searchCriteria.put(propName, name);
		final String location = "*nakuru*";
		searchCriteria.put(propLocation, location);
		final String comment = "*good";
		searchCriteria.put(propComment, comment);
		searchCriteria.put(propFile, "spring");
		searchCriteria.put(WildcardLookupableImpl.WILDCARD_PROPERTYNAMES, wildCardPropertyNames.toString());
		
		Map<String, String> result = lookupable.processSearchCriteria(new LookupForm(), searchCriteria);
		final String message = "search value differs";
		assertEquals(message, name, result.get(propName));
		assertEquals(message, location, result.get(propLocation));
		assertEquals(message, comment, result.get(propComment));
		assertEquals(message, "*spring*", result.get(propFile));
		assertNull("the wildcards entry should have been deleted", result.get(WildcardLookupableImpl.WILDCARD_PROPERTYNAMES));
		
		searchCriteria.put(WildcardLookupableImpl.WILDCARD_PROPERTYNAMES, propFile);
		result = lookupable.processSearchCriteria(new LookupForm(), searchCriteria);
		assertEquals(message, "*spring*", result.get(propFile));
	}
}
