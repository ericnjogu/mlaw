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


import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.martinlaw.bo.MartinlawPerson;


/**
 * @author mugo
 *serve as a common parent to court case witness and client
 */
@MappedSuperclass
public abstract class CourtCasePerson extends MartinlawPerson {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7290495005716959116L;
	
	@Column(name="court_case_id", nullable=false)
	private Long courtCaseId;

	@Override
	protected Map<String, Object> toStringMapper() {
		Map<String, Object> propMap = super.toStringMapper();
		propMap.put("courtCaseId", getCourtCaseId());
		return propMap;
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
