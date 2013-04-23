/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * indicates the transaction type e.g. receipt, refund, interest, payment, mpesa etc
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_transaction_type_t")
public class TransactionType extends BaseDetail {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8487159531883364904L;
	@Id
	@Column(name = "transaction_type_id")
	private Long id;
	public static enum TRANSACTION_EFFECT_ON_CONSIDERATION {NONE, INCREASE, DECREASE};
	@Column(name = "effect_on_consideration", nullable=false, columnDefinition="varchar(20)")
	private String effectOnConsideration = TransactionType.TRANSACTION_EFFECT_ON_CONSIDERATION.NONE.toString();
	/* (non-Javadoc)
	 * @see org.martinlaw.bo.BaseDetail#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the effectOnConsideration
	 */
	public String getEffectOnConsideration() {
		return effectOnConsideration;
	}
	/**
	 * @param effectOnConsideration the effectOnConsideration to set
	 */
	public void setEffectOnConsideration(String effectOnConsideration) {
		this.effectOnConsideration = effectOnConsideration;
	}

}
