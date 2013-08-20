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

<channel:portalChannelTop channelTitle="Conveyancing" />
<div class="body">
  <strong>Conveyance</strong>
  <ul class="chan">  
  <%-- <li><portal:portalLink displayTitle="true" title="New Conveyance" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.conveyance.Conveyance&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li> --%>
  	<li><portal:portalLink displayTitle="true" title="Conveyance" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.conveyance.Conveyance&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  	<br/>
  <%-- <li><portal:portalLink displayTitle="true" title="New Conveyance Event" url="${ConfigProperties.application.url}/kr-krad/maintenance?dataObjectClassName=org.martinlaw.bo.conveyance.Event&methodToCall=start" /></li> --%>
  <li><portal:portalLink displayTitle="true" title="Conveyance Event" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.conveyance.Event&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  	</ul>
  	</br>
  	<strong>Conveyance Type</strong>  
  	  <ul class="chan">  	
  	<%-- <li><portal:portalLink displayTitle="true" title="New Conveyance Type" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.conveyance.ConveyanceType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li> --%>
  	<li><portal:portalLink displayTitle="true" title="Conveyance Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.conveyance.ConveyanceType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  </ul>
<strong>Assign Conveyance</strong>
  <ul class="chan">
  <%-- <li><portal:portalLink displayTitle="true" title="New Conveyance Assignment" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.conveyance.Assignment" /></li> --%>
  <li><portal:portalLink displayTitle="true" title="Conveyance Assignment" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.conveyance.Assignment&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  </ul>
  
  <strong>Conveyance Work</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Conveyance Work" url="${ConfigProperties.dochandler_conveyance_work}&command=initiate&viewId=conveyance_work_doc_view" /></li>
  </ul>
  
  <strong>Conveyance Consideration e.g. Legal Fee</strong>
  <ul class="chan">
  <%-- <li><portal:portalLink displayTitle="true" title="New Conveyance Consideration" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.conveyance.Consideration" /></li> --%>
  <li><portal:portalLink displayTitle="true" title="Conveyance Consideration" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.conveyance.Consideration&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  </ul>
  
  <strong>Conveyance Transaction e.g. Receipt</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Conveyance Transaction" url="${ConfigProperties.dochandler_conveyance_fee}&command=initiate" /></li>
  </ul>
  
  <strong>Document Search</strong>
	<ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Conveyance" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=ConveyanceMaintenanceDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Conveyance Type" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=ConveyanceTypeMaintenanceDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Conveyance Assignment" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=ConveyanceAssignmentMaintenanceDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Conveyance Event" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=ConveyanceEventMaintenanceDocument"/></li>
	</ul>
</div>
<channel:portalChannelBottom />

