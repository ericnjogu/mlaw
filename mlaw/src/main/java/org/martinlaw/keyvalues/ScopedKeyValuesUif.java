package org.martinlaw.keyvalues;

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

import java.util.List;

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.MatterExtensionHelper;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.web.MatterTxForm;
/**
 * generates a list of values e.g. event types for a particular matter class (scope) based on form value
 * @author mugo
 *
 */
public class ScopedKeyValuesUif extends UifKeyValuesFinderBase {
	private ScopedKeyValuesHelper scopedKeyValuesHelper;
	private BusinessObjectService businessObjectService;
	private Class<? extends BusinessObject> scopedClass;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3514101768472447004L;

	public ScopedKeyValuesUif() {
		super();
	}
	

	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		return getScopedKeyValuesHelper().getKeyValues(getQualifiedMatterClassName(model), getScopedClass());
	}
	
	/**
	 * extract the matter class from the form
	 * @param model - the form
	 * @return empty string or null if matter class could not be retrieved, the qualified class name if successful
	 */
	protected String getQualifiedMatterClassName(ViewModel model) {
		String qualifiedMatterClassName = "";
		// check if the dataobject is a matter extension helper, retrieve the id, retrieve the object, retrieve the concrete class
		if (model instanceof MaintenanceDocumentForm) {
			MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
			if (form.getDocument() != null && form.getDocument().getNewMaintainableObject() != null 
					&& form.getDocument().getNewMaintainableObject().getDataObject() != null) {
				Object dataObject = form.getDocument().getNewMaintainableObject().getDataObject();
				if (dataObject instanceof MatterExtensionHelper) {
					MatterExtensionHelper helper = (MatterExtensionHelper)dataObject;
					if (helper.getMatterId() != null) {
						qualifiedMatterClassName = retrieveQualifiedMatterClassName(qualifiedMatterClassName, helper.getMatterId());
					}
				// works for court case, matter, conveyance, contract maintenance
				} else if (dataObject instanceof Matter) {
					qualifiedMatterClassName = dataObject.getClass().getCanonicalName();
				}
			}
		} else if (model instanceof MatterTxForm) {
			MatterTxForm txForm = (MatterTxForm)model;
			if (txForm.getDocument() != null && txForm.getDocument() instanceof MatterTxDocBase) {
				MatterTxDocBase txDoc = (MatterTxDocBase) txForm.getDocument();
				if (txDoc.getMatterId() != null) {
					qualifiedMatterClassName = retrieveQualifiedMatterClassName(qualifiedMatterClassName, txDoc.getMatterId());
				}
			}
		}
		
		return qualifiedMatterClassName;
	}


	/**
	 * convenience method to retrieve a matter from the database
	 * @param qualifiedMatterClassName - the default name
	 * @param matterId - the primary key
	 * @return @see {@link #getKeyValues(ViewModel)}
	 */
	protected String retrieveQualifiedMatterClassName(String qualifiedMatterClassName, Long matterId) {
		Matter matter = getBusinessObjectService().findBySinglePrimaryKey(Matter.class, matterId);
		if (matter != null) {
			qualifiedMatterClassName = matter.getConcreteClass();
		}
		
		return qualifiedMatterClassName;
	}
	
	/**
	 * return a class for which a scope has been declared
	 * <p>e.g. {@link EventType}, {@link ConsiderationType} etc </p>
	 * @return
	 */
	public Class<? extends BusinessObject> getScopedClass() {
		return scopedClass;
	}
	/**
	 * @return the scopedKeyValuesHelper
	 */
	public ScopedKeyValuesHelper getScopedKeyValuesHelper() {
		if (scopedKeyValuesHelper == null) {
			scopedKeyValuesHelper = new ScopedKeyValuesHelper();
		}
		return scopedKeyValuesHelper;
	}

	/**
	 * @param scopedKeyValuesHelper the scopedKeyValuesHelper to set
	 */
	public void setScopedKeyValuesHelper(ScopedKeyValuesHelper scopedKeyValuesHelper) {
		this.scopedKeyValuesHelper = scopedKeyValuesHelper;
	}
	
	/**
	 * @return the krad implementation or a mock
	 */
	protected BusinessObjectService getBusinessObjectService() {
		if (businessObjectService == null) {
			businessObjectService = KRADServiceLocator.getBusinessObjectService();
		}
		return businessObjectService;
	}

	/**
	 * @param businessObjectService the businessObjectService to set
	 */
	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}


	/**
	 * @param scopedClass the scopedClass to set
	 */
	public void setScopedClass(Class<? extends BusinessObject> scopedClass) {
		this.scopedClass = scopedClass;
	}

}