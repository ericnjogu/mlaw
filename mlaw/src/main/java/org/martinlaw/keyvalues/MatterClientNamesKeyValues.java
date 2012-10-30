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


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.TransactionForm;
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.MatterAssignee;
import org.martinlaw.bo.MatterClient;
import org.martinlaw.bo.MatterClientFee;
import org.martinlaw.bo.MatterFee;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.bo.MatterWork;

/**
 * generate client names (value) and principal names (key) for use e.g. on fee TX docs
 * 
 * @author mugo
 *
 */
public class MatterClientNamesKeyValues extends UifKeyValuesFinderBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6846503154489611649L;

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.uif.control.UifKeyValuesFinder#getKeyValues(org.kuali.rice.krad.uif.view.ViewModel)
	 */
	/**
	 * unit test in {@link org.martinlaw.test.contract.ContractFeeBOTest#testClientNamesKeyValues()}
	 */
	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		TransactionForm form = (TransactionForm) model;
		if (form.getDocument() != null) {
			MatterTxDocBase doc = ((MatterTxDocBase)form.getDocument());
			if (doc.isMatterIdValid()) {
				Matter<? extends MatterAssignee, ? extends MatterWork, ? extends MatterClientFee<? extends MatterFee>, ? extends MatterClient> matter = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
						doc.getMatterClass(), doc.getMatterId());
				if (matter.getClients() != null && !matter.getClients().isEmpty()) {
					for (MatterClient client: matter.getClients()) {
						String value = client.getPerson().getName();
						if (StringUtils.isEmpty(value)) {
							value = client.getPrincipalName();
						}
						keyValues.add(new ConcreteKeyValue(client.getPrincipalName(), value));
					}
				}
			}
		}
		return keyValues;
	}

}
