// namespace
martinlaw = {};

// util
martinlaw.Util = function() {};

/**
 * toggle the conveyance type select depending on the whether an attachment has been selected in any of the attachment selects
 * 
 * <p>When none of the attachment selects has a valid attachment (blank), enable the conveyance select and
 * enable it if at least one has a valid attachment<p>
 * 
 * @param convTypeId - the html id of the conveyance type select
 * @param dataAttr - the data attribute that marks the attachment hidden timestamp input
 * @param msgId - the id of the span which will 'replace' the select field using display:none manipulation
 */
martinlaw.Util.prototype.toggleConvTypeOnAttCheck = function (convTypeId, dataAttr, msgId) {
	var atts = jQuery('input[data-' + dataAttr + ']');
	for (var i=0; i<atts.length; i++) {
		if (atts[i].value && atts[i].value != "") {
			// switch visibility
			jQuery('#'+ convTypeId).css("display", "none");
			jQuery('#'+ msgId).css("display", "inline");
			// set current label on the message field (assuming the first span is meant to hold the message)
			var convType = jQuery('#'+ convTypeId).find("option[value='" + jQuery('#'+ convTypeId).val() + "']").text();
			jQuery('#'+ msgId).text(convType);
			return;
		}
	}
	// nothing is selected
	jQuery('#'+convTypeId).css("display", "inline");
	jQuery('#'+ msgId).css("display", "none");
};

/**
 * set the timestamp of the selected file to the hidden control
 * 
 * (if value is blank, remove the 'value' attribute) - does not look right since an attachment is meant to be specified
 * 
 * @param annexSectionId - the id of the div holding the annexes
 * @param lightBoxMarkerAttr - an attribute that marks the tags where the info will be set
 * @param timestamp - the  timestamp identifying the note containing the att
 * @param filename - the name of the file selected
 */
martinlaw.Util.prototype.setSelectedValueOnHidden = function (annexSectionId, lightBoxMarkerAttr, timestamp, filename) {
	if (timestamp && timestamp != "") {
		var tsInput = jQuery('#' + annexSectionId + " input[type='hidden'][data-" + lightBoxMarkerAttr + "]");
		tsInput.val(timestamp);
		tsInput.removeAttr('data-' + lightBoxMarkerAttr);
		
		var fnField = jQuery('#' + annexSectionId + " input[type='text'][data-" + lightBoxMarkerAttr + "]");
		fnField.val(filename);
		fnField.removeAttr('data-' + lightBoxMarkerAttr);
	}
};

/**
 * retrieve the note timestamp (key) and file name (value) pairs from the hidden custom field in the notes and atts section
 * 
 * @param attSectionObj - a jquery object representing the notes and attachments section
 * @param tsAttr - the attribute containing the note timestamp
 * @param fnAttr -  the attribute containing the file name
 * @returns the map containing key value pairs - an empty map if no info was found
 */
martinlaw.Util.prototype.getHiddenAttSectionInfoMap = function (attSectionObj, tsAttr, fnAttr) {
	var result = {};
	attSectionObj.find('div[data-' + tsAttr + ']').each(function () {
		var infoObj = jQuery(this);
		var filename = infoObj.attr('data-' + fnAttr);
		if (filename) {//avoid notes with no attachments
			result[infoObj.attr('data-' + tsAttr)] = filename;
		}
	});
	return result;
};

/**
 * convert a key value map to <option></option> html that can be set as the inner html of a select
 * 
 * @param infoMap - the map to be converted
 * @param annexSectionId - the id of the div holding the annexes
 * @param lightBoxMarkerAttr  - the data attribute to set to identify the filename and timestamp tags to receive the selected file information
 * @param popupId - the id of the div whose content used to create a lightbox
 * @param dataAttr - the data attribute that marks the attachment hidden timestamp input
 * @returns the html if the map was not empty, an empty string if the map was empty
 */
