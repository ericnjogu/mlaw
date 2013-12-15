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

import java.util.Map;

import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;

/**
 * adds wildcards to selected fields
 * <p>{@link #processSearchCriteria(LookupForm, Map)} is overriden to add wildcards to criteria belonging to the fields specified
 * using {@link #WILDCARD_PROPERTYNAMES}
 * @author mugo
 *
 */
public class WildcardLookupableImpl extends LookupableImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8704912161597156451L;
	public static String WILDCARD_PROPERTYNAMES = "wildcardPropertyNames";

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.lookup.LookupableImpl#processSearchCriteria(org.kuali.rice.krad.web.form.LookupForm, java.util.Map)
	 */
	@Override
	protected Map<String, String> processSearchCriteria(LookupForm lookupForm,
			Map<String, String> searchCriteria) {
		String wildcardPropertyNames = searchCriteria.get(WILDCARD_PROPERTYNAMES);
		searchCriteria =  super.processSearchCriteria(lookupForm, searchCriteria);
		if (wildcardPropertyNames != null && searchCriteria != null) { 
			String[] wcpList = wildcardPropertyNames.split(",");
			for (String propertyName: wcpList) {
				propertyName = propertyName.trim();
				if (searchCriteria.containsKey(propertyName)) {
					String value = searchCriteria.get(propertyName);
					if (!value.contains("*")) {
						searchCriteria.put(propertyName, "*" + value + "*");
					}
				}
			}
			searchCriteria.remove(WILDCARD_PROPERTYNAMES);
		}
		return searchCriteria;
	}
}
