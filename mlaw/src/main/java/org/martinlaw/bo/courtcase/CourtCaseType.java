/**
 * 
 */
package org.martinlaw.bo.courtcase;

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
import org.martinlaw.bo.BaseDetail;

/**
 * indicates the court case type e.g.civil, criminal ...
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_court_case_type_t")
public class CourtCaseType extends BaseDetail {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8930634258234222250L;
	@Id
	@Column(name = "court_case_type_id")
	private Long id;
	
	/* (non-Javadoc)
	 * @see org.martinlaw.bo.BaseDetail#getId()
	 */
	@Override
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
