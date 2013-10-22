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
    /*:DOC +=<div id="accordion">
  <h3>Matters</h3>
  <div id="matters">
  <ul>
  <li>
  <a title="Court Case" id="kesi" href="portal.do?channelTitle=Court Case&amp;channelUrl=http://localhost:8080/mlaw/kr-krad/lookup?methodToCall=start&amp;dataObjectClassName=org.martinlaw.bo.courtcase.CourtCase&amp;returnLocation=http://localhost:8080/mlaw/portal.do&amp;hideReturnLink=true&amp;showMaintenanceLinks=true">
  Kesi</a>
  </ul>  
</div>
	<h3>Configuration</h3>
	<div id="config">
	<ul>
	  <li>
  <a title="Annex Type" id="annex_type" href="portal.do?channelTitle=Annex Type&amp;channelUrl=http://localhost:8080/mlaw/kr-krad/lookup?methodToCall=start&amp;dataObjectClassName=org.martinlaw.bo.MatterAnnexType&amp;returnLocation=http://localhost:8080/mlaw/portal.do&amp;hideReturnLink=true&amp;showMaintenanceLinks=true" class="portal_link">Annex Type</a>
</li>
	  </ul>
	  </div>
</div>
     */
	jQuery( "#accordion" ).accordion();
};

/**
 * tests highlightCurrentUrlInAccordion()
 */
MlawTest.prototype.testHighlightCurrentUrlInAccordion = function () {
	var mlawUtil = new martinlaw.Util();
	annexTypeUrl = 'http://localhost:8080/mlaw/portal.do?channelTitle=Annex Type&channelUrl=http://localhost:8080/mlaw/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.MatterAnnexType&returnLocation=http://localhost:8080/mlaw/portal.do&hideReturnLink=true&showMaintenanceLinks=true';
	courtCaseUrl = 'http://localhost:8080/mlaw/portal.do?channelTitle=Court Case&channelUrl=http://localhost:8080/mlaw/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.martinlaw.bo.courtcase.CourtCase&returnLocation=http://localhost:8080/mlaw/portal.do&hideReturnLink=true&showMaintenanceLinks=true';
	accordionId = 'accordion';
	
	// crash tests
	mlawUtil.highlightCurrentUrlInAccordion(undefined, accordionId);
	mlawUtil.highlightCurrentUrlInAccordion('', undefined);
	mlawUtil.highlightCurrentUrlInAccordion('not a url', '');
	mlawUtil.highlightCurrentUrlInAccordion('bad:portal.do', '');
	mlawUtil.highlightCurrentUrlInAccordion('bad:portal.do/wapi', 'hakuna');
	
	//check original values
	var normalFontWeight = jQuery("#annex_type").css('font-weight');
	
	mlawUtil.highlightCurrentUrlInAccordion(annexTypeUrl, accordionId);
	// containing div be visible attr, annex_type should be visible, with 'highlight class'/bold styling
	assertEquals("config div should be displayed", "block", jQuery("#config").css('display'));
	assertTrue("annex type link font weight should be more", jQuery("#annex_type").css('font-weight') > normalFontWeight);
	// on providing the court case url
	mlawUtil.highlightCurrentUrlInAccordion(courtCaseUrl, accordionId);
	// assertEquals("annex type link should be normal", normalFontWeight, jQuery("#annex_type").css('font-weight'));
	assertTrue("kesi link should be bold", jQuery("#kesi").css('font-weight') > normalFontWeight);
	assertEquals("matters div should be displayed", "block", jQuery("#matters").css('display'));
};