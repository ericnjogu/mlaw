/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.kuali.rice.krad.service.KRADServiceLocator;

/**
 * defines the basic fields for an object which will be used to maintain a part of a matter e.g assignment, dates
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MatterMaintenanceHelper extends MartinlawBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5216148157928934781L;
	@Column(name = "matter_id", nullable = false)
	private Long matterId;
	
	/**
	 * @return the matterId
	 */
	public Long getMatterId() {
		return matterId;
	}

	/**
	 * @param matterId the matterId to set
	 */
	public void setMatterId(Long matterId) {
		this.matterId = matterId;
	}

	/**
	 * checks whether the matter id given exists
	 * 
	 * @return true if it exists, false if null or otherwise
	 */
	public boolean isMatterIdValid() {
		if (getMatterId() == null) {
			return false;
		} else {
			return KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(getMatterClass(), getMatterId()) != null;
		}
	}

	/**
	 * useful in determining whether the matter id represents a valid matter of the class given here
	 * 
	 * <p>adapted from {@link http://stackoverflow.com/questions/182636/how-to-determine-the-class-of-a-generic-type} </p>
	 * 
	 * @return the matterClass
	 */
	@SuppressWarnings("rawtypes")
	public abstract Class<? extends Matter> getMatterClass();

	/**
	 * @return the matter
	 */
	@SuppressWarnings("rawtypes")
	public abstract Matter getMatter();
}
