/**
 * 
 */
package org.martinlaw.bo;

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


import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.krad.util.GlobalVariables;


/**
 * provides a way for an assignee to submit work for a matter
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MatterWork extends MatterTxDocBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3637053012196079706L;
	/*@Column(name = "final", nullable = false, length=1)
	protected String status; //only used to get jpa to create the column but ojb will set statusIsFinal from/to db
*/	@Transient //marked as so that we can create  column using a string data type above
	private Boolean statusIsFinal = null;
	/**
	 * provides a way for us to select final documents
	 * 
	 * <p>This appeared to be a shortcut to creating a separate table where this BO would have been copied to on getting to final</p>
	 * @return the statusIsFinal
	 */
	public Boolean getStatusIsFinal() {
		WorkflowDocument workflowDoc = WorkflowDocumentFactory.loadDocument(GlobalVariables.getUserSession().getPrincipalId(), getDocumentNumber());
		if (workflowDoc.isFinal()) {
			setStatusIsFinal(true);
		} else {
			setStatusIsFinal(false);
		}
		return statusIsFinal;
	}
	/**
	 * @param statusIsFinal the statusIsFinal to set
	 */
	public void setStatusIsFinal(Boolean statusIsFinal) {
		this.statusIsFinal = statusIsFinal;
	}
}
