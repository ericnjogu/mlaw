/**
 * 
 */
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
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * a transactional document which holds a transaction (cash receipt, refund, bond etc)
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_matter_transaction_doc_t")
public class MatterTransactionDoc extends MatterTxDocBase {
	@OneToOne
	@JoinColumn(name = "consideration_id", nullable = false, insertable=false, updatable=false)
	private MatterConsideration consideration;
	@OneToOne
	@JoinColumn(name = "matter_id", nullable = false, insertable=false, updatable=false)
	private Matter matter;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4007181787439191485L;
	
	@Column(scale = 2, precision = 10, nullable = false)
	private BigDecimal amount;
	@Column(name = "transaction_date", nullable = false)
	private Date date;
	@Column(name = "client_principal_name", length = 100, nullable = false)
	private String clientPrincipalName;
	@Column(name = "consideration_id", nullable = false)
	private Long considerationId;
	@Transient
	private long transactionTypeId;
	@OneToOne
	@JoinColumn(name = "type_id", nullable = false, updatable = false)
	private TransactionType transactionType;

	/**
	 * e.g. 20,000.50
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
	 * e.g. 12-Jun-2011o[
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * gets the principal name of the client who made this payment
	 * 
	 * @return the clientPrincipalName
	 */
	public String getClientPrincipalName() {
		return clientPrincipalName;
	}

	/**
	 * @param clientPrincipalName the clientPrincipalName to set
	 */
	public void setClientPrincipalName(String clientPrincipalName) {
		this.clientPrincipalName = clientPrincipalName;
	}

	/**
	 * @return the considerationId
	 */
	public Long getConsiderationId() {
		return considerationId;
	}

	/**
	 * @param considerationId the considerationId to set
	 */
	public void setConsiderationId(Long considerationId) {
		this.considerationId = considerationId;
	}

	/**
	 * @return the transactionTypeId
	 */
	public long getTransactionTypeId() {
		return transactionTypeId;
	}

	/**
	 * @param transactionTypeId the transactionTypeId to set
	 */
	public void setTransactionTypeId(long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	/**
	 * @return the transactionType
	 */
	public TransactionType getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	/**
	 * returns the matter that has been populated by the ojb configuration
	 * @return the matter
	 */
	public Matter getMatter() {
		return matter;
	}
	
	/**
	 * returns the consideration that has been populated by the ojb configuration
	 * @return the matter
	 */
	public MatterConsideration getConsideration() {
		return consideration;
	}

	/**
	 * @param consideration the consideration to set
	 */
	public void setConsideration(MatterConsideration consideration) {
		this.consideration = consideration;
	}

	/**
	 * @param matter the matter to set
	 */
	public void setMatter(Matter matter) {
		this.matter = matter;
	}
	
}
