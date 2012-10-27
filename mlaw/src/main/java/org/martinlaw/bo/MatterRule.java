package org.martinlaw.bo;

import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.TransactionalDocumentRuleBase;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ErrorMessage;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.martinlaw.Constants;

/**
 * adds some validation checks for {@link MatterTxDocBase} documents
 * 
 * @author mugo
 *
 */
public class MatterRule extends TransactionalDocumentRuleBase {

	public MatterRule() {
		super();
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

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.rules.DocumentRuleBase#processCustomSaveDocumentBusinessRules(org.kuali.rice.krad.document.Document)
	 */
	@Override
	public boolean processCustomSaveDocumentBusinessRules(Document document) {
		MatterTxDocBase matterWork = (MatterTxDocBase) document;
		if (matterWork.isMatterIdValid()) {
				return true;
		} else {
			ErrorMessage errMsg = new ErrorMessage(RiceKeyConstants.ERROR_EXISTENCE, 
					KRADServiceLocatorWeb.getDataDictionaryService().getAttributeLabel(
							matterWork.getMatterClass(), Constants.PropertyNames.MATTER_ID));
			addMatterIdError(errMsg);
			
			return false;
		}
	}

}