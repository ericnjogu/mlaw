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

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.martinlaw.bo.Matter;
import org.martinlaw.bo.MatterEvent;


/**
 * represents an event e.g.a meeting date etc set for a contract
 * 
 * @author mugo
 */
@Entity(name="contract_event")
@Table(name="martinlaw_contract_event_t")
public class Event extends MatterEvent {
	@Transient
	private Contract matter;
	/**
	 * default constructor
	 */
	public Event() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3686195124782039856L;
	/**
	 * initializes fields to the param list
	 * 
	 * @param startDate - the start date
	 * @param comment - the event comment
	 * @param matterId - the id of the matter to which the event belongs
	 */
	public Event(Timestamp startDate, String comment, Long matterId) {
		super();
		setStartDate(startDate);
		setComment(comment);
		setMatterId(matterId);
	}
		
	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends Matter> getMatterClass() {
		return Contract.class;
	}

	@Override
	public Contract getMatter() {
		return matter;
	}

	public void setMatter(Contract m) {
		this.matter = m;
		
	}
}
