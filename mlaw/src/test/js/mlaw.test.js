/*
 * Copyright 2012 Protosoft Ltd
 */
/**
 * tests various utility functions in mlaw.js. Test names need a 'test' suffix to be detected
 * 
 * Start server
 * java -jar c:\users\mugo\apps\jstestdriver\JsTestDriver-1.3.4.b.jar --port 9876 --browser "c:\Program Files (x86)\Mozilla Firefox\firefox.exe"
 *
 * Run with jsTestDriver in working directory that holds this file:
 * java -jar [path/to/]JsTestDriver-1.3.4.b.jar --tests all --basePath [path/to/project/root]
 * java -jar c:\Users\mugo\apps\jstestdriver\JsTestDriver-1.3.4.b.jar --tests all --basePath c:\Users\mugo\git\mlaw\mlaw
 *
 * To run from another working directory, the --config option will need to be supplied to specify the config file location
 *
 * Good tutorial at http://www.ibm.com/developerworks/java/library/os-jstesting/index.html?ca=drs-
 */
MlawTest = TestCase("MlawTest");

/**
 * sets up a document fragment for use in tests
 */
MlawTest.prototype.setUp = function () {
    /*:DOC +=<select id="convType">
	     <option value="1" selected="selected">Type1</option>
	     <option value=""></option>
	     </select>
	     <div id="msg" style="display:none"><div>
	 <!-- expect the function to report that there is an attachment selected in this div -->
     <div id="conv-annexes-section">
     <!--simulate horizontal groups for hidden control and message field -->
 			<!-- simulates an already selected file-->
	     <div data-attr-grp="true"> 
		     <input id="att1" type="hidden" data-mlaw-conv-att-timestamp="true" value="timestamp1" />
		     <div id="fn1"><input data-mlaw-conv-att-filename="true" type='text' value="info.odt"/></div>
		     <button id="actn1">select file</button>
	     </div>
	     <!-- simulates an att selector group where no action has been taken -->
	     <div data-attr-grp="true">
		     <input id="att2" type="hidden" data-mlaw-conv-att-timestamp="true"/>
		     <div id="fn2"><input data-mlaw-conv-att-filename="true" type='text'/></div>
		     <button id="actn2">select file</button>
	     </div>
	     <!-- simulates a attachment selector where a lightbox has been specified -->
	     <div data-attr-grp="true"> 
		     <input id="att3" type="hidden"  data-mlaw-conv-att-timestamp="true" data-mlaw-lightbox-target="true"/>
		     <div id="fn3"><input data-mlaw-conv-att-filename="true" type='text' data-mlaw-lightbox-target="true"/></div>
		     <button id="actn3">select file</button>
	     </div>
     </div>
     <div id="other-section">
     <!--expect no attachment to be reported -->
	     <input id="att3"/>
     </div> 
     <!-- extract from notes and attachments -->
     <div id="notes_atts_section">
	     <div data-fn="sample-article.xml" data-ts="2012-07-29 17:37:13.935" 
	      style="display:none" class="uif-field uif-messageField" id="u339_line4"></div>
	     
	     <div data-ts="2012-07-29 17:37:03.124" style="display:none" 
	     class="uif-field uif-messageField" id="u339_line1"></div>
	     
	     <div data-fn="filename.pdf" data-ts="2012-07-29 17:37:17.026" 
	      style="display:none" class="uif-field uif-messageField" id="u339_line0"></div>
     </div>
     */
};

/**
 * tests toggleConvTypeOnAttCheck()
 */
MlawTest.prototype.testToggleConvTypeOnAttCheck = function () {
	var mlawUtil = new martinlaw.Util();
	var convTypeId = "convType";
	var msgId = "msg";
	mlawUtil.toggleConvTypeOnAttCheck(convTypeId, 'mlaw-conv-att-timestamp', msgId);
    assertEquals("Conveyance type select should be invisible", "none", jQuery("#" + convTypeId).css("display"));
    assertEquals("The msg span should be visible", "inline" , jQuery("#" + msgId).css("display"));
    assertEquals("The msg span should have text equal to the selected conveyance", "Type1" , jQuery("#" + msgId).text());
    // set selected att to blank
    jQuery("#att1").val("");
    mlawUtil.toggleConvTypeOnAttCheck(convTypeId, 'mlaw-conv-att-timestamp', msgId);
    assertEquals("Conveyance type select should be visible", "inline" , jQuery("#" + convTypeId).css("display"));
    assertEquals("The msg span should be invisible", "none" , jQuery("#" + msgId).css("display"));
};

/**
 * tests setSelectedValueOnHidden()
 */
