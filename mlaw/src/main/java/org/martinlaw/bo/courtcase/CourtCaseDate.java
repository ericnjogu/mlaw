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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.martinlaw.bo.MatterDate;


/**
 * represents a hearing date set for a case
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_court_case_date_t")
public class CourtCaseDate extends MatterDate {
	@Column(name="court_case_id", nullable=false)
	private Long courtCaseId;
	/**
	 * default constructor
	 */
	public CourtCaseDate() {
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
	 * @param courtCaseId TODO
	 */
	public CourtCaseDate(Date date, String comment, Long courtCaseId) {
		super();
		setDate(date);
		setComment(comment);
		setCourtCaseId(courtCaseId);
	}
	
	@Id
    /*@GeneratedValue(generator="martinlaw_court_case_date_id_s")
	@GenericGenerator(name="martinlaw_court_case_date_id_s",strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",parameters={
			@Parameter(name="sequence_name",value="martinlaw_court_case_date_id_s"),
			@Parameter(name="value_column",value="id")
	})*/
	@Column(name="court_case_date_id")
	Long id;
	
	/**
	 * get the primary key
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the courtCaseId
	 */
	public Long getCourtCaseId() {
		return courtCaseId;
	}
	/**
	 * @param courtCaseId the courtCaseId to set
	 */
	public void setCourtCaseId(Long courtCaseId) {
		this.courtCaseId = courtCaseId;
	}
}
