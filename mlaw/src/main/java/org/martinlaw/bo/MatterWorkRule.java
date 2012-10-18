/**
 * 
 */
package org.martinlaw.bo;

import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.TransactionalDocumentRuleBase;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ErrorMessage;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.martinlaw.Constants;

/**
 * adds some validation checks for {@link MatterWork} documents
 * 
 * @author mugo
 *
 */
public class MatterWorkRule extends TransactionalDocumentRuleBase {

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.rules.DocumentRuleBase#processCustomSaveDocumentBusinessRules(org.kuali.rice.krad.document.Document)
	 */
	@Override
	public boolean processCustomSaveDocumentBusinessRules(Document document) {
		MatterWork matterWork = (MatterWork) document;
		if (matterWork.isMatterIdValid()) {
			// check if the initiator is an assignee for this matter
			String principalId = document.getDocumentHeader().getWorkflowDocument().getInitiatorPrincipalId();
			String initiatorPrincipalName = KimApiServiceLocator.getIdentityService().getPrincipal(principalId).getPrincipalName();
			if (isPrincipalNameInAssigneeList(matterWork, initiatorPrincipalName)) {
				return true;
			} else {
				ErrorMessage errMsg = new ErrorMessage(Constants.MessageKeys.ERROR_NOT_INITIATOR, initiatorPrincipalName, 
								matterWork.getMatterClass().getSimpleName());
				addMatterIdError(errMsg);
				return false;
			}
		} else {
			ErrorMessage errMsg = new ErrorMessage(RiceKeyConstants.ERROR_EXISTENCE, 
					KRADServiceLocatorWeb.getDataDictionaryService().getAttributeLabel(
							matterWork.getMatterClass(), Constants.PropertyNames.MATTER_ID));
			addMatterIdError(errMsg);
			
			return false;
		}
	}

	/**
	 * convenience method to add an error relating to the matter id to the global variables
	 * 
	 * @param matterWork
	 */
	protected void addMatterIdError(ErrorMessage errMsg) {
		GlobalVariables.getMessageMap().addToErrorPath(KRADConstants.DOCUMENT_PROPERTY_NAME);
		GlobalVariables.getMessageMap().putError(Constants.PropertyNames.MATTER_ID, errMsg);
		GlobalVariables.getMessageMap().removeFromErrorPath(KRADConstants.DOCUMENT_PROPERTY_NAME);
	}
	
	/**
	 * determines if a principal name is in the assignee list
	 * 
	 * @param matterWork - the work document
	 * @param principalName - the principal name to look for
	 * 
	 * @return true if found, false if not or the list is empty
	 */
	public boolean isPrincipalNameInAssigneeList(MatterWork matterWork, String principalName) {
		Matter<? extends MatterAssignee, ? extends MatterWork> matter = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
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
