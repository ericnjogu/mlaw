/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.kuali.rice.krad.service.KRADServiceLocator;

/**
 * defines the basic fields for an object which will be used to extend a matter by creating associated business objects and
 * separate maintenance or transaction documents e.g assignment, events, transaction
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MatterExtensionHelper extends MartinlawBusinessObjectBase {
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
			return KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Matter.class, getMatterId()) != null;
		}
	}

	/**
	 * returns the matter that has been populated by the ojb configuration
	 * @return the matter
	 *//*
	@SuppressWarnings("rawtypes")
	public abstract Matter getMatter();*/
}