martinlaw.Util.prototype.attInfoMaptoHtmlList = function(infoMap, annexSectionId, lightBoxMarkerAttr, popupId, convTypeId, 
		convTypeMsgId, dataAttr) {
	var result = "";
	// not undefined and length not undefined and not zero length
	for (var key in infoMap) {
		result += "\t<li class=\"att_file\" onclick=\"var mlawUtil = new martinlaw.Util();" +
			"mlawUtil.setSelectedValueOnHidden('" + annexSectionId + "', '" + 
			lightBoxMarkerAttr + "', '" + key + "', '" + infoMap[key] + 
			"'); jQuery('#" + popupId + "').dialog('close'); mlawUtil.toggleConvTypeOnAttCheck('" + convTypeId + "', '" + dataAttr +
			"', '" + convTypeMsgId + "');\">" +
			infoMap[key] + "</li>\n";
	}
	if (result != "") {
		return "<ol>\n" + result + "</ol>\n";
	} else {
		return result;
	};
};

/**
 * create data attributes on the time stamp and filename tags in the group
 * 
 * @param eventObj - a button which has been clicked and is in the same div with the tags containing the above info
 * @param attGrpAttr - an attribute that marks the div enclosing the time stamp, filename and action button fields
 * @param timestampAttr - a data attribute that marks the time stamp hidden input field
 * @param filenameAttr -  a data attribute that marks the filename message field
 * @param markerAttr - the data attribute to set to identify the filename and timestamp tags to receive the selected file information
 */
martinlaw.Util.prototype.createLightboxMarkers = function(eventObj, attGrpAttr, timestampAttr, filenameAttr, markerAttr) {
	var enclosingDiv = eventObj.parents('div[data-' + attGrpAttr + ']');
	var tsInput = enclosingDiv.find('input[data-' + timestampAttr + ']');
	tsInput.attr('data-' + markerAttr, "true");
	var fnInput = enclosingDiv.find('input[data-' + filenameAttr + ']');
	fnInput.attr('data-' + markerAttr, "true");
};

/**
 * carries out the action on a select file button click
 * 
 * @param noteSectionId - the id of the div enclosing the notes and attachments section
 * @param tsAttr - a data attribute that marks the time stamp hidden input field
 * @param fnAttr - a data attribute that marks the filename message field
 * @param annexSectionId - the id of the div holding the annexes
 * @param lbMarkerAttr - the data attribute to set to identify the filename and timestamp tags to receive the selected file information
 * @param popupId - the id of the div whose content used to create a lightbox
 * @param attGrpId - an attribute that marks the div enclosing the time stamp, filename and action button fields
 * @param actionObj - a button which has been clicked and is in the same div with the tags holding a pair of timestamp and filename info
 * @param tsAttrLine - a data attribute that contains the time stamp of the attachment in the notes and atts section
 * @param fnAttrLine - a data attribute that contains the filename of the attachment in the notes and atts section
 * @param convTypeId - the html id of the conveyance type select
 * @param convTypeMsgId - the id of the span which will 'replace' the select field
 */
martinlaw.Util.prototype.selectFileAction = function(noteSectionId, tsAttr, fnAttr, annexSectionId, lbMarkerAttr, popupId, 
		attGrpId, actionObj, tsAttrLine, fnAttrLine, convTypeId, convTypeMsgId) {
	var infoMap = this.getHiddenAttSectionInfoMap(actionObj.parents('#' + annexSectionId).siblings('#' + noteSectionId), tsAttrLine, fnAttrLine);
	var html = this.attInfoMaptoHtmlList(infoMap, annexSectionId, lbMarkerAttr, popupId, convTypeId, convTypeMsgId, tsAttr);
	if (html != '')	{
		jQuery('#' + popupId).html(html);
		jQuery('#' + popupId).dialog({modal:true, title:'File Selection'});
		this.createLightboxMarkers(actionObj, attGrpId, tsAttr, fnAttr, lbMarkerAttr);
	} else {
		//show non-modal msg that no attachment was found
		showGrowl('No attachment files were found.<br/>Have any been added in the notes and attachments section?', 
				'File Selection', 'infoGrowl');
	}
};