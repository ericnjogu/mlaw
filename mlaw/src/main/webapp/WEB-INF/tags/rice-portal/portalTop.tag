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

<!-- NOTICE: This file has been modified to display the martinlaw logo -->

<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
<title>mLaw</title>
<c:forEach items="${fn:split(ConfigProperties.portal.css.files, ',')}" var="cssFile">
	<c:if test="${fn:length(fn:trim(cssFile)) > 0}">
        <link href="${pageContext.request.contextPath}/${fn:trim(cssFile)}" rel="stylesheet" type="text/css" />
	</c:if>
</c:forEach>
<c:forEach items="${fn:split(ConfigProperties.portal.javascript.files, ',')}" var="javascriptFile">
	<c:if test="${fn:length(fn:trim(javascriptFile)) > 0}">
        <script type="text/javascript" src="${ConfigProperties.application.url}/${fn:trim(javascriptFile)}"></script>
	</c:if>
</c:forEach> 

<link href="${ConfigProperties.application.url}/krad/plugins/jqueryUI/jquery-ui-1.9.2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ConfigProperties.application.url}/krad/plugins/jqueryUI/jquery-ui-1.9.2.js"/>

<script type="text/javascript" >
if (top.location != self.location) {
	top.location = self.location;
}
</script>

<style type="text/css">
div#accordion {width: 215px; margin-left: 15px; float: left; margin-top: 10px;}
TABLE#iframe_portlet_container_table {width: 78%;float: right;}
div#iframe_portlet_container_div {width: 78%;float: right;}
DIV#footer-copyright {float: right;}
/* adapted from jquery-ui-1.9.2.css to increase the line height */
.ui-helper-reset { margin: 0; padding: 0; border: 0; outline: 0; line-height: 1.7; text-decoration: none; font-size: 100%; list-style: none; }
</style>
</head>
<body>

<div id="header" title="<c:out value="${ConfigProperties.portal.title}"/>"> 
    <img alt="mLaw Logo" src="${ConfigProperties.application.url}/images/mlaw-logo.png"></img>
  </div>
  <div id="feedback">
  	<a class="portal_link" href="mailto:mlaw.msaada@gmail.com" target="_blank" title="Contact Support"><bean:message key="app.feedback.linkText" /></a>
  </div>
  <div id="build">${ConfigProperties.version} (${ConfigProperties.datasource.ojb.platform})</div>
