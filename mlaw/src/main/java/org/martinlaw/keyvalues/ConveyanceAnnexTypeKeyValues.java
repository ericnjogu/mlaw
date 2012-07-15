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
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.bo.ConveyanceAnnexType;

/**
 * generates a list of {@code ConveyanceAnnexType} key values for the set 
 *  
 *  to be displayed as a drop down box on the conveyance annexes collection section add line 
 * 
 * @see ConveyanceAnnexType
 * @author mugo
 *
 */
public class ConveyanceAnnexTypeKeyValues extends KeyValuesBase {

	
	private Long conveyanceTypeId;
	/**
	 * 
	 */
	private static final long serialVersionUID = 9206469740259414962L;

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.keyvalues.KeyValuesFinder#getKeyValues()
	 */
	@Override
	public List<KeyValue> getKeyValues() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		keyValues.add(new ConcreteKeyValue("", ""));
		if (getConveyanceTypeId() != null) {
			// fetch all conveyance annex types that belonging to the supplied conveyance type id
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("conveyanceTypeId", getConveyanceTypeId());
			Collection<ConveyanceAnnexType> results = KRADServiceLocator.getBusinessObjectService().findMatching(
					ConveyanceAnnexType.class, params);
			for (ConveyanceAnnexType annexType: results) {
				keyValues.add(new ConcreteKeyValue(annexType.getId().toString(), annexType.getName()));
			}
			
		}
		return keyValues;
	}

	/**
	 * gets the conveyance type id to fetch conveyance annex types for
	 * 
	 * @return the id
	 */
	public Long getConveyanceTypeId() {
		return conveyanceTypeId;
	}

	/**
	 * @param conveyanceTypeId the id to set
	 */
	public void setConveyanceTypeId(Long conveyanceTypeId) {
		this.conveyanceTypeId = conveyanceTypeId;
	}
}
