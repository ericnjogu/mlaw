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


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.martinlaw.ScopedKeyValue;

/**
 * defines an event type for use in court case or other matters
 * 
 * <p>e.g. Hearing Date, Mention Date, Bring up Date</p>
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_event_type_t")
public class EventType extends BaseDetail  implements ScopedKeyValue {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7245197877574711265L;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "typeId")
	private List<EventTypeScope> scope;

	/**
	 * @return the scope
	 */
	public List<EventTypeScope> getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(List<EventTypeScope> scope) {
		this.scope = scope;
	}

	@Override
	public String getKey() {
		return String.valueOf(getId());
	}

	@Override
	public String getValue() {
		return getName();
	}

	/**
	 * default constructor
	 */
	public EventType() {
		setScope(new ArrayList<EventTypeScope>());
	}
}
