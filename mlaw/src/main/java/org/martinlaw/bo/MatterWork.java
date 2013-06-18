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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.kuali.rice.core.api.uif.RemotableAttributeErrorContract;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.action.ActionRequest;
import org.kuali.rice.kew.api.action.ActionRequestType;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.action.AdHocRevoke;
import org.kuali.rice.kew.api.action.AdHocToGroup;
import org.kuali.rice.kew.api.action.AdHocToPrincipal;
import org.kuali.rice.kew.api.action.MovePoint;
import org.kuali.rice.kew.api.action.RequestedActions;
import org.kuali.rice.kew.api.action.ReturnPoint;
import org.kuali.rice.kew.api.action.ValidActions;
import org.kuali.rice.kew.api.document.Document;
import org.kuali.rice.kew.api.document.DocumentContent;
import org.kuali.rice.kew.api.document.DocumentContentUpdate;
import org.kuali.rice.kew.api.document.DocumentDetail;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.api.document.attribute.WorkflowAttributeDefinition;
import org.kuali.rice.kew.api.document.node.RouteNodeInstance;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.util.GlobalVariables;


/**
 * provides a way for an assignee to submit work for a matter
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MatterWork extends MatterTxDocBase {
	transient Logger log = Logger.getLogger(getClass());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3637053012196079706L;
	
	/**
	 * returns the matter that has been populated by the ojb configuration
	 * <p>helps in displaying the matter name as an additional field in the work document</p>
	 * @return the matter
	 */
	@SuppressWarnings("rawtypes")
	public abstract Matter getMatter();
	
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
	 * represents a work flow document that was not found or for a newly instantiated Matter Work object
	 * @author mugo
	 *
	 */
	public class DummyWorkFlowDocument implements WorkflowDocument {

		@Override
		public String getDocumentId() {
			
			return null;
		}

		@Override
		public DocumentStatus getStatus() {
			
			return null;
		}

		@Override
		public DateTime getDateCreated() {
			
			return null;
		}

		@Override
		public DateTime getDateLastModified() {
			
			return null;
		}

		@Override
		public DateTime getDateApproved() {
			
			return null;
		}

		@Override
		public DateTime getDateFinalized() {
			
			return null;
		}

		@Override
		public String getTitle() {
			
			return null;
		}

		@Override
		public String getApplicationDocumentId() {
			
			return null;
		}

		@Override
		public String getInitiatorPrincipalId() {
			
			return null;
		}

		@Override
		public String getRoutedByPrincipalId() {
			
			return null;
		}

		@Override
		public String getDocumentTypeName() {
			
			return null;
		}

		@Override
		public String getDocumentTypeId() {
			
			return null;
		}

		@Override
		public String getDocumentHandlerUrl() {
			
			return null;
		}

		@Override
		public String getApplicationDocumentStatus() {
			
			return null;
		}

		@Override
		public DateTime getApplicationDocumentStatusDate() {
			
			return null;
		}

		@Override
		public Map<String, String> getVariables() {
			
			return null;
		}

		@Override
		public String getPrincipalId() {
			
			return null;
		}

		@Override
		public void switchPrincipal(String principalId) {
			
			
		}

		@Override
		public Document getDocument() {
			
			return null;
		}

		@Override
		public DocumentContent getDocumentContent() {
			
			return null;
		}

		@Override
		public String getApplicationContent() {
			
			return null;
		}

		@Override
		public void setTitle(String title) {
			
			
		}

		@Override
		public void setApplicationDocumentId(String applicationDocumentId) {
			
			
		}

		@Override
		public void setApplicationDocumentStatus(
				String applicationDocumentStatus) {
			
			
		}

		@Override
		public void setApplicationContent(String applicationContent) {
			
			
		}

		@Override
		public void setAttributeContent(String attributeContent) {
			
			
		}

		@Override
		public void clearAttributeContent() {
			
			
		}

		@Override
		public String getAttributeContent() {
			
			return null;
		}

		@Override
		public void addAttributeDefinition(
				WorkflowAttributeDefinition attributeDefinition) {
			
			
		}

		@Override
		public void removeAttributeDefinition(
				WorkflowAttributeDefinition attributeDefinition) {
			
			
		}

		@Override
		public void clearAttributeDefinitions() {
			
			
		}

		@Override
		public List<WorkflowAttributeDefinition> getAttributeDefinitions() {
			
			return null;
		}

		@Override
		public void setSearchableContent(String searchableContent) {
			
			
		}

		@Override
		public void addSearchableDefinition(
				WorkflowAttributeDefinition searchableDefinition) {
			
			
		}

		@Override
		public void removeSearchableDefinition(
				WorkflowAttributeDefinition searchableDefinition) {
			
			
		}

		@Override
		public void clearSearchableDefinitions() {
			
			
		}

		@Override
		public void clearSearchableContent() {
			
			
		}

		@Override
		public List<WorkflowAttributeDefinition> getSearchableDefinitions() {
			
			return null;
		}

		@Override
		public void setVariable(String name, String value) {
			
			
		}

		@Override
		public String getVariableValue(String name) {
			
			return null;
		}

		@Override
		public void setReceiveFutureRequests() {
			
			
		}

		@Override
		public void setDoNotReceiveFutureRequests() {
			
			
		}

		@Override
		public void setClearFutureRequests() {
			
			
		}

		@Override
		public String getReceiveFutureRequestsValue() {
			
			return null;
		}

		@Override
		public String getDoNotReceiveFutureRequestsValue() {
			
			return null;
		}

		@Override
		public String getClearFutureRequestsValue() {
			
			return null;
		}

		@Override
		public List<? extends RemotableAttributeErrorContract> validateAttributeDefinition(
				WorkflowAttributeDefinition attributeDefinition) {
			
			return null;
		}

		@Override
		public List<ActionRequest> getRootActionRequests() {
			
			return null;
		}

		@Override
		public List<ActionTaken> getActionsTaken() {
			
			return null;
		}

		@Override
		public ValidActions getValidActions() {
			
			return null;
		}

		@Override
		public RequestedActions getRequestedActions() {
			
			return null;
		}

		@Override
		public void saveDocument(String annotation) {
			
			
		}

		@Override
		public void route(String annotation) {
			
			
		}

		@Override
		public void complete(String annotation) {
			
			
		}

		@Override
		public void disapprove(String annotation) {
			
			
		}

		@Override
		public void approve(String annotation) {
			
			
		}

		@Override
		public void cancel(String annotation) {
			
			
		}

		@Override
		public void recall(String annotation, boolean cancel) {
			
			
		}

		@Override
		public void blanketApprove(String annotation) {
			
			
		}

		@Override
		public void blanketApprove(String annotation, String... nodeNames) {
			
			
		}

		@Override
		public void saveDocumentData() {
			
			
		}

		@Override
		public void acknowledge(String annotation) {
			
			
		}

		@Override
		public void fyi(String annotation) {
			
			
		}

		@Override
		public void fyi() {
			
			
		}

		@Override
		public void delete() {
			
			
		}

		@Override
		public void refresh() {
			
			
		}

		@Override
		public void adHocToPrincipal(ActionRequestType actionRequested,
				String annotation, String targetPrincipalId,
				String responsibilityDescription, boolean forceAction) {
			
			
		}

		@Override
		public void adHocToPrincipal(ActionRequestType actionRequested,
				String nodeName, String annotation, String targetPrincipalId,
				String responsibilityDescription, boolean forceAction) {
			
			
		}

		@Override
		public void adHocToPrincipal(ActionRequestType actionRequested,
				String nodeName, String annotation, String targetPrincipalId,
				String responsibilityDescription, boolean forceAction,
				String requestLabel) {
			
			
		}

		@Override
		public void adHocToPrincipal(AdHocToPrincipal adHocToPrincipal,
				String annotation) {
			
			
		}

		@Override
		public void adHocToGroup(ActionRequestType actionRequested,
				String annotation, String targetGroupId,
				String responsibilityDescription, boolean forceAction) {
			
			
		}

		@Override
		public void adHocToGroup(ActionRequestType actionRequested,
				String nodeName, String annotation, String targetGroupId,
				String responsibilityDescription, boolean forceAction) {
			
			
		}

		@Override
		public void adHocToGroup(ActionRequestType actionRequested,
				String nodeName, String annotation, String targetGroupId,
				String responsibilityDescription, boolean forceAction,
				String requestLabel) {
			
			
		}

		@Override
		public void adHocToGroup(AdHocToGroup adHocToGroup, String annotation) {
			
			
		}

		@Override
		public void revokeAdHocRequestById(String actionRequestId,
				String annotation) {
			
			
		}

		@Override
		public void revokeAdHocRequests(AdHocRevoke revoke, String annotation) {
			
			
		}

		@Override
		public void revokeAllAdHocRequests(String annotation) {
			
			
		}

		@Override
		public void returnToPreviousNode(String annotation, String nodeName) {
			
			
		}

		@Override
		public void returnToPreviousNode(String annotation,
				ReturnPoint returnPoint) {
			
			
		}

		@Override
		public void move(MovePoint movePoint, String annotation) {
			
			
		}

		@Override
		public void takeGroupAuthority(String annotation, String groupId) {
			
			
		}

		@Override
		public void releaseGroupAuthority(String annotation, String groupId) {
			
			
		}

		@Override
		public void placeInExceptionRouting(String annotation) {
			
			
		}

		@Override
		public void superUserBlanketApprove(String annotation) {
			
			
		}

		@Override
		public void superUserNodeApprove(String nodeName, String annotation) {
			
			
		}

		@Override
		public void superUserTakeRequestedAction(String actionRequestId,
				String annotation) {
			
			
		}

		@Override
		public void superUserDisapprove(String annotation) {
			
			
		}

		@Override
		public void superUserCancel(String annotation) {
			
			
		}

		@Override
		public void superUserReturnToPreviousNode(ReturnPoint returnPoint,
				String annotation) {
			
			
		}

		@Override
		public void logAnnotation(String annotation) {
			
			
		}

		@Override
		public boolean isCompletionRequested() {
			
			return false;
		}

		@Override
		public boolean isApprovalRequested() {
			
			return false;
		}

		@Override
		public boolean isAcknowledgeRequested() {
			
			return false;
		}

		@Override
		public boolean isFYIRequested() {
			
			return false;
		}

		@Override
		public boolean isBlanketApproveCapable() {
			
			return false;
		}

		@Override
		public boolean isRouteCapable() {
			
			return false;
		}

		@Override
		public boolean isValidAction(ActionType actionType) {
			
			return false;
		}

		@Override
		public boolean checkStatus(DocumentStatus status) {
			
			return false;
		}

		@Override
		public boolean isInitiated() {
			
			return false;
		}

		@Override
		public boolean isSaved() {
			
			return false;
		}

		@Override
		public boolean isEnroute() {
			
			return false;
		}

		@Override
		public boolean isException() {
			
			return false;
		}

		@Override
		public boolean isCanceled() {
			
			return false;
		}

		@Override
		public boolean isRecalled() {
			
			return false;
		}

		@Override
		public boolean isDisapproved() {
			
			return false;
		}

		@Override
		public boolean isApproved() {
			
			return false;
		}

		@Override
		public boolean isProcessed() {
			
			return false;
		}

		@Override
		public boolean isFinal() {
			
			return false;
		}

		@Override
		public Set<String> getNodeNames() {
			
			return null;
		}

		@Override
		public Set<String> getCurrentNodeNames() {
			
			return null;
		}

		@Override
		public List<RouteNodeInstance> getActiveRouteNodeInstances() {
			
			return null;
		}

		@Override
		public List<RouteNodeInstance> getCurrentRouteNodeInstances() {
			
			return null;
		}

		@Override
		public List<RouteNodeInstance> getRouteNodeInstances() {
			
			return null;
		}

		@Override
		public List<String> getPreviousNodeNames() {
			
			return null;
		}

		@Override
		public DocumentDetail getDocumentDetail() {
			
			return null;
		}

		@Override
		public void updateDocumentContent(
				DocumentContentUpdate documentContentUpdate) {
			
			
		}
		
	}
}
