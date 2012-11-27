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


import java.sql.Date;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * holds information on a contract duration
 * 
 * <p>If a start date, duration and time unit are supplied, calculate the end date on the client/server<p> 
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_contract_duration_t")
public class ContractDuration extends PersistableBusinessObjectBase {
	/**
	 * default constructor
	 */
	public ContractDuration() {
		super();
	}
	/**
	 * initialize using start date and end date
	 * 
	 * <p>The duration can be calculated when the duration time unit is selected</p>
	 * 
	 * @param startDate - the start date
	 * @param endDate - the end date
	 */
	public ContractDuration(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	/**
	 * initialize using the start date and duration
	 * 
	 * <p>Uses calendar ops to add the duration and calculate the end date</p>
	 * 
	 * @param startDate - the start date
	 * @param duration - the duration 
	 * @param durationTimeUnit - a numeric constant from {@link Calendar} that specifies the time unit
	 */
	public ContractDuration(Date startDate, long duration, long durationTimeUnit) {
		this.startDate = startDate;
		this.duration = duration;
		this.durationTimeUnit = durationTimeUnit;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -926646997349727724L;
	@Id
    @GeneratedValue(generator="martinlaw_contract_duration_s")
	@GenericGenerator(name="martinlaw_contract_duration_s",strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",parameters={
			@Parameter(name="sequence_name",value="martinlaw_contract_duration_s"),
			@Parameter(name="value_column",value="id")
	})
	@Column(name="contract_duration_id", nullable=false)
	private Long id;
	@Column(name="start_date", nullable=false)
	private Date startDate;
	@Column(name="end_date", nullable=true)
	private Date endDate;
	@Column(name="duration", nullable=true)
	private long duration;
	@Column(name="duration_time_unit", nullable=true)
	private long durationTimeUnit;
	@Column(name = "description", length = 255)
	private String description;

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * e.g. 2 - to be used in combination with the time unit e.g. 2 months
	 * 
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}
	/**
	 * this will be a user readable string e.g. day, week, hour
	 * 
	 * <p>Will be shown as a drop down, with the key being the constant representing the string in Calendar</p>
	 * @return the durationTimeUnit
	 */
	public long getDurationTimeUnit() {
		return durationTimeUnit;
	}
	/**
	 * @param durationTimeUnit the durationTimeUnit to set
	 */
	public void setDurationTimeUnit(long durationTimeUnit) {
		this.durationTimeUnit = durationTimeUnit;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the id
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
}
