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
  <br/>
  <li><portal:portalLink displayTitle="true" title="New Contract Event" url="${ConfigProperties.application.url}/kr-krad/maintenance?dataObjectClassName=org.martinlaw.bo.contract.Event&methodToCall=start" /></li>
  <li><portal:portalLink displayTitle="true" title="Lookup Contract Event" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.Event&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
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
  </ul>
  
  <strong>Contract Work</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Contract Work" url="${ConfigProperties.dochandler_contract_work}&command=initiate" /></li>
  </ul>
  
  <strong>Contract Fee</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Contract Fee" url="${ConfigProperties.dochandler_contract_fee}&command=initiate" /></li>
  </ul>
  
  <strong>Document Search</strong>
	<ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Contract" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=ContractMaintenanceDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Contract Type" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=ContractTypeMaintenanceDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Contract Assignment" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=ContractAssignmentMaintenanceDocument"/></li>
		<li><portal:portalLink displayTitle="true" title="Contract Event" url="${ConfigProperties.application.url}/kew/DocumentSearch.do?documentTypeName=ContractEventMaintenanceDocument"/></li>
	</ul>

</div>
<channel:portalChannelBottom />