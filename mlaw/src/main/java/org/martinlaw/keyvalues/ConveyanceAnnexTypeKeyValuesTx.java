/**
 * 
 */
package org.martinlaw.keyvalues;


import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.TransactionForm;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.conveyance.ConveyanceAnnexType;

/**
 * generates a list of {@code ConveyanceAnnexType} key values
 *  
 *  <p>to be displayed as a drop down box on the conveyance work TX doc</p>
 * 
 * @see ConveyanceAnnexType
 * @author mugo
 *
 */
public class ConveyanceAnnexTypeKeyValuesTx extends ConveyanceAnnexTypeKeyValuesBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9206469740259414962L;

	/**
	 * retrieve the conveyance type id from a TX document
	 * 
	 * <p>unit test is in {@link org.martinlaw.test.conveyance.ConveyanceWorkBOTest#testConveyanceAnnexTypeKeyValues()}</p>
	 */
	@Override
	protected Long getConveyanceTypeId(ViewModel model) {
		Long conveyanceTypeId = null;
		TransactionForm form = (TransactionForm) model;
		if (form.getDocument() != null) {
			MatterTxDocBase work = ((MatterTxDocBase)form.getDocument());
			if (work.isMatterIdValid()) {
				Conveyance conv = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
						Conveyance.class, work.getMatterId());
				conveyanceTypeId = conv.getTypeId();
			}
		}
		
		return conveyanceTypeId;
	}
}
