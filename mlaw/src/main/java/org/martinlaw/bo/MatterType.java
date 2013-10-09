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
 * indicates the matter type and uses scope to indicate the specific matter to which it applies if any e.g.civil, criminal ...
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_matter_type_t")
public class MatterType extends Type implements ScopedKeyValue {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8930634258234222250L;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy="matterTypeId")
	private List<MatterTypeAnnexDetail> annexDetails;
	/**
	 * 
	 */
	public MatterType() {
		super();
		setAnnexDetails(new ArrayList<MatterTypeAnnexDetail>());
	}
	
	/**
	 * @return the annexDetails
	 */
	public List<MatterTypeAnnexDetail> getAnnexDetails() {
		return annexDetails;
	}
	/**
	 * @param annexDetails the annexDetails to set
	 */
	public void setAnnexDetails(List<MatterTypeAnnexDetail> annexDetails) {
		this.annexDetails = annexDetails;
	}
}
