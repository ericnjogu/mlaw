<%--
 Copyright 2007-2009 The Kuali Foundation
 
 Licensed under the Educational Community License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl2.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Court Case Management" />
<div class="body">
  
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="Matter" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.Matter&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <%-- <li><portal:portalLink displayTitle="true" title="New Court Case" url="${ConfigProperties.application.url}/kr-krad/maintenance?dataObjectClassName=org.martinlaw.bo.courtcase.CourtCase&methodToCall=start" /></li> --%>
  <li><portal:portalLink displayTitle="true" title="Court Case" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.courtcase.CourtCase&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Land Case" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.courtcase.LandCase&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Conveyance" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.conveyance.Conveyance&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Contract" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.Contract&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Event" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.MatterEvent&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Court Case Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.courtcase.CourtCaseType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>	
  </ul>

<strong>Assign Court Case</strong>
  <ul class="chan">
  <%-- <li><portal:portalLink displayTitle="true" title="New Court Case Assignment" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.courtcase.Assignment" /></li> --%>
  <!-- TODO make the necessary lookup DD changes to support this lookup -->
  <li><portal:portalLink displayTitle="true" title="Assignment" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.MatterAssignee&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Work" url="${ConfigProperties.dochandler_matter_work}&command=initiate&viewId=matter_work_doc_view" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Conveyance Work" url="${ConfigProperties.dochandler_conveyance_work}&command=initiate&viewId=conveyance_work_doc_view" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Consideration" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.MatterConsideration&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Transaction" url="${ConfigProperties.dochandler_matter_transaction}&command=initiate&viewId=matter_transaction_doc_view" /></li>
  </ul>
  
	<strong>Auditing</strong>
	<ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Matter" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=MatterMaintenanceDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Court Case" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=CourtCaseMaintenanceDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Land Case" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=LandCourtCaseMaintenanceDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Conveyance" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=ConveyanceMaintenanceDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Contract" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=ContractMaintenanceDocument"/></li>	
		<li><portal:portalLink displayTitle="true" title="Event" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=MatterEventMaintenanceDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Assignment" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=MatterAssigneeMaintenanceDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Work" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=MatterWorkDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Consideration" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=MatterConsiderationMaintenanceDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Transaction" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=MatterTransactionDocument"/></li>
	</ul>
	<!-- to be enabled when the left accordion/menu is in place -->
	<%-- <strong>Building Blocks</strong>
	<ul class="chan">
	<li><portal:portalLink displayTitle="true" title="Status" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.Status&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Event Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.EventType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Consideration Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.ConsiderationType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Transaction Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.TransactionType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Work Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.WorkType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Users, Clients, Witnesses" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.rice.kim.api.identity.Person&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
  </ul> --%>
</div>
<channel:portalChannelBottom />


