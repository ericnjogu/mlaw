/**
 * 
 */
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


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.martinlaw.ScopedKeyValue;


/**
 * defines a type of matter annex (required document) as will be referred to by a matter type
 * e.g. land board approval or lease agreement, admission letter, p3 form
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_matter_annex_type_t")
public class MatterAnnexType extends BaseDetail implements ScopedKeyValue {
	/**
	 * 
	 */
	private static final long serialVersionUID = 971771175104844067L;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "typeId")
	private List<MatterAnnexTypeScope> scope;
	
	@Column(name="requires_approval", columnDefinition=" varchar(1) not null")
	private Boolean requiresApproval=false;
	
	
	/**
	 * @return the requiresApproval
	 */
	public Boolean getRequiresApproval() {
		return requiresApproval;
	}
	/**
	 * @param requiresApproval the requiresApproval to set
	 */
	public void setRequiresApproval(Boolean requiresApproval) {
		this.requiresApproval = requiresApproval;
	}
	@Override
	public String getKey() {
		return String.valueOf(getId());
	}
	@Override
	public String getValue() {
		return getName();
	}
	@Override
	public List<? extends Scope> getScope() {
		return scope;
	}
}
