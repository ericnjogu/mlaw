/**
 * 
 */
package org.martinlaw.web;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2013 Eric Njogu (kunadawa@gmail.com)
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.krad.bo.DataObjectRelationship;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.element.Link;
import org.kuali.rice.krad.uif.util.LookupInquiryUtils;
import org.kuali.rice.krad.uif.widget.Inquiry;
import org.kuali.rice.krad.util.ExternalizableBusinessObjectUtils;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.form.InquiryForm;
import org.martinlaw.bo.Matter;

/**
 * @author mugo
 * adds functionality to generate edit/copy/new links
 * <p>adapted from {@link LookupableImpl}</p>
 *
 */
public class EnhancedInquirableImpl extends InquirableImpl {
	private LookupableImpl lookupable;
	private transient Logger log = Logger.getLogger(getClass());
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
	
	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.inquiry.InquirableImpl#buildInquirableLink(java.lang.Object, java.lang.String, org.kuali.rice.krad.uif.widget.Inquiry)
	 */
	/**
	 * a copy of the overriden method with some changes to return the class named in {@link Matter#getConcreteClass()} for objects
	 * that are descendants of matter
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public void buildInquirableLink(Object dataObject, String propertyName,
			Inquiry inquiry) {
		Class<?> inquiryObjectClass = null;
		Object inquiryDataObject = dataObject;
		
		// inquiry into data object class if property is title attribute
        Class<?> objectClass = ObjectUtils.materializeClassForProxiedObject(dataObject);
        if (propertyName.equals(getDataObjectMetaDataService().getTitleAttribute(objectClass))) {
            inquiryObjectClass = objectClass;
        } else if (ObjectUtils.isNestedAttribute(propertyName)) {
            String nestedPropertyName = ObjectUtils.getNestedAttributePrefix(propertyName);
            Object nestedPropertyObject = ObjectUtils.getNestedValue(dataObject, nestedPropertyName);

            if (ObjectUtils.isNotNull(nestedPropertyObject)) {
                String nestedPropertyPrimitive = ObjectUtils.getNestedAttributePrimitive(propertyName);
                Class<?> nestedPropertyObjectClass = ObjectUtils.materializeClassForProxiedObject(nestedPropertyObject);

                if (nestedPropertyPrimitive.equals(getDataObjectMetaDataService().getTitleAttribute(
                        nestedPropertyObjectClass))) {
                    inquiryObjectClass = nestedPropertyObjectClass;
                    inquiryDataObject = nestedPropertyObject;
                }
            }
        }

        // if not title, then get primary relationship
        DataObjectRelationship relationship = null;
        if (inquiryObjectClass == null) {
            relationship = getDataObjectMetaDataService().getDataObjectRelationship(dataObject, objectClass,
                    propertyName, "", true, false, true);
            if (relationship != null) {
                inquiryObjectClass = relationship.getRelatedClass();
                inquiryDataObject = ObjectUtils.getNestedValue(dataObject, propertyName);
            }
        }

        // if haven't found inquiry class, then no inquiry can be rendered
        if (inquiryObjectClass == null) {
            inquiry.setRender(false);

            return;
        } else if (inquiryDataObject != null) {
        	if (Matter.class.isAssignableFrom(inquiryDataObject.getClass())) {
        		try {
        			final String concreteClass = ((Matter)inquiryDataObject).getConcreteClass();
        			if (!StringUtils.isEmpty(concreteClass)) {
        				inquiryObjectClass = Class.forName(concreteClass);
        			}
				} catch (ClassNotFoundException e) {
					log.error(e.getMessage());
				}
        	}
        }

        if (DocumentHeader.class.isAssignableFrom(inquiryObjectClass)) {
            String documentNumber = (String) ObjectUtils.getPropertyValue(dataObject, propertyName);
            if (StringUtils.isNotBlank(documentNumber)) {
                inquiry.getInquiryLink().setHref(getConfigurationService().getPropertyValueAsString(
                        KRADConstants.WORKFLOW_URL_KEY)
                        + KRADConstants.DOCHANDLER_DO_URL
                        + documentNumber
                        + KRADConstants.DOCHANDLER_URL_CHUNK);
                inquiry.getInquiryLink().setLinkText(documentNumber);
                inquiry.setRender(true);
            }

            return;
        }

        synchronized (SUPER_CLASS_TRANSLATOR_LIST) {
            for (Class<?> clazz : SUPER_CLASS_TRANSLATOR_LIST) {
                if (clazz.isAssignableFrom(inquiryObjectClass)) {
                    inquiryObjectClass = clazz;
                    break;
                }
            }
        }

        if (!inquiryObjectClass.isInterface() && ExternalizableBusinessObject.class.isAssignableFrom(
                inquiryObjectClass)) {
            inquiryObjectClass = ExternalizableBusinessObjectUtils.determineExternalizableBusinessObjectSubInterface(
                    inquiryObjectClass);
        }

        // listPrimaryKeyFieldNames returns an unmodifiable list. So a copy is necessary.
        List<String> keys = new ArrayList<String>(getDataObjectMetaDataService().listPrimaryKeyFieldNames(
                inquiryObjectClass));

        if (keys == null) {
            keys = Collections.emptyList();
        }

        // build inquiry parameter mappings
        Map<String, String> inquiryParameters = new HashMap<String, String>();
        for (String keyName : keys) {
            String keyConversion = keyName;
            if (relationship != null) {
                keyConversion = relationship.getParentAttributeForChildAttribute(keyName);
            } else if (ObjectUtils.isNestedAttribute(propertyName)) {
                String nestedAttributePrefix = ObjectUtils.getNestedAttributePrefix(propertyName);
                keyConversion = nestedAttributePrefix + "." + keyName;
            }

            inquiryParameters.put(keyConversion, keyName);
        }

        inquiry.buildInquiryLink(dataObject, propertyName, inquiryObjectClass, inquiryParameters);
	}

}
