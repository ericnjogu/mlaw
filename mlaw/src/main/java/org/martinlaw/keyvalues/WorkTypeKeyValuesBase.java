/**
 * 
 */
package org.martinlaw.keyvalues;

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


import java.util.List;

import org.kuali.rice.core.api.util.KeyValue;
import org.martinlaw.bo.WorkType;

/**
 * displays work types whose {@link WorkType#getScope()} is either empty or includes the specific matter (via the class name)
 * 
 * @author mugo
 *
 */
public abstract class WorkTypeKeyValuesBase extends ScopedKeyValuesBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3622671458824630258L;

	/**
	 * gets every status whose scope either includes the provided matter class name or has an empty scope (applies to all)
	 * 
	 * @param qualifiedMatterClassName - the class name of the matter for which we should retrieve statuses
	 * @return matching status as key values
	 */
	public List<KeyValue> getKeyValues(String qualifiedMatterClassName) {
		return super.getKeyValues(qualifiedMatterClassName, WorkType.class);
	}
}
