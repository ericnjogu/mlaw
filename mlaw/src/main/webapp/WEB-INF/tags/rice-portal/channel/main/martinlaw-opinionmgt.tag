<%--
 Copyright 2012 Protosoft Ltd
--%>
<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Legal Opinion Management" />
<div class="body">
  
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Legal Opinion" url="${ConfigProperties.application.url}/kr-krad/maintenance?dataObjectClassName=org.martinlaw.bo.opinion.Opinion&methodToCall=start" /></li>
  <li><portal:portalLink displayTitle="true" title="Lookup Legal Opinion" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.opinion.Opinion&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>	
  </ul>

<strong>Assign Legal Opinion</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Legal Opinion Assignment" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.martinlaw.bo.opinion.Assignment" /></li>
  <!-- assignment lookup will be done as part of opinion lookup - the assignments will be a section in the inquiry page -->
  <%-- <li><portal:portalLink displayTitle="true" title="Lookup Legal Opinion Assignment" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.opinion.Assignment&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li> --%>
  </ul>
  
   <strong>Opinion Work</strong>
  <ul class="chan">
  <li><portal:portalLink displayTitle="true" title="New Opinion Work" url="${ConfigProperties.dochandler_opinion_work}&command=initiate" /></li>
  </ul>
  
</div>
<channel:portalChannelBottom />


