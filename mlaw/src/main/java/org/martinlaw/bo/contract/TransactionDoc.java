/**
 * 
 */
package org.martinlaw.bo.contract;

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


import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.Matter;
import org.martinlaw.bo.MatterTransactionDoc;

/**
 * transactional document that holds information of contract {@link Transaction}
 *  
 * @author mugo
 *
 */
@Entity(name="contract_transaction_doc")
@Table(name="martinlaw_contract_transaction_doc_t")
public class TransactionDoc extends MatterTransactionDoc {
	private Consideration consideration;
	private Contract matter;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8397901980615462593L;

	/* (non-Javadoc)
	 * @see org.martinlaw.bo.MatterTxDocBase#getMatterClass()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends Matter> getMatterClass() {
		return Contract.class;
	}

	@Override
	public Contract getMatter() {
		return matter;
	}

	@Override
	public Consideration getConsideration() {
		return consideration;
	}

	/**
	 * @param consideration the consideration to set
	 */
	public void setConsideration(Consideration consideration) {
		this.consideration = consideration;
	}

	/**
	 * @param matter the matter to set
	 */
	public void setMatter(Contract matter) {
		this.matter = matter;
	}

}
