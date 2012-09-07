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



<channel:portalChannelTop channelTitle="Contract Handling" />
<div class="body">
  
  <ul class="chan">
  <strong>Contract</strong>
  <li><portal:portalLink displayTitle="true" title="New Contract" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.Contract" /></li>
  <li><portal:portalLink displayTitle="true" title="Lookup Contract" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.Contract&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <strong>Contract Type</strong>
  <li><portal:portalLink displayTitle="true" title="New Contract Type" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.ContractType" /></li>
  <li><portal:portalLink displayTitle="true" title="Lookup Contract Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.ContractType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
 
  </ul>

  
</div>
<channel:portalChannelBottom />