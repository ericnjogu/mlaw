/**
 * 
 */
package org.martinlaw.bo.contract;

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


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * represents a signatory to a contract
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_contract_signatory_t")
public class ContractSignatory extends ContractPerson {

	/**
	 * initializes principal name
	 */
	public ContractSignatory(String principalName) {
		setPrincipalName(principalName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2139426065091120287L;
	/**
	 * default constructor
	 */
	public ContractSignatory() {
		super();
	}

	@Id
	@Column(name="contract_signatory_id")
	private Long id;
	/* (non-Javadoc)
	 * @see org.martinlaw.bo.MartinlawPerson#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;

	}

	/* (non-Javadoc)
	 * @see org.martinlaw.bo.MartinlawPerson#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

}
