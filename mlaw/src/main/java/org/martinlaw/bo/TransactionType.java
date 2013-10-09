/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * indicates the transaction type e.g. receipt, refund, interest, payment, mpesa etc
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_transaction_type_t")
public class TransactionType extends Type {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8487159531883364904L;
	public static enum TRANSACTION_EFFECT_ON_CONSIDERATION {NONE, INCREASE, DECREASE};
	@Column(name = "effect_on_consideration", nullable=false, columnDefinition="varchar(20)")
	private String effectOnConsideration = TransactionType.TRANSACTION_EFFECT_ON_CONSIDERATION.NONE.toString();
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
