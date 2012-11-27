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


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.martinlaw.bo.MatterConsideration;


/**
 * holds a {@link CourtCase} consideration details
 * 
 * <p>Having the info here enables changes without affecting the contract</p>
 * 
 * @author mugo
 *
 */
@Entity(name="case_consideration")
@Table(name="martinlaw_court_case_consideration_t")
public class Consideration extends MatterConsideration {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9114507684352254606L;
	/**
	 * default constructor
	 */
	public Consideration() {
		super();
	}
	@Id
	@GeneratedValue(generator = "martinlaw_court_case_consideration_s")
	@GenericGenerator(name = "martinlaw_court_case_consideration_s", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "martinlaw_court_case_consideration_s"),
			@Parameter(name = "value_column", value = "id") })
	@Column(name = "consideration_id")
	private Long id;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @see MatterConsideration#MatterConsideration(BigDecimal, String, String)
	 */
	public Consideration(BigDecimal amount, String currency,
			String description) {
		super(amount, currency, description);
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
