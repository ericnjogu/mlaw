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
  <li><portal:portalLink displayTitle="true" title="New Court Case" url="${ConfigProperties.application.url}/kr-krad/maintenance?dataObjectClassName=org.martinlaw.bo.CourtCase&methodToCall=start" /></li>
  <li><portal:portalLink displayTitle="true" title="Lookup Court Case" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.CourtCase&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
	
  </ul>

  
</div>
<channel:portalChannelBottom />


<channel:portalChannelTop channelTitle="Conveyancing" />
<div class="body">
  
  <ul class="chan">  
  <li><portal:portalLink displayTitle="true" title="New Conveyance" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.Conveyance&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
  	<li><portal:portalLink displayTitle="true" title="Lookup Conveyance" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.Conveyance&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>  	
  	<li><portal:portalLink displayTitle="true" title="New Conveyance Type" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.ConveyanceType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
  	<li><portal:portalLink displayTitle="true" title="Lookup Conveyance Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.ConveyanceType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
	
  </ul>

  
</div>
<channel:portalChannelBottom />

<channel:portalChannelTop channelTitle="Contract Handling" />
<div class="body">
  
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Contract Type" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.ContractType" /></li>
  <li><portal:portalLink displayTitle="true" title="Lookup Contract Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.ContractType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
 
  </ul>

  
</div>
<channel:portalChannelBottom />

<channel:portalChannelTop channelTitle="Status Management" />
<div class="body">
  
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Status" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.Status" /></li>
  <li><portal:portalLink displayTitle="true" title="Lookup Status" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.Status&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
 
  </ul>

  
</div>
<channel:portalChannelBottom />
