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


import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * a transactional document which holds a transaction (cash receipt, refund, bond etc)
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MatterTransactionDoc<T extends MatterTransaction> extends MatterTxDocBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4007181787439191485L;
	@Transient
	private  Long transactionId;
	@OneToOne
	@JoinColumn(name = "transaction_id", nullable = false, updatable = true)
	private T transaction;

	/**
	 * gets the transaction id of the {@link MatterTransaction} that is created along with this doc and that participates in a 1:1 relationship
	 * @return the transactionId
	 */
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * gets the transaction object, while setting the matter id
	 * @return the transaction
	 */
	public T getTransaction() {
		return transaction;
	}

	/**
	 * @param transaction the transaction to set
	 */
	public void setTransaction(T transaction) {
		this.transaction = transaction;
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.bo.PersistableBusinessObjectBase#prePersist()
	 */
	@Override
	protected void prePersist() {
		// a hack since it was not clear how ojb/jpa keys could have been used to populate this value
		transaction.setMatterId(getMatterId());
		super.prePersist();
	}
}
