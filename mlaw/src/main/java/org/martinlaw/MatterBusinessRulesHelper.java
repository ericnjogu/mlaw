/**
 * 
 */
package org.martinlaw;

import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.service.DataDictionaryService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.ErrorMessage;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

/**
 * holds document validation rules common to both transactional and maintenance documents
 * @author mugo
 *
 */
public class MatterBusinessRulesHelper {
	private DataDictionaryService dataDictionaryService;

	/**
	 * convenience method to add an error relating to the matter id to the global variables
	 * 
	 * @param matterWork
	 */
	public void addMatterIdError(ErrorMessage errMsg) {
		GlobalVariables.getMessageMap().addToErrorPath(KRADConstants.DOCUMENT_PROPERTY_NAME);
		GlobalVariables.getMessageMap().putError(Constants.PropertyNames.MATTER_ID, errMsg);
		GlobalVariables.getMessageMap().removeFromErrorPath(KRADConstants.DOCUMENT_PROPERTY_NAME);
	}
	
	/**
	 * creates an error to be displayed to the user that the matter id provided did not match an existing matter of the class type
	 * @param klass - the class type - used for looking up a user friendly name for the matter id label from the DD
	 * @return the error message
	 */
	public ErrorMessage createMatterNotExistingError(Class<?> klass) {
		ErrorMessage errMsg = new ErrorMessage(RiceKeyConstants.ERROR_EXISTENCE, 
				getDataDictionaryService().getAttributeLabel(
						klass, Constants.PropertyNames.MATTER_ID));
		errMsg.setNamespaceCode(Constants.MODULE_NAMESPACE_CODE);
		return errMsg;
	}

	/**
	 * gets the local ref to {@link DataDictionaryService} in a mock friendly way
	 * @return the local reference if not null or {@link KRADServiceLocatorWeb#getDataDictionaryService()}
	 */
	private DataDictionaryService getDataDictionaryService() {
		if (dataDictionaryService == null) {
			dataDictionaryService = KRADServiceLocatorWeb.getDataDictionaryService();
		}
		return dataDictionaryService;
	}

	/**
	 * @param dataDictionaryService the dataDictionaryService to set
	 */
	public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

}
