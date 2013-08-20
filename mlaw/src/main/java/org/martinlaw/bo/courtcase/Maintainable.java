/**
 * 
 */
package org.martinlaw.bo.courtcase;

import org.kuali.rice.krad.bo.DocumentHeader;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.MatterMaintainable;

/**
 * customizes the save operation to save new principals
 * @author mugo
 *
 */
public class Maintainable extends MatterMaintainable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1660294013486130605L;

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.maintenance.MaintainableImpl#doRouteStatusChange(org.kuali.rice.krad.bo.DocumentHeader)
	 */
	@Override
	public void doRouteStatusChange(DocumentHeader documentHeader) {
		super.doRouteStatusChange(documentHeader);
		if (documentHeader.getWorkflowDocument().isProcessed()) {
			createPrincipals(((CourtCase) getDataObject()).getWitnesses(), MartinlawConstants.AffiliationCodes.WITNESS);
		}
	}
}
