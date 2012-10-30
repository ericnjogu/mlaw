package org.martinlaw.bo;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012 Eric Njogu (kunadawa@gmail.com)
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
import javax.persistence.MappedSuperclass;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
/**
 * fee is a base class for specifying fees paid to the lawyer for services
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class Fee extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7112194910865693180L;
	@Column(scale = 2, precision = 10, nullable = false)
	private BigDecimal amount;
	@Column(name = "date_received", nullable = false)
	private Date date;
	// description can be provided on the fee document explanation
	/*@Column(name = "description", length = 100)
	private String description;*/
	@Column(name = "client_principal_name", length = 100, nullable=false)
	private String clientPrincipalName;

	public Fee() {
		super();
	}

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
	 * e.g. received from Macharia
	 * @return the description
	 *//*
	public String getDescription() {
		return description;
	}

	*//**
	 * @param description the description to set
	 *//*
	public void setDescription(String description) {
		this.description = description;
	}*/
	
	/**
	 * gets the primary key that will be provided by implementing classes
	 * @return
	 */
	public abstract Long getId();

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
}