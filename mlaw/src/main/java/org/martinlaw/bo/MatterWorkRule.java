/**
 * 
 */
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


import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.ErrorMessage;
import org.martinlaw.MartinlawConstants;

/**
 * adds some validation checks for {@link MatterWork} documents
 * 
 * @author mugo
 *
 */
public class MatterWorkRule extends MatterTxBusinessRulesBase {

	/**
	 * 
	 */
	public MatterWorkRule() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.rules.DocumentRuleBase#processCustomSaveDocumentBusinessRules(org.kuali.rice.krad.document.Document)
	 */
	@Override
	public boolean processCustomSaveDocumentBusinessRules(Document document) {
		MatterTxDocBase matterWork = (MatterTxDocBase) document;
		if (super.processCustomSaveDocumentBusinessRules(document)) {// checks matter id validity
			// check if the initiator is an assignee for this matter
			String principalId = document.getDocumentHeader().getWorkflowDocument().getInitiatorPrincipalId();
			String initiatorPrincipalName = KimApiServiceLocator.getIdentityService().getPrincipal(principalId).getPrincipalName();
			if (isPrincipalNameInAssigneeList(matterWork, initiatorPrincipalName)) {
				return true;
			} else {
				ErrorMessage errMsg = new ErrorMessage(MartinlawConstants.MessageKeys.ERROR_NOT_ASSIGNED, initiatorPrincipalName, 
								matterWork.getMatterClass().getSimpleName());
				errMsg.setNamespaceCode(MartinlawConstants.MODULE_NAMESPACE_CODE);
				getRulesHelper().addMatterIdError(errMsg);
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * determines if a principal name is in the assignee list
	 * 
	 * @param matterWork - the work document
	 * @param principalName - the principal name to look for
	 * 
	 * @return true if found, false if not or the list is empty
	 */
	@SuppressWarnings("unchecked")
	public boolean isPrincipalNameInAssigneeList(MatterTxDocBase matterWork, String principalName) {
		Matter<? extends MatterAssignee, ? extends MatterWork, ? extends MatterTransactionDoc<?>, 
				? extends MatterClient, ? extends MatterConsideration, ? extends MatterEvent> matter = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
				matterWork.getMatterClass(), matterWork.getMatterId());
		if (matter == null || matter.getAssignees() == null || matter.getAssignees().size() == 0) {
			return false;
		} else {
			for (MatterAssignee assignee: matter.getAssignees()) {
				if (assignee.getPrincipalName().equalsIgnoreCase(principalName)) {
					return true;
				}
			}
		}
		
		return false;
	}
}
