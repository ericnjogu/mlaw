/**
 * 
 */
package org.martinlaw.bo.courtcase;

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

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.Matter;
import org.martinlaw.bo.MatterDate;


/**
 * represents a hearing date set for a case
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_court_case_date_t")
public class MyDate extends MatterDate<CourtCase> {

	/**
	 * default constructor
	 */
	public MyDate() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3686195124782039856L;
	/**
	 * initializes fields to the param list
	 * 
	 * @param date
	 * @param comment
	 * @param matterId TODO
	 */
	public MyDate(Date date, String comment, Long matterId) {
		super();
		setDate(date);
		setComment(comment);
		setMatterId(matterId);
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends Matter> getMatterClass() {
		return CourtCase.class;
	}
}
