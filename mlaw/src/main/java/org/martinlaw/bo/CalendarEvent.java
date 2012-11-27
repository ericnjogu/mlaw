/**
 * 
 */
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


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * holds information that links a date with an online calendar event entry e.g. on google calendar
 * 
 * <p>The password, username and calendar url will be configured using system admin accessible property files
 * and wired into the service beans which will be activated by Quartz or spring integration</p>
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_calendar_event_t")
public class CalendarEvent extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7541372290907234648L;
	@Id
	@GeneratedValue(generator="martinlaw_calendar_event_s")
	@GenericGenerator(name="martinlaw_calendar_event_s",strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",parameters={
			@Parameter(name="sequence_name",value="martinlaw_calendar_event_s"),
			@Parameter(name="value_column",value="id")
	})
	@Column(name="calendar_event_id")
	private Long id;
	@Column(name="url", length=255, nullable=false)
	private String url;
	@Column(name="date_ver_nbr", nullable=false)
	private Long dateVersionNumber;
	/**
	 * default constructor
	 */
	public CalendarEvent() {
		super();
	}

	/**
	 * @param url
	 * @param dateVersionNumber
	 */
	public CalendarEvent(String url, Long dateVersionNumber) {
		super();
		setUrl(url);
		setDateVersionNumber(dateVersionNumber);
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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

	/**
	 * gets the version number that was present on this event's matter date when this event was created or updated
	 * 
	 * <p>This information will be used in determining whether the matter date has been changed and the changes need to be
	 * posted to the calendar</p>
	 * 
	 * @return the dateVersionNumber
	 */
	public Long getDateVersionNumber() {
		return dateVersionNumber;
	}

	/**
	 * @param dateVersionNumber the dateVersionNumber to set
	 */
	public void setDateVersionNumber(Long dateVersionNumber) {
		this.dateVersionNumber = dateVersionNumber;
	}
}
