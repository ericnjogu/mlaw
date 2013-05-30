/**
 * 
 */
package org.martinlaw.bo.opinion;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012,2013 Eric Njogu (kunadawa@gmail.com)
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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.Matter;

/**
 * holds information about a legal opinion
 * 
 * <p>Most of the information will be held as attachments in the maintenance doc or thru work documents by assignees</p>
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_opinion_t")
public class Opinion extends Matter<Assignee, Work, Client, Consideration, Event> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4075690470255555315L;
	/**
	 * 
	 */
	public Opinion() {
		super();
		setClients(new ArrayList<Client>()); 
		try {
			setConsiderations(createDefaultConsiderations(Consideration.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Column(name="summary")
	private String summary;
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public Class<Work> getWorkClass() {
		return Work.class;
	}
}
