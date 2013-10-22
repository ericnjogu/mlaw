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

<script type="text/javascript" >
//namespace
martinlaw = {};

// util
martinlaw.Util = function() {};

/**
 * open the accordion and highlight the current url
 * credits:
 * http://stackoverflow.com/a/5515349
 * @param windowHref
 */
martinlaw.Util.prototype.highlightCurrentUrlInAccordion = function(windowHref, accordionId) {
	// find the portion of the href that is after portal.do (if any)
	var urlEnd = '';
	if (windowHref && accordionId) {
		searchTerm = "portal.do";
		foundPos = windowHref.indexOf(searchTerm);
		if (foundPos == -1) {
			this.log("did not find '" + searchTerm + "' in url '" + windowHref + "'");
		} else {
			urlEnd = windowHref.substring(foundPos);
			// this.log("Found urlEnd '" + urlEnd + "' in url '" + windowHref + "'");
		}
	}
	var customAttr = "data-mlaw_accdn_current";
	// remove prev link styling - no need to as the page is reloaded
	/*jQuery("a[" + customAttr + "]").each(function () {
		jQuery(this).attr("font-weight", "normal");
		jQuery(this).removeAttr(customAttr);
	});*/
	// use url portion to locate the link within the accordion div
	if (urlEnd) {
		var linkSelector = "a[href='" + urlEnd + "']";
		link = jQuery("#" + accordionId ).find(linkSelector);
		if (link.length > 0) {
			link.each (function () {
				// add bold styling
				jQuery(this).css("font-weight", "bold");
				// add a custom attr
				jQuery(this).attr(customAttr, "yes");
				// find enclosing div, then the sibling h3 and click() on it
				link.parents("div[class~='ui-accordion-content']").each(function () {
					h3Id = jQuery(this).attr("aria-labelledby");
					jQuery("#" + h3Id).click();
				});
			});
		} else {
			this.log("did not find link using '" + linkSelector + "'");
		}
	} else {
		this.log("urlEnd '" + urlEnd + "' cannot be used to locate a link");
	}
};

/**
 * a debug logging utility
 * @param windowHref
 */
martinlaw.Util.prototype.log = function(msg) {
	// console.log(msg);
	// jstestdriver.log(
};
</script>

<style type="text/css">
div#accordion {width: 190px; margin-left: 15px; float: left; margin-top: 10px;}
TABLE#iframe_portlet_container_table {width: 80%;float: right;}
div#iframe_portlet_container_div {width: 80%;float: right;}
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
  	<a class="portal_link" href="mailto:mlaw@yourservice.co.ke" target="_blank" title="Contact Support"><bean:message key="app.feedback.linkText" /></a>
  </div>
  <div id="build">${ConfigProperties.version} (${ConfigProperties.datasource.ojb.platform})</div>
