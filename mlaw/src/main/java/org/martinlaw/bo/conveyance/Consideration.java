/**
 * 
 */
package org.martinlaw.bo.conveyance;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012,2013 Eric Njogu (kunadawa@gmail.com)
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
import org.martinlaw.bo.MatterConsideration;


/**
 * holds a {@link Conveyance} consideration details
 * 
 * <p>Having the info here enables changes without affecting the contract</p>
 * 
 * @author mugo
 *
 */
@Entity(name="conveyance_consideration")
@Table(name="martinlaw_conveyance_consideration_t")
public class Consideration extends MatterConsideration<TransactionDoc> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9114507684352254606L;

	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends Matter> getMatterClass() {
		return Conveyance.class;
	}

	@OneToOne
	@JoinColumn(name = "matter_id", nullable = false, insertable=false, updatable=false)
	private Conveyance matter;
	
	/**
	 * implements the parent class method to return the matter fetched via ojb
	 */
	public Conveyance getMatter() {
		return matter;
	}

	/**
	 * @param matter the matter to set
	 */
	public void setMatter(Conveyance matter) {
		this.matter = matter;
	}
}
