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