/**
 * 
 */
package org.martinlaw.keyvalues;


import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.TransactionForm;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.conveyance.ConveyanceAnnexType;

/**
 * generates a list of {@code ConveyanceAnnexType} key values
 *  
 *  <p>to be displayed as a drop down box on the conveyance work tx doc</p>
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
	 * retrieve the conveyance type id from a tx document
	 * 
	 * <p>unit test is in {@link org.martinlaw.test.conveyance.ConveyanceWorkBOTest#testConveyanceAnnexTypeKeyValues()}</p>
	 */
	@Override
	protected Long getConveyanceTypeId(ViewModel model) {
		Long conveyanceTypeId = null;
		TransactionForm form = (TransactionForm) model;
		if (form.getDocument() != null) {
			MatterWork work = ((MatterWork)form.getDocument());
			if (work.isMatterIdValid()) {
				Conveyance conv = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
						Conveyance.class, work.getMatterId());
				conveyanceTypeId = conv.getTypeId();
			}
		}
		
		return conveyanceTypeId;
	}
}
