/**
 * 
 */
package org.martinlaw.bo.opinion;

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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.martinlaw.bo.Matter;
import org.martinlaw.bo.MatterTransactionDoc;

/**
 * transactional document that holds information on a contract transaction
 *  
 * @author mugo
 *
 */
@Entity(name="opinion_transaction_doc")
@Table(name="martinlaw_opinion_transaction_doc_t")
public class TransactionDoc extends MatterTransactionDoc {
	@OneToOne
	@JoinColumn(name = "consideration_id", nullable = false, insertable=false, updatable=false)
	private Consideration consideration;
	@OneToOne
	@JoinColumn(name = "matter_id", nullable = false, insertable=false, updatable=false)
	private Opinion matter;
	/**
	 * 
	 */
	private static final long serialVersionUID = 803850528676339102L;

	/* (non-Javadoc)
	 * @see org.martinlaw.bo.MatterTxDocBase#getMatterClass()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends Matter> getMatterClass() {
		return Opinion.class;
	}
	
	@Override
	public Opinion getMatter() {
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
	public void setMatter(Opinion matter) {
		this.matter = matter;
	}
}
