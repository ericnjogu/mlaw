/**
 * 
 */
package org.martinlaw.keyvalues;


import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.conveyance.ConveyanceAnnexType;

/**
 * generates a list of {@code ConveyanceAnnexType} key values
 *  
 *  <p>to be displayed as a drop down box on the conveyance annexes collection section add line maintenance doc</p>
 * 
 * @see ConveyanceAnnexType
 * @author mugo
 *
 */
public class ConveyanceAnnexTypeKeyValuesMaint extends ConveyanceAnnexTypeKeyValuesBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9206469740259414962L;

	@Override
	/**
	 * retrieve conveyance id from maitenance form
	 * 
	 * <p>unit test in {@link org.martinlaw.test.conveyance.ConveyanceBOTest#testConveyanceAnnexTypeKeyValues()}
	 */
	protected Long getConveyanceTypeId(ViewModel model) {
		Long conveyanceTypeId = null;
		MaintenanceForm form = (MaintenanceForm) model;
		if (form.getDocument() != null) {
			conveyanceTypeId = ((Conveyance)form.getDocument().getNewMaintainableObject().getDataObject()).getTypeId();
		}
		
		return conveyanceTypeId;
	}
}
