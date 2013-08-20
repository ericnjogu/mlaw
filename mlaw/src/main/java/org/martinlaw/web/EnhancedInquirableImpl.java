/**
 * 
 */
package org.martinlaw.web;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.element.Link;
import org.kuali.rice.krad.uif.util.LookupInquiryUtils;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.form.InquiryForm;

/**
 * @author mugo
 * adds functionality to generated edit/copy/new links
 * <p>adapted from {@link LookupableImpl}</p>
 *
 */
public class EnhancedInquirableImpl extends InquirableImpl {
	private LookupableImpl lookupable;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7448642552698860147L;
	/**
     * @see org.kuali.rice.krad.lookup.Lookupable#getMaintenanceActionLink
     */
    public void getMaintenanceActionLink(Link actionLink, Object model, String maintenanceMethodToCall) {
    	InquiryForm inquiryForm = (InquiryForm) model;
        Object dataObject = inquiryForm.getDataObject();
    	

        List<String> pkNames = getDataObjectMetaDataService().listPrimaryKeyFieldNames(getDataObjectClass());

        // build maintenance link href
        String href = getActionUrlHref(inquiryForm, dataObject, maintenanceMethodToCall, pkNames);
        if (StringUtils.isBlank(href)) {
            actionLink.setRender(false);
            return;
        }
        // TODO: need to handle returning anchor
        actionLink.setHref(href);

        // build action title
        String prependTitleText = actionLink.getLinkText() + " " +
                getDataDictionaryService().getDataDictionary().getDataObjectEntry(getDataObjectClass().getName())
                        .getObjectLabel() + " " +
                getConfigurationService().getPropertyValueAsString(
                        KRADConstants.Lookup.TITLE_ACTION_URL_PREPENDTEXT_PROPERTY);

        Map<String, String> primaryKeyValues = KRADUtils.getPropertyKeyValuesFromDataObject(pkNames, dataObject);
        String title = LookupInquiryUtils.getLinkTitleText(prependTitleText, getDataObjectClass(), primaryKeyValues);
        actionLink.setTitle(title);
    }
    
    /**
     * Generates a URL to perform a maintenance action on the given result data object
     *
     * <p>
     * Will build a URL containing keys of the data object to invoke the given maintenance action method
     * within the maintenance controller
     * </p>
     *
     * @param dataObject - data object instance for the line to build the maintenance action link for
     * @param methodToCall - method name on the maintenance controller that should be invoked
     * @param pkNames - list of primary key field names for the data object whose key/value pairs will be added to
     * the maintenance link
     * @return String URL link for the maintenance action
     */
    protected String getActionUrlHref(InquiryForm inquiryForm, Object dataObject, String methodToCall,
            List<String> pkNames) {

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);

        Map<String, String> primaryKeyValues = KRADUtils.getPropertyKeyValuesFromDataObject(pkNames, dataObject);
        for (String primaryKey : primaryKeyValues.keySet()) {
            String primaryKeyValue = primaryKeyValues.get(primaryKey);

            props.put(primaryKey, primaryKeyValue);
        }

        if (StringUtils.isNotBlank(inquiryForm.getReturnLocation())) {
            props.put(KRADConstants.RETURN_LOCATION_PARAMETER, inquiryForm.getReturnLocation());
        }

        props.put(UifParameters.DATA_OBJECT_CLASS_NAME, inquiryForm.getDataObjectClassName());
        props.put(UifParameters.VIEW_TYPE_NAME, UifConstants.ViewType.MAINTENANCE.name());

        return UrlFactory.parameterizeUrl(KRADConstants.Maintenance.REQUEST_MAPPING_MAINTENANCE, props);
    }
    
    /**
     * @see org.kuali.rice.krad.lookup.LookupableImpl#allowsMaintenanceNewOrCopyAction
     */
    public boolean allowsMaintenanceNewOrCopyAction() {
        return getLookupable().allowsMaintenanceNewOrCopyAction();
    }

    /**
     * @see org.kuali.rice.krad.lookup.LookupableImpl#allowsMaintenanceEditAction
     */
    public boolean allowsMaintenanceEditAction(Object dataObject) {
        return getLookupable().allowsMaintenanceEditAction(dataObject);
    }

    /**
     * @ see org.kuali.rice.krad.lookup.LookupableImpl#allowsMaintenanceDeleteAction
     */
    public boolean allowsMaintenanceDeleteAction(Object dataObject) {
        return getLookupable().allowsMaintenanceDeleteAction(dataObject);
    }

	/**
	 * @return the lookupable
	 */
	public LookupableImpl getLookupable() {
		if (lookupable == null) {
			lookupable = new LookupableImpl();
			lookupable.setDataObjectClass(getDataObjectClass());
		}
		return lookupable;
	}

	/**
	 * @param lookupable the lookupable to set
	 */
	public void setLookupable(LookupableImpl lookupable) {
		this.lookupable = lookupable;
	}

}
