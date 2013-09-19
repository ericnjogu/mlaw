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
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
/**
 * retrieves key values that can be used in a drop down list
 * @author mugo
 *
 */
public abstract class ScopedKeyValuesBase extends KeyValuesBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3514101768472447004L;
	private ScopedKeyValuesHelper scopedKeyValuesHelper;

	public ScopedKeyValuesBase() {
		super();
	}
	public ScopedKeyValuesHelper getScopedKeyValuesHelper() {
		if (scopedKeyValuesHelper == null) {
			scopedKeyValuesHelper = new ScopedKeyValuesHelper();
		}
		return scopedKeyValuesHelper;
	}

	/**
	 * @param scopedKeyValuesHelper the scopedKeyValuesHelper to set
	 */
	public void setScopedKeyValuesHelper(ScopedKeyValuesHelper scopedKeyValuesHelper) {
		this.scopedKeyValuesHelper = scopedKeyValuesHelper;
	}
	
	/**
	 * @see org.martinlaw.keyvalues.ScopedKeyValuesHelper#getKeyValues(String, Class<? extends BusinessObject>)
	 */
	public List<KeyValue> getKeyValues(String qualifiedMatterClassName, Class<? extends BusinessObject> scopedClass) {
		return getScopedKeyValuesHelper().getKeyValues(qualifiedMatterClassName, scopedClass);
	}

}