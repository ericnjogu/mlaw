<%--
 Copyright 2012 Protosoft Ltd
--%>
<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>



<channel:portalChannelTop channelTitle="Contract Handling" />
<div class="body">
  <strong>Contract</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Contract" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.Contract" /></li>
  <li><portal:portalLink displayTitle="true" title="Lookup Contract" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.Contract&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  </ul>
  <br/>
   <strong>Contract Type</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Contract Type" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.ContractType" /></li>
  <li><portal:portalLink displayTitle="true" title="Lookup Contract Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.ContractType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  </ul>
  
  <strong>Assign Contract</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Contract Assignment" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.Assignment" /></li>
  <li><portal:portalLink displayTitle="true" title="Lookup Contract Assignment" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.Assignment&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  </ul>

</div>
<channel:portalChannelBottom />