/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012 Eric Njogu (kunadawa@gmail.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
// namespace
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