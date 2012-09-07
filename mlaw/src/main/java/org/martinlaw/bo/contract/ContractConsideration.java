/**
 * 
 */
package org.martinlaw.bo.contract;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * holds a contracts consideration details
 * 
 * <p>Having the info here enables changes without affecting the contract</p>
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_contract_consideration_t")
public class ContractConsideration extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9114507684352254606L;
	@Id
    @GeneratedValue(generator="martinlaw_contract_consideration_id_s")
	@GenericGenerator(name="martinlaw_contract_consideration_id_s",strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",parameters={
			@Parameter(name="sequence_name",value="martinlaw_contract_consideration_id_s"),
			@Parameter(name="value_column",value="id")
	})
	@Column(name="contract_consideration_id")
	private Long id;
	@Column(scale = 2, precision = 20, nullable = false)
	private BigDecimal amount;
	@Column(name="currency", nullable=false)
	private String currency;
	/**
	 * default constructor
	 */
	public ContractConsideration() {
		super();
	}

	@Column(name = "description", length = 255)
	private String description;

	/**
	 * initializes fields
	 * @param amount - the amount
	 * @param currency - the currency
	 * @param description - a comment
	 */
	public ContractConsideration(BigDecimal amount, String currency,
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
}
