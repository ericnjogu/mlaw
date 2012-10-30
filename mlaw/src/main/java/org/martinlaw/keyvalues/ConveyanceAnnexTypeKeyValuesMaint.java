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
