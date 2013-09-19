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


import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.MatterConsideration;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.web.MatterTxForm;

/**
 * generate consideration type - currency - amount (value) and consideration (key) for use e.g. on TX docs
 * 
 * @author mugo
 *
 */
public class MatterConsiderationKeyValues extends UifKeyValuesFinderBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6846503154489611649L;

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.uif.control.UifKeyValuesFinder#getKeyValues(org.kuali.rice.krad.uif.view.ViewModel)
	 */
	/**
	 * unit test in {@link org.martinlaw.test.MatterTransactionDocBOTest#testMatterConsiderationKeyValues()}
	 */
	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		MatterTxForm form = (MatterTxForm) model;
		if (form.getDocument() != null) {
			MatterTxDocBase doc = ((MatterTxDocBase)form.getDocument());
			if (doc.isMatterIdValid()) {
				Matter matter = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
						Matter.class, doc.getMatterId());
				if (matter.getConsiderations() != null && !matter.getConsiderations().isEmpty()) {
					for (Object considObj: matter.getConsiderations()) {
						MatterConsideration consideration = (MatterConsideration)considObj;
						StringBuilder value = new StringBuilder();
						value.append(consideration.getConsiderationType().getName());
						value.append(" - ");
						value.append(consideration.getCurrency());
						value.append(" - ");
						value.append(consideration.getAmount());
						keyValues.add(new ConcreteKeyValue(String.valueOf(consideration.getId()), value.toString()));
					}
				}
			}
		}
		return keyValues;
	}

}
