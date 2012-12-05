package org.martinlaw.bo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
/**
 * holds information on the agreed consideration that the client is willing to pay for this matter
 * 
 * <p>For a contract, there will be two considerations - one for the value of the contract and the other for how much it costs
 * to draw up the contract</p>
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MatterConsideration extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4589635622324081369L;
	
	@Column(scale = 2, precision = 20, nullable = false)
	protected BigDecimal amount;
	@Column(name = "currency", nullable = false)
	protected String currency;
	@Column(name = "description", length = 250)
	protected String description;

	public MatterConsideration() {
		super();
	}
	
	/**
	 * initializes fields
	 * @param amount - the amount
	 * @param currency - the currency
	 * @param description - a comment
	 */
	public MatterConsideration(BigDecimal amount, String currency,
			String description) {
		this.amount = amount;
		this.currency = currency;
		this.description = description;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * will be shown as a drop down to enable contracts to be looked up using a drop down as well
	 * 
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * can be used to point to an attached file with more details e.g. breakdowns, phases etc
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}