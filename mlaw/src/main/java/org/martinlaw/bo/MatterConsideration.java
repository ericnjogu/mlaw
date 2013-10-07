package org.martinlaw.bo;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012, 2013 Eric Njogu (kunadawa@gmail.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
/**
 * holds the common information needed by a matter consideration (value/purchase price/legal fee/security deposit/bond)
 * 
 * <p>For a contract, there will be at least two considerations - one for the value of the contract and the other for the legal fee</p>
 * 
 * @author mugo
 * @param <MatterTransactionDoc>
 *
 */
@Entity
@Table(name="martinlaw_matter_consideration_t")
public class MatterConsideration extends MatterExtensionHelper {

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
	@Column(name = "type_id")
	private Long considerationTypeId;
	@OneToOne
	@JoinColumn(name = "type_id", nullable = false, insertable=false, updatable=false)
	private ConsiderationType considerationType; 
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="considerationId")
	private List<MatterTransactionDoc> transactions;
	@Transient
	private transient String html = "";
	@OneToOne
	@JoinColumn(name = "matter_id", nullable = false, insertable=false, updatable=false)
	private Matter matter;

	public MatterConsideration() {
		super();
		setTransactions(new ArrayList<MatterTransactionDoc>());
	}
	
	/**
	 * initializes fields
	 * @param amount - the amount
	 * @param currency - the currency
	 * @param description - a comment
	 */
	public MatterConsideration(BigDecimal amount, String currency, String description) {
		this();
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

	/**
	 * used in the matter inquiry to display as a subcollection of each consideration
	 * created via a specific matter transaction document
	 * @return the transactions
	 */
	public List<MatterTransactionDoc> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<MatterTransactionDoc> transactions) {
		this.transactions = transactions;
	}
	
	/**
	 * generate a html representation, showing totals of all transactions and the consideration itself
	 * @return
	 */
	public String toHtml() {
		if (StringUtils.isEmpty(html)) {
			BigDecimal total = new BigDecimal(0);
			if (getAmount() != null) {
				total = total.add(getAmount());
			}
			if (getTransactions() != null && getTransactions().size() > 0) {
				for (MatterTransactionDoc tx: getTransactions()) {
					if (tx.getAmount() != null) {
						total = total.add(tx.getAmount());
					}
				}
			}
			if (total.doubleValue() != 0) {
				StringBuilder sb = new StringBuilder();
				sb.append("<b>");
				if (getConsiderationType() == null) {
					sb.append("&lt;unknown&gt;");
				} else {
					sb.append(getConsiderationType().getName());
				}
				sb.append("</b>:&nbsp;");
				sb.append(NumberFormat.getCurrencyInstance().format(total.doubleValue()));
				html = sb.toString();
			}
		}
		
		return html;
	}
	
	/**
	 * implements the parent class method to return the matter fetched via ojb
	 */
	public Matter getMatter() {
		return matter;
	}

	/**
	 * @param matter the matter to set
	 */
	public void setMatter(Matter matter) {
		this.matter = matter;
	}
}