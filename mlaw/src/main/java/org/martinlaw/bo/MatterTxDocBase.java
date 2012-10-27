package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.kuali.rice.krad.document.TransactionalDocumentBase;
import org.kuali.rice.krad.service.KRADServiceLocator;
/**
 * holds the common fields and methods that a transactional document relating to a matter needs to have
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MatterTxDocBase extends TransactionalDocumentBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9138503410750623334L;
	@Column(name = "matter_id")
	private Long matterId;

	public MatterTxDocBase() {
		super();
	}

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
	 * @param matter the matter to set
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
	 * 
	 * <p>adapted from {@link http://stackoverflow.com/questions/182636/how-to-determine-the-class-of-a-generic-type} </p>
	 * 
	 * @return the matterClass
	 */
	public abstract Class<? extends Matter<? extends MatterAssignee, ? extends MatterWork, ? extends MatterClientFee<?>, ? extends MatterClient>> getMatterClass();

}