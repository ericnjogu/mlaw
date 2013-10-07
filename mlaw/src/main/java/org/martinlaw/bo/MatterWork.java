/**
 * 
 */
package org.martinlaw.bo;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012, 2013  Eric Njogu (kunadawa@gmail.com)
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


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.martinlaw.MartinlawConstants;


/**
 * provides a way for work to be submitted for a matter. an annex type has to be chosen to indicate the type of document attached
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_matter_work_doc_t")
@Inheritance(strategy=InheritanceType.JOINED)
public class MatterWork extends MatterTxDocBase {
	transient Logger log = Logger.getLogger(getClass());
	@Column(name = "annex_type_id")
	private Long annexTypeId = MartinlawConstants.DEFAULT_ANNEX_TYPE_ID;
	@OneToOne
	@JoinColumn(name = "annex_type_id", nullable = false, insertable=false, updatable=false)
	private MatterAnnexType annexType;
	@OneToOne
	@JoinColumn(name = "matter_id", nullable = false, insertable=false, updatable=false)
	private Matter matter;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3637053012196079706L;
	
	/**
	 * returns the matter that has been populated by the ojb configuration
	 * <p>helps in displaying the matter name as an additional field in the work document</p>
	 * @return the matter
	 */
	public Matter getMatter() {
		return matter;
	}
	
	/**
	 * @param matter the matter to set
	 */
	public void setMatter(Matter matter) {
		this.matter = matter;
	}
	
	/**
	 * Retrieves the principal name (network id) for the document's initiator
	 * adapted from {@link org.kuali.rice.krad.web.form.DocumentFormBase#getDocumentInitiatorNetworkId}
	 *
	 * @return String initiator name
	 */
	public String getDocumentInitiatorNetworkId() {
		String initiatorNetworkId = "";
		DocumentHeader tmpDocumentHeader = getDocumentHeader();
		if (tmpDocumentHeader != null && !StringUtils.isEmpty(tmpDocumentHeader.getDocumentNumber())) {
			String initiatorPrincipalId = tmpDocumentHeader.getWorkflowDocument().getInitiatorPrincipalId();
			Person initiator = KimApiServiceLocator.getPersonService().getPerson(initiatorPrincipalId);
			if (initiator != null) {
				initiatorNetworkId = initiator.getPrincipalName();
			}
		}

		return initiatorNetworkId;
	}

	/**
	 * initializes {@link #workflowDocument} so that info can be displayed on the work inquiry section table
	 * <p>This is because during the inquiry, the work bo is fetched via ojb directly and not via the document service</p>
	 */
	@Override
	public DocumentHeader getDocumentHeader() {
		DocumentHeader tmpDocumentHeader = super.getDocumentHeader();
		if (tmpDocumentHeader != null && ! tmpDocumentHeader.hasWorkflowDocument()) {
			WorkflowDocument wfd = null;
			if (! StringUtils.isEmpty(getDocumentNumber())) {
				wfd = WorkflowDocumentFactory.loadDocument(
						GlobalVariables.getUserSession().getPrincipalId(), getDocumentNumber());
			}
			if (wfd == null) {
				log.debug("set dummy WorkflowDocument");
				tmpDocumentHeader.setWorkflowDocument(new DummyWorkFlowDocument());
			} else {
				tmpDocumentHeader.setWorkflowDocument(wfd);
				log.debug("set found WorkflowDocument");
			}
		}
		return tmpDocumentHeader;
	}
	
	/**
	 * wraps {@link org.kuali.rice.kew.api.document.DocumentContract#getDateCreated()} of 
	 * {@link org.kuali.rice.kew.api.WorkflowDocument} to return a timestamp since it will be displayed is the configured format
	 * @return the timestamp if {@link #getDocumentHeader().getWorkflowDocument().getDateCreated()} is not null, otherwise null
	 */
	public Timestamp getDateCreated() {
		DateTime dateCreated = null;
		DocumentHeader tmpHdr = getDocumentHeader();
		if (tmpHdr != null && (dateCreated = tmpHdr.getWorkflowDocument().getDateCreated()) != null) {
			return new Timestamp(dateCreated.getMillis());
		} else {
			return null;
		}
	}
	
	/**
	 * the time period between creation and last modification
	 * @return empty string if any of created or last modification times are null, the period otherwise
	 */
	public String getPeriodToLastModification() {
		final DocumentHeader tmpHdr = getDocumentHeader();
		if (tmpHdr == null) {
			return "";
		} else {
			final DateTime dateCreated = tmpHdr.getWorkflowDocument().getDateCreated();
			final DateTime dateLastModified = tmpHdr.getWorkflowDocument().getDateLastModified();
			if(dateCreated != null && dateLastModified != null) {
				Period period = new Period(dateCreated, dateLastModified);
				return period.toString(PeriodFormat.getDefault());
			} else {
				return "";
			}
		}
	}
	
	/**
	 * the time period between creation and completion
	 * @return empty string if any of created or completion times are null, the period otherwise
	 */
	public String getPeriodToCompletion() {
		final DocumentHeader tmpHdr = getDocumentHeader();
		if (tmpHdr == null) {
			return "";
		}
		final DateTime dateCreated = tmpHdr.getWorkflowDocument().getDateCreated();
		DateTime dateCompleted = getActionDate(tmpHdr, KewApiConstants.ACTION_TAKEN_COMPLETED_CD);
		if (dateCreated != null && dateCompleted != null) {
			Period period = new Period(dateCreated, dateCompleted);
			return period.toString(PeriodFormat.getDefault());
		} else {
			return "";
		}
	}

	/**
	 * retrieve an action date
	 * <p>If there are several actions of the same type, return the last of those actions encountered</p> 
	 * @param docHdr - the document header to retrieve the date from
	 * @param actionCode - the action's code
	 * @return the date if found in {@link org.kuali.rice.kew.api.WorkflowDocument#getActionsTaken()}, null otherwise
	 */
	protected DateTime getActionDate(final DocumentHeader docHdr, String actionCode) {
		DateTime date = null;
		for (ActionTaken action: docHdr.getWorkflowDocument().getActionsTaken()) {
			if (StringUtils.equals(action.getActionTaken().getCode(), actionCode)) {
				date = action.getActionDate();
			}
		}
		return date;
	}
	
	/**
	 * the time period between completion and approval
	 * @return empty string if any of created or completion times are null, the period otherwise
	 */
	public String getPeriodToApprove() {
		final DocumentHeader tmpHdr = getDocumentHeader();
		if (tmpHdr == null) {
			return "";
		}
		DateTime dateCompleted = getActionDate(tmpHdr, KewApiConstants.ACTION_TAKEN_COMPLETED_CD);
		DateTime dateApproved = getActionDate(tmpHdr, KewApiConstants.ACTION_TAKEN_APPROVED_CD);
		if (dateApproved != null && dateCompleted != null) {
			Period period = new Period(dateCompleted, dateApproved);
			return period.toString(PeriodFormat.getDefault());
		} else {
			return "";
		}
	}
	
	/**
	 * the foreign key of the annex type that this work has been assigned
	 * @return the annexTypeId
	 */
	public Long getAnnexTypeId() {
		return annexTypeId;
	}

	/**
	 * @param annexTypeId the annexTypeId to set
	 */
	public void setAnnexTypeId(Long annexTypeId) {
		this.annexTypeId = annexTypeId;
	}

	/**
	 * the type of document/file attached in this work
	 * @return the annexType
	 */
	public MatterAnnexType getAnnexType() {
		return annexType;
	}

	/**
	 * @param annexType the annexType to set
	 */
	public void setAnnexType(MatterAnnexType annexType) {
		this.annexType = annexType;
	}
}
