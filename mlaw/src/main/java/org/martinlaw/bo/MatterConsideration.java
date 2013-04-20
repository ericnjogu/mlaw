package org.martinlaw.bo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
/**
 * holds the common information needed by a matter consideration (value/purchase price/legal fee/security deposit/bond)
 * 
 * <p>For a contract, there will be at least two considerations - one for the value of the contract and the other for the legal fee</p>
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MatterConsideration extends MatterMaintenanceHelper {

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
	@Id
	@Column(name = "consideration_id")
	private Long id;
	@Transient //placed here for ojb's sake, coz jpa uses the object field below
	private Long considerationTypeId;
	@OneToOne
	@JoinColumn(name = "consideration_type_id", nullable = false)
	private ConsiderationType considerationType; 

	public MatterConsideration() {
		super();
	}
	
	/**
	 * initializes fields
	 * @param amount - the amount
	 * @param currency - the currency
	 * @param description - a comment
	 */
	public MatterConsideration(BigDecimal amount, String currency, String description) {
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
	
	/**
	 * @return the id
	 */
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
	 * @return the considerationTypeId
	 */
	public Long getConsiderationTypeId() {
		return considerationTypeId;
	}

	/**
	 * @param considerationTypeId the considerationTypeId to set
	 */
	public void setConsiderationTypeId(Long considerationTypeId) {
		this.considerationTypeId = considerationTypeId;
	}

	/**
	 * @return the considerationType
	 */
	public ConsiderationType getConsiderationType() {
		return considerationType;
	}

	/**
	 * @param considerationType the considerationType to set
	 */
	public void setConsiderationType(ConsiderationType considerationType) {
		this.considerationType = considerationType;
	}
}