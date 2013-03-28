package org.martinlaw.bo;

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


import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.martinlaw.MatterBusinessRulesHelper;

/**
 * adds some validation checks for {@link MatterTxDocBase} documents
 * 
 * <p>Meant to be added to the 
 * @author mugo
 *
 */
public class MatterMaintenanceHelperBusinessRulesBase extends MaintenanceDocumentRuleBase {

	private MatterBusinessRulesHelper rulesHelper;

	public MatterMaintenanceHelperBusinessRulesBase() {
		super();
		setRulesHelper(new MatterBusinessRulesHelper());
	}

	/**
	 * @return the rulesHelper
	 */
	public MatterBusinessRulesHelper getRulesHelper() {
		return rulesHelper;
	}

	/**
	 * @param rulesHelper the rulesHelper to set
	 */
	public void setRulesHelper(MatterBusinessRulesHelper rulesHelper) {
		this.rulesHelper = rulesHelper;
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase#processCustomRouteDocumentBusinessRules(org.kuali.rice.krad.maintenance.MaintenanceDocument)
	 */
	/**
	 * checks whether the matter id specified in the maintainable object is valid
	 */
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		if (document instanceof MaintenanceDocument) {
			final Maintainable newMaintainableObject = ((MaintenanceDocument)document).getNewMaintainableObject();
			if (newMaintainableObject.getDataObject() instanceof MatterMaintenanceHelper) {
				MatterMaintenanceHelper maintHelper = (MatterMaintenanceHelper) newMaintainableObject.getDataObject();
				if (maintHelper.isMatterIdValid()) {
						return true;
				} else {
					getRulesHelper().addMatterIdError(getRulesHelper().createMatterNotExistingError(maintHelper.getClass()));
					return false;
				}
			} else {
				throw new RuntimeException("Expected 'org.martinlaw.bo.MatterMaintenanceHelper' for newMaintainableObject.getDataObject(). " +
						"Found '" + newMaintainableObject.getDataObject() + "'");
			}
		} else {
			throw new RuntimeException("Expected an instance of MaintenanceDocument. Received '" + document + "'");
		}
	}

}