/**
 * 
 */
package org.martinlaw.keyvalues;

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
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.conveyance.ConveyanceAnnexType;

/**
 * generates a list of {@code ConveyanceAnnexType} key values for the set 
 *  
 *  to be displayed as a drop down box on the conveyance annexes collection section add line 
 * 
 * @see ConveyanceAnnexType
 * @author mugo
 *
 */
public class ConveyanceAnnexTypeKeyValues extends UifKeyValuesFinderBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9206469740259414962L;

	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		// to be added by default during rendering
		// keyValues.add(new ConcreteKeyValue("", ""));
		Long conveyanceTypeId = null;
		MaintenanceForm form = (MaintenanceForm) model;
		if (form.getDocument() != null) {
			conveyanceTypeId = ((Conveyance)form.getDocument().getNewMaintainableObject().getDataObject()).getTypeId();
		}
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
