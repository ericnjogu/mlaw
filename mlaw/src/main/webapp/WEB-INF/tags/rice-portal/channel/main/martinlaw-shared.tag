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

<channel:portalChannelTop channelTitle="Building Blocks" />
<div class="body">
  <strong>Status</strong>
  <ul class="chan">
  <%-- <li><portal:portalLink displayTitle="true" title="New Status" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.Status" /></li> --%>
  <li><portal:portalLink displayTitle="true" title="Status" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.Status&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  </ul>
  <br/>
  <strong>Event Type</strong>
  <ul class="chan">
  <%-- <li><portal:portalLink displayTitle="true" title="New Event Type" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.EventType" /></li> --%>
  <li><portal:portalLink displayTitle="true" title="Event Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.EventType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  </ul>
  <strong>Consideration Type</strong>
  <ul class="chan">
  <%-- <li><portal:portalLink displayTitle="true" title="New Consideration Type" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.ConsiderationType" /></li> --%>
  <li><portal:portalLink displayTitle="true" title="Consideration Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.ConsiderationType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  </ul>
  <strong>Transaction Type</strong>
  <ul class="chan">
  <%-- <li><portal:portalLink displayTitle="true" title="New Transaction Type" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.TransactionType" /></li> --%>
  <li><portal:portalLink displayTitle="true" title="Transaction Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.TransactionType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  </ul>
  <strong>Work Type</strong>
  <ul class="chan">
  <%-- <li><portal:portalLink displayTitle="true" title="New Work Type" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.WorkType" /></li> --%>
  <li><portal:portalLink displayTitle="true" title="Work Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.WorkType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  </ul>
  <strong>Users, Clients, Witnesses</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="Person" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.rice.kim.api.identity.Person&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
  </ul>

  
</div>
<channel:portalChannelBottom />
