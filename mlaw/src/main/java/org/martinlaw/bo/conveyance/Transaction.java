/**
 * 
 */
package org.martinlaw.bo.conveyance;

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
import org.martinlaw.bo.MatterTransaction;


/**
 * represents a transaction involving a {@link Conveyance}
 * @author mugo
 */
@Entity(name="conveyance_transaction")
@Table(name="martinlaw_conveyance_transaction_t")
public class Transaction extends MatterTransaction {
	private Conveyance conveyance;
	private Consideration consideration;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5139498341007335334L;

	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends Matter> getMatterClass() {
		return Conveyance.class;
	}

	@Override
	public Conveyance getMatter() {
		return conveyance;
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
}
