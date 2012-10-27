/**
 * 
 */
package org.martinlaw.keyvalues;

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