MlawTest.prototype.testSetSelectedValueOnHidden = function () {
	var mlawUtil = new martinlaw.Util();
	var timestamp = "ts100";
	var filename = "fn100";
	var lightBoxMarkerAttr = "mlaw-lightbox-target";
	mlawUtil.setSelectedValueOnHidden("conv-annexes-section", lightBoxMarkerAttr, timestamp, filename);
	assertEquals("hidden value should have changed ",timestamp, jQuery("#att3").val());
	assertEquals("filename should have been set ", filename, jQuery("#fn3 input").val());
	assertEquals("lightbox marker should have been removed", undefined, jQuery("#fn3 input").attr(lightBoxMarkerAttr));
	assertEquals("lightbox marker should have been removed", undefined, jQuery("#att3").attr(lightBoxMarkerAttr));
};

/**
 * tests getHiddenAttSectionInfoMap()
 */
MlawTest.prototype.testgetHiddenAttSectionInfoMap = function () {
	var mlawUtil = new martinlaw.Util();
	assertEquals("notes section not found", 1, jQuery('#notes_atts_section').length);
	assertEquals("expected 3 attachment info fields", 3, jQuery('#notes_atts_section').find('div[data-ts]').length);
	var result = mlawUtil.getHiddenAttSectionInfoMap(jQuery("#notes_atts_section"), "ts", 'fn');
	// assertEquals("expected two attachments in the section", 2, result.length); // getting the length of a map in js is not straightfoward
	assertEquals("key value pair not found", "sample-article.xml", result['2012-07-29 17:37:13.935']);
	assertEquals("key value pair not found", "filename.pdf", result['2012-07-29 17:37:17.026']);
};

/**
 * tests attInfoMaptoHtmlList()
 */
MlawTest.prototype.testAttInfoMaptoHtmlList = function () {
	var attInfo = {};
	var mlawUtil = new martinlaw.Util();
	var secId = 'id1';
	var markerAttr = 'data-lb';
	var popupId = "pop3";
	var convTypeId = 'ctId';
	var tsMarkerAttr = 'ts';
	var convTypeMsgId = 'msgId'; 
	
	assertEquals("empty map should result in a blank string", "", mlawUtil.attInfoMaptoHtmlList(attInfo, secId, markerAttr, popupId, 
			convTypeId, convTypeMsgId, tsMarkerAttr));
	
	attInfo = {ts1:'fn1'};
	var expectedHtml = "<ol>\n\t<li class=\"att_file\" onclick=\"var mlawUtil = new martinlaw.Util();" +
			"mlawUtil.setSelectedValueOnHidden('" + secId + "', '" + markerAttr + 
			"', 'ts1', 'fn1'); jQuery('#" + popupId + "').dialog('close'); mlawUtil.toggleConvTypeOnAttCheck('" + 
			convTypeId + "', '" + tsMarkerAttr + "', '" + convTypeMsgId + "');\">" +
					"fn1</li>\n</ol>\n";
	assertEquals("html output differs from expected", expectedHtml, mlawUtil.attInfoMaptoHtmlList(attInfo, secId, markerAttr, popupId,
			convTypeId, convTypeMsgId, tsMarkerAttr));
	
	attInfo = {ts1:'fn1', ts2:'fn2'};
	expectedHtml = "<ol>\n\t" +
			"<li class=\"att_file\" onclick=\"var mlawUtil = new martinlaw.Util();" +
	"mlawUtil.setSelectedValueOnHidden('" + secId + "', '" + markerAttr + "', 'ts1', 'fn1'); jQuery('#" + popupId + "').dialog('close'); " +
	"mlawUtil.toggleConvTypeOnAttCheck('" + convTypeId + "', '" + tsMarkerAttr + "', '" + convTypeMsgId + "');\">" +
			"fn1</li>\n" +
	"\t<li class=\"att_file\" onclick=\"var mlawUtil = new martinlaw.Util();" +
	"mlawUtil.setSelectedValueOnHidden('" + secId + "', '" + markerAttr + "', 'ts2', 'fn2'); jQuery('#" + popupId + "').dialog('close'); " +
	"mlawUtil.toggleConvTypeOnAttCheck('" + convTypeId + "', '" + tsMarkerAttr + "', '" + convTypeMsgId + "');\">"  +
			"fn2</li>\n</ol>\n";
	assertEquals("html output differs from expected", expectedHtml, mlawUtil.attInfoMaptoHtmlList(attInfo, secId, markerAttr, popupId,
			convTypeId, convTypeMsgId, tsMarkerAttr));
};

/**
 * tests createLightboxMarkers()
 */
MlawTest.prototype.testCreateLightboxMarkers = function () {
	var mlawUtil = new martinlaw.Util();
	mlawUtil.createLightboxMarkers(jQuery("#actn2"), 'attr-grp', "mlaw-conv-att-timestamp", "mlaw-conv-att-filename", "lb-mk");
	assertEquals("does not contain lightbox marker attribute", "true", jQuery("#att2").attr('data-lb-mk'));
	assertEquals("does not contain lightbox marker attribute", "true", jQuery("#fn2 input").attr('data-lb-mk'));
	// use none existent id - see if there will be errors
	mlawUtil.createLightboxMarkers(jQuery("#actnX"), 'attr-grp', "mlaw-conv-att-timestamp", "mlaw-conv-att-filename", "lb-mk");
};