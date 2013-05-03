/**
 * 
 */
package org.martinlaw.bo;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2013 Eric Njogu (kunadawa@gmail.com)
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
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * gives information on which matter(s) an event type applies to
 * EventType (one) -> EventTypeScope (many)
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_event_type_scope_t")
public class EventTypeScope extends Scope {
	/**
	 * 
	 */
	private static final long serialVersionUID = 698178229853855467L;
	@Id
	@Column(name="event_type_scope_id")
	private Long id;
	@Column(name="event_type_id", nullable=false)
	private Long eventTypeId;
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
	 * the foreign key that links to the related event type
	 * @return the eventTypeId
	 */
	public Long getEventTypeId() {
		return eventTypeId;
	}
	/**
	 * @param eventTypeId the eventTypeId to set
	 */
	public void setEventTypeId(Long eventTypeId) {
		this.eventTypeId = eventTypeId;
	}
}
