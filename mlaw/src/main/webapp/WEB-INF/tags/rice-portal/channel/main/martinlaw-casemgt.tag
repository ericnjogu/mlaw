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
  <li><portal:portalLink displayTitle="true" title="New Court Case" url="${ConfigProperties.application.url}/kr-krad/maintenance?dataObjectClassName=org.martinlaw.bo.courtcase.CourtCase&methodToCall=start" /></li>
  <li><portal:portalLink displayTitle="true" title="Lookup Court Case" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.courtcase.CourtCase&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  <br/>
  <li><portal:portalLink displayTitle="true" title="New Court Case Date" url="${ConfigProperties.application.url}/kr-krad/maintenance?dataObjectClassName=org.martinlaw.bo.courtcase.MyDate&methodToCall=start" /></li>
  <li><portal:portalLink displayTitle="true" title="Lookup Court Case Date" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.courtcase.MyDate&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>	
  </ul>

<strong>Assign Court Case</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Court Case Assignment" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.courtcase.Assignment" /></li>
  </ul>
  
 <strong>Court Case Work</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Court Case Work" url="${ConfigProperties.dochandler_courtcase_work}&command=initiate&viewId=contract_work_doc_view" /></li>
  </ul>
  
  <strong>Court Case Fee</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Court Case Fee" url="${ConfigProperties.dochandler_courtcase_fee}&command=initiate" /></li>
  </ul>
  
</div>
<channel:portalChannelBottom />


