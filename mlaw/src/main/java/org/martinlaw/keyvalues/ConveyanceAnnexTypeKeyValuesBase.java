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


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.martinlaw.bo.conveyance.ConveyanceAnnexType;
/**
 * holds common logic for maintenance and TX docs
 * 
 * @author mugo
 *
 */
public abstract class ConveyanceAnnexTypeKeyValuesBase extends
		UifKeyValuesFinderBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 889118964987850907L;

	public ConveyanceAnnexTypeKeyValuesBase() {
		super();
	}
	
	/**
	 * get the conveyance type id to use in fetching annex types
	 * 
	 * @param model - provides the TX or maintenance document which is used to lookup the type id
	 * @return
	 */
	protected abstract Long getConveyanceTypeId(ViewModel model);

	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		// to be added by default during rendering
		// keyValues.add(new ConcreteKeyValue("", ""));
		Long conveyanceTypeId = getConveyanceTypeId(model);
		if (conveyanceTypeId != null) {
			// fetch all conveyance annex types that belonging to the supplied conveyance type id
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("conveyanceTypeId", conveyanceTypeId);
			Collection<ConveyanceAnnexType> results = KRADServiceLocator.getBusinessObjectService().findMatching(
					ConveyanceAnnexType.class, params);
			for (ConveyanceAnnexType annexType: results) {
				keyValues.add(new ConcreteKeyValue(annexType.getId().toString(), annexType.getName()));
			}
			
		}
		return keyValues;
	}

}