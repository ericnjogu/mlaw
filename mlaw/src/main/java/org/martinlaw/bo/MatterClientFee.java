/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * holds information relating to a transactional document which represents a client payment
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MatterClientFee<F extends MatterFee> extends MatterTxDocBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4007181787439191485L;
	@Transient
	private  Long feeId;
	@OneToOne
	@JoinColumn(name = "fee_id", nullable = false, updatable = true)
	private F fee;

	/**
	 * gets the fee id of the {@link MatterFee} that is created along with this doc and that participates in a 1:1 relationship
	 * @return the feeId
	 */
	public Long getFeeId() {
		return feeId;
	}

	/**
	 * @param feeId the feeId to set
	 */
	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}

	/**
	 * gets the fee object, while setting the matter id
	 * @return the fee
	 */
	public F getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(F fee) {
		this.fee = fee;
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.bo.PersistableBusinessObjectBase#prePersist()
	 */
	@Override
	protected void prePersist() {
		// a hack since it was not clear how ojb/jpa keys could have been used to populate this value
		fee.setMatterId(getMatterId());
		super.prePersist();
	}
}
