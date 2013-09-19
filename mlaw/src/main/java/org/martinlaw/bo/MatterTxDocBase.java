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


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.kuali.rice.krad.document.TransactionalDocumentBase;
import org.kuali.rice.krad.service.KRADServiceLocator;
/**
 * holds the common fields and methods that a transactional document relating to a matter needs to have
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MatterTxDocBase extends TransactionalDocumentBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9138503410750623334L;
	@Column(name = "matter_id")
	private Long matterId;

	public MatterTxDocBase() {
		super();
	}

	/**
	 * @return the matterId
	 */
	public Long getMatterId() {
		return matterId;
	}

	/**
	 * @param matterId the matterId to set
	 */
	public void setMatterId(Long matterId) {
		this.matterId = matterId;
	}

	/**
	 * provide a public method to log errors to console - useful for unit tests
	 * 
	 * @see org.kuali.rice.krad.document.DocumentBase#logErrors()
	 */
	@Override
	public void logErrors() {
		super.logErrors();
	}

	/**
	 * checks whether the matter id given exists
	 * 
	 * @return true if it exists, false if null or otherwise
	 */
	public boolean isMatterIdValid() {
		if (getMatterId() == null) {
			return false;
		} else {
			final Matter matter = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Matter.class, getMatterId());
			return matter != null;
		}
	}
}