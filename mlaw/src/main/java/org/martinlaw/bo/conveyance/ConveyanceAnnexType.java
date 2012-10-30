/**
 * 
 */
package org.martinlaw.bo.conveyance;

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
import javax.persistence.Id;
import javax.persistence.Table;

import org.martinlaw.bo.BaseDetail;

/**
 * defines a type of conveyance annex (attachment) as will be referred to by a conveyance type
 * e.g. land board approval or lease agreement
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_convey_annex_type_t")
public class ConveyanceAnnexType extends BaseDetail {
	/**
	 * 
	 */
	private static final long serialVersionUID = 971771175104844067L;
	@Id
	@Column(name="convey_annex_type_id")
	Long id;
	@Column(name="convey_type_id", nullable=false)
	private Long conveyanceTypeId;
	
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
	 * gets the foreign key of the conveyance type to which this annex type is associated
	 * 
	 * @return the conveyanceTypeId
	 */
	public Long getConveyanceTypeId() {
		return conveyanceTypeId;
	}
	/**
	 * @param conveyanceTypeId the conveyanceTypeId to set
	 */
	public void setConveyanceTypeId(Long conveyanceTypeId) {
		this.conveyanceTypeId = conveyanceTypeId;
	}
}
