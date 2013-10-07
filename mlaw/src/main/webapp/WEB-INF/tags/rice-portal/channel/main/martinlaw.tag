<%--
 #%L mlaw %% Copyright (C) 2012, 2013 Eric Njogu (kunadawa@gmail.com) %% This 
	program is free software: you can redistribute it and/or modify it under 
	the terms of the GNU General Public License as published by the Free Software 
	Foundation, either version 3 of the License, or (at your option) any later 
	version. This program is distributed in the hope that it will be useful, 
	but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
	or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
	more details. You should have received a copy of the GNU General Public License 
	along with this program. If not, see <http://www.gnu.org/licenses/gpl-3.0.html>. 
	#L%
--%>
<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Matters" />
<div class="body">
  
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="Matter" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.Matter&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Court Case" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.courtcase.CourtCase&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Land Case" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.courtcase.LandCase&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Conveyance" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.conveyance.Conveyance&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Contract" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.Contract&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>  
  </ul>

<strong>Matter Details</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="Event" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.MatterEvent&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>	
  <li><portal:portalLink displayTitle="true" title="Assignment" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.MatterAssignee&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="Work" url="${ConfigProperties.dochandler_matter_work}&command=initiate&viewId=matter_work_doc_view" /></li>
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
	
	<strong>Configuration</strong>
	<ul class="chan">
	<li><portal:portalLink displayTitle="true" title="Matter Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.MatterType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  	<br/>	
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
	  </ul>
</div>
<channel:portalChannelBottom />


