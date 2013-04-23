package org.martinlaw.bo;

import java.sql.Timestamp;

import org.kuali.rice.krad.maintenance.MaintainableImpl;
/**
 * adds the modified time stamp on save
 * @author mugo
 *
 */
public class MatterMaintenanceHelperMaintainable extends MaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6410278054744295126L;

	public MatterMaintenanceHelperMaintainable() {
		super();
	}

	/**
	 * overrides the parent method to set the modified timestamp
	 */
	@Override
	public void prepareForSave() {
		((MatterExtensionHelper)getDataObject()).setDateModified(new Timestamp(System.currentTimeMillis()));
		super.prepareForSave();
	}

}