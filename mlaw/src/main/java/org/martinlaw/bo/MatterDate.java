package org.martinlaw.bo;

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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@SuppressWarnings("rawtypes")
@MappedSuperclass
public abstract class MatterDate <M extends Matter> extends MatterMaintenanceHelper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8395850579903958430L;
	@Column(name = "matter_date", nullable = false)
	private Date date;
	@Column(name = "date_comment", length = 150)
	private String comment;
	@Transient
	private Long calendarEventId;
	@OneToOne
	@JoinColumn(name = "calendar_event_id", nullable = true, updatable = true)
	private CalendarEvent calendarEvent;
	@Transient
	private Long typeId;
	@OneToOne
	@JoinColumn(name = "type_id", nullable = false, updatable = true)
	private DateType type;
	@Id
	@Column(name="id")
	Long id;
	private M matter;
	
	// there is not yet a clear client requirement for this feature
	/*@Column(name = "active")
	private Boolean active;*/
	

	public MatterDate() {
		super();
	}
	
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
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * any relevant comment - optional
	 * 
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the calendarEventId
	 */
	public Long getCalendarEventId() {
		return calendarEventId;
	}

	/**
	 * @param calendarEventId the calendarEventId to set
	 */
	public void setCalendarEventId(Long calendarEventId) {
		this.calendarEventId = calendarEventId;
	}

	/**
	 * gets the event that is created by the calendar integration logic
	 * 
	 * <p>This should happen after the edited or new date has been persisted to the database</p>
	 * 
	 * @return the calendarEvent
	 */
	public CalendarEvent getCalendarEvent() {
		return calendarEvent;
	}

	/**
	 * @param calendarEvent the calendarEvent to set
	 */
	public void setCalendarEvent(CalendarEvent calendarEvent) {
		this.calendarEvent = calendarEvent;
	}

	/**
	 * @return the typeId
	 */
	public Long getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the type
	 */
	public DateType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(DateType type) {
		this.type = type;
	}

	/**
	 * whether this date is active. The reasons for deactivation can be placed in the {@link}comment
	 * @return the active
	 *//*
	public Boolean getActive() {
		return active;
	}

	*//**
	 * @param active the active to set
	 *//*
	public void setActive(Boolean active) {
		this.active = active;
	}*/
	
	/**
	 * @return the matter
	 */
	public M getMatter() {
		return matter;
	}

	/**
	 * @param matter the matter to set
	 */
	public void setMatter(M m) {
		this.matter = m;
	}
}