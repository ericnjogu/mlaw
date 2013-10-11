<%--
 Copyright 2005-2009 The Kuali Foundation
 
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

<!-- NOTICE: This rice file has been modified to display the martinlaw copyright message -->

<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<%@ attribute name="channelTitle" required="true" %>
<%@ attribute name="channelUrl" required="true" %>
<%@ attribute name="selectedTab" required="true" %>

<portal:immutableBar />
 <div id="accordion">
  
  <h3>Matters</h3>
  <div>
  <ul>
  <li><portal:portalLink displayTitle="true" title="Matter" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.Matter&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  
  <li><portal:portalLink displayTitle="true" title="Court Case" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.courtcase.CourtCase&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>

  <li><portal:portalLink displayTitle="true" title="Land Case" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.courtcase.LandCase&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>

  <li><portal:portalLink displayTitle="true" title="Conveyance" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.conveyance.Conveyance&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>

  <li><portal:portalLink displayTitle="true" title="Contract" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.contract.Contract&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>
  </ul>  
  </div>

<h3>Matter Details</h3>
<div>
<ul>
  <li><portal:portalLink displayTitle="true" title="Event" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.MatterEvent&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>

  <li><portal:portalLink displayTitle="true" title="Assignment" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.MatterAssignee&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>

  <li><portal:portalLink displayTitle="true" title="Work" url="${ConfigProperties.dochandler_matter_work}&command=initiate&viewId=matter_work_doc_view" /></li>

  <li><portal:portalLink displayTitle="true" title="Consideration" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.MatterConsideration&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>

  <li><portal:portalLink displayTitle="true" title="Transaction" url="${ConfigProperties.dochandler_matter_transaction}&command=initiate&viewId=matter_transaction_doc_view" /></li>
</ul>
</div>
  
	<h3>Configuration</h3>
	<div>
	<ul>
	<li><portal:portalLink displayTitle="true" title="Matter Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.MatterType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>

	<li><portal:portalLink displayTitle="true" title="Status" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.Status&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>

	  <li><portal:portalLink displayTitle="true" title="Event Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.EventType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>

	  <li><portal:portalLink displayTitle="true" title="Consideration Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.ConsiderationType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>

	  <li><portal:portalLink displayTitle="true" title="Transaction Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.TransactionType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>

	  <li><portal:portalLink displayTitle="true" title="Annex Type" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.MatterAnnexType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true" /></li>

	  <li><portal:portalLink displayTitle="true" title="Users, Clients, Witnesses" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.rice.kim.api.identity.Person&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
	  </ul>
</div>

<h3>Auditing</h3>
	<div>
	<ul>
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
</div>
</div>
<script>
$( "#accordion" ).accordion();
</script>
<c:choose>
  <c:when test='${!empty channelTitle && !empty channelUrl}'>
	  <div id="iframe_portlet_container_div">
	  	<portal:iframePortletContainer channelTitle="${channelTitle}" channelUrl="${channelUrl}" />
	  </div>
  </c:when>
  <c:otherwise>
	<table border="0" cellspacing="0" cellpadding="0" id="iframe_portlet_container_table">
		<c:set var="motd" value="" scope="page"/>
		
		  	<tr valign="top" bgcolor="#FFFFFF">
				<td width="15" class="leftback-focus">&nbsp;</td>
				<td colspan="5">
				    <channel:portalChannelTop channelTitle="Message Of The Day" />
					    <div class="body">
				    	    <strong>Welcome</strong>
				    	    <c:if test="${!empty pageScope.motd}">
				    	    	<c:out value="${pageScope.motd}"  />
				    	    </c:if>
		    		    </div>
		    		<channel:portalChannelBottom />
				</td>
		   	</tr>
	   	
	   	<tr valign="top" bgcolor="#FFFFFF">
      		<td width="15" class="leftback-focus">&nbsp;</td>
	 		<c:choose>
	 		  <%-- then default to tab based actions if they are not focusing in --%>
	          <c:when test='${selectedTab == "main"}'>
	              <portal:mainTab />
	          </c:when>
	          
	          <c:when test='${selectedTab == "administration"}'>
	              <portal:administrationTab />
	          </c:when>
	          
	          <%-- as backup go to the main menu index --%>
	          <c:otherwise>
	              <portal:mainTab />
	          </c:otherwise>
	        </c:choose>
       </tr>
    </table>
  </c:otherwise>
</c:choose>

 <div id="footer-copyright">Copyright 2012 Eric Mugo. All rights reserved. <!-- <bean:message key="app.copyright" /> --></div>
