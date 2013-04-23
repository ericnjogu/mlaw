package org.martinlaw.bo;

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


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.martinlaw.MartinlawConstants;
/**
 * basic event info for a matter
 * <p>this is meant to be integrated with an actual calendaring system which will offer reminders, invitations, sharing etc
 * ideally by implementing CALDAV or syncML in mLaw</p>
 * @author mugo
 *
 * @param <M>
 */
@MappedSuperclass
public abstract class MatterEvent extends MatterExtensionHelper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8395850579903958430L;
	@Column(name = "start_date", columnDefinition="datetime not null")
	private Timestamp startDate;
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
	@JoinColumn(name = "event_type_id", nullable = false, updatable = true)
	private EventType type;
	@Id
	@Column(name="id")
	Long id;
	@Transient
	private String eventSummary;
	@Column(name = "end_date",  columnDefinition="datetime null")
	private Timestamp endDate;
	@Column(name = "location", nullable=false)
	private String location;
	@Column(name = "active", columnDefinition=" varchar(1) not null")
	private Boolean active = true;
	

	public MatterEvent() {
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
	 * @return the startDate
	 */
	public Timestamp getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Timestamp date) {
		this.startDate = date;
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
	 * <p>This should happen after the edited or new startDate has been persisted to the database</p>
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
	public EventType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(EventType type) {
		this.type = type;
	}

	/**
	 * whether this event is active. The reasons for deactivation can be placed in the {@link #comment}
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	/**
	 * produce an icalendar representing this startDate as an entry
	 * @param template - the template where this object's values are substituted into
	 * @return the vcalendar output
	 */
	public String toIcalendar(String template) {
		Map<String, String> eventData = new HashMap<String, String>();
		// set the start and end dates
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyyMMdd'T'hhmmssZ");
		eventData.put("DTSTART", sdfDateTime.format(getStartDate()));
		if (getEndDate() == null) {
			eventData.put("DTEND", "");
		} else {
			eventData.put("DTEND", sdfDateTime.format(getEndDate()));
		}
		
		eventData.put("CREATED", sdfDateTime.format(getDateCreated()));
		eventData.put("DTSTAMP", sdfDateTime.format(getDateModified()));
		
		String summary = getEventSummary();
		eventData.put("SUMMARY", summary);
		eventData.put("DESCRIPTION", summary);
		
		eventData.put("UID", getEventUID());
		StrSubstitutor sub = new StrSubstitutor(eventData);
		
		return sub.replace(template);
	}
	
	/**
	 * gets the UID value to use in the calendar for identifying the source of the event
	 * @return primary key and canonical class name concatenated e.g. 1001-org.martinlaw.bo.courtcase.Event
	 */
	public String getEventUID() {
		StringBuilder sb = new StringBuilder();
		sb.append(getId());
		sb.append("-");
		sb.append(getClass().getCanonicalName());
		sb.append("@mlaw.co.ke");
		return sb.toString();
	}
	
	/**
	 * gets the event summary for use within the vcalendar output and notification
	 * @return a combination of local reference, matter name, event type name and matter startDate separated by " - "
	 */
	public String getEventSummary() {
		// have a local copy of the event summary
		if (eventSummary == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(getMatter().getLocalReference());
			sb.append(" - ");
			sb.append(getMatter().getName());
			sb.append(" - ");
			sb.append(getType().getName());
			sb.append(" - ");
			SimpleDateFormat sdf3 = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm a");
			sb.append(sdf3.format(getStartDate()));
			eventSummary = sb.toString();
		}
		return eventSummary;
	}
	
	/**
	 * populates the provided template with event values
	 * @param xmlTemplate - the template to use {@link org.martinlaw.bo.event-notification-template.xml}
	 * @param channelName - the name of the channel to use in the notification
	 * @param producerName - the producer name to use in the notification
	 * @return the xmlTemplate, with the parameters replaced with actual values
	 */
	public String toNotificationXML(String xmlTemplate, String channelName, String producerName, String message) {
		Map<String, String> eventData = new HashMap<String, String>();
		eventData.put(MartinlawConstants.NotificationTemplateParameters.CHANNEL_NAME, channelName);
		eventData.put(MartinlawConstants.NotificationTemplateParameters.PRODUCER_NAME, producerName);
		eventData.put(MartinlawConstants.NotificationTemplateParameters.MESSAGE, message);
		
		eventData.put(MartinlawConstants.NotificationTemplateParameters.TITLE, getEventSummary());
		eventData.put(MartinlawConstants.NotificationTemplateParameters.DESCRIPTION, getEventSummary());
		eventData.put(MartinlawConstants.NotificationTemplateParameters.SUMMARY, getEventSummary());
		
		eventData.put(MartinlawConstants.NotificationTemplateParameters.LOCATION, getLocation());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		// set the nanos so that tests can pass - a different value was being generated each time
		getStartDate().setNanos(0);
		final String formattedStartDate = sdf.format(getStartDate());
		eventData.put(MartinlawConstants.NotificationTemplateParameters.STARTDATETIME, formattedStartDate);
		if (getEndDate() == null) {
			eventData.put(MartinlawConstants.NotificationTemplateParameters.STOPDATETIME, formattedStartDate);
		} else {
			getEndDate().setNanos(0);
			eventData.put(MartinlawConstants.NotificationTemplateParameters.STOPDATETIME, sdf.format(getEndDate()));
		}
		return StrSubstitutor.replace(xmlTemplate, eventData);
		
	}
	
	/**
	 * retrieves the end date
	 * @return the end
	 */
	public Timestamp getEndDate() {
		/*if (endDate == null && startDate != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getStartDate());
			cal.roll(Calendar.DAY_OF_YEAR, true);
			return new Timestamp(cal.getTimeInMillis());
		} else {*/
			return endDate;
		/*}*/
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
}