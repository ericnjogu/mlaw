/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.kuali.rice.krad.document.TransactionalDocumentBase;
import org.kuali.rice.krad.service.KRADServiceLocator;

/**
 * provides a way for an assignee to submit work for a matter
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MatterWork extends TransactionalDocumentBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3637053012196079706L;
	@Column(name = "matter_id")
	private Long matterId;
	@Transient
	private Class<? extends Matter<? extends MatterAssignee, ? extends MatterWork>> matterClass;
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
	 * @return the matter
	 *//*
	public M getMatter() {
		return matter;
	}
	*//**
	 * @param matter the matter to set
	 *//*
	public void setMatter(M matter) {
		this.matter = matter;
	}*/
	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.document.DocumentBase#logErrors()
	 */
	@Override
	public void logErrors() {
		super.logErrors();
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
	 * @return the matterClass
	 */
	public Class<? extends Matter<? extends MatterAssignee, ? extends MatterWork>> getMatterClass() {
		return matterClass;
	}
	/**
	 * @param matterClass the matterClass to set
	 */
	public void setMatterClass(Class<? extends Matter<? extends MatterAssignee, ? extends MatterWork>> matterClass) {
		this.matterClass = matterClass;
	}
}
