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
import org.martinlaw.bo.conveyance.Conveyance;

/**
 * displays consideration types whose scope is not set or which includes @{link Conveyance}
 * 
 * @author mugo
 *
 */
public class ConveyanceConsiderationTypeKeyValues  extends ConsiderationTypeKeyValuesBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3939234064376747895L;

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.keyvalues.KeyValuesFinder#getKeyValues()
	 */
	@Override
	public List<KeyValue> getKeyValues() {
		return super.getKeyValues(Conveyance.class.getCanonicalName());	
	}
}
