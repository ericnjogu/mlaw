/**
 * 
 */
package org.martinlaw.bo.work;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2013 Eric Njogu (kunadawa@gmail.com)
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

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mugo
 *
 */
public class NoteHelperTest {

	private NoteHelper noteHelper;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		noteHelper = new NoteHelper();
	}

	/**
	 * Test method for {@link org.martinlaw.bo.work.NoteHelper#addLinks(String)}.
	 */
	@Test
	public void testAddLinks() {
		final String httpLink = "http://www.protosoft.co.ke/some/article/that/requires/urgent/attention.html";
		final String dosLink = "c:\\temp\\case22\\history\\documents\\folder1\\folder2\\folder3\\help.odp";
		String text = "please see " + httpLink + " or" +
				" ftp://resources.net/a/file. " +
				dosLink + ", it may be of help. ";
				//"Confidential info is in /home/users/zeph/documents/history.odt";
		String expected ="please see <a href=\"" + httpLink + "\" target=\"_blank\">" + StringUtils.abbreviateMiddle(httpLink, "...", 50) + "</a> or" +
				" <a href=\"ftp://resources.net/a/file.\" target=\"_blank\">ftp://resources.net/a/file.</a> " +
				"<a href=\"file://" + dosLink + "\" target=\"_blank\">" + StringUtils.abbreviateMiddle(dosLink, "...", 50) + "</a>, it may be of help. ";
				// "Confidential info is in <a href='file:///home/users/zeph/documents/history.odt'>/home/users/zeph/documents/history.odt</a>";
		
		assertEquals("note text differs", expected, noteHelper.addLinks(text));
		assertEquals("link differs", "<a href=\"" + httpLink + "\" target=\"_blank\">" + StringUtils.abbreviateMiddle(httpLink, "...", 50) + "</a>",
				noteHelper.addLinks(httpLink));
		String longUrl = "http://localhost:8080/mlaw/portal.do?channelTitle=New%20Court%20Case%20Work" +
				"&channelUrl=http://localhost:8080/mlaw/kr-krad/tx?methodToCall=docHandler" +
				"&docTypeName=CourtCaseWorkDocument&viewId=courtcase_work_doc_view&command=initiate&viewId=contract_work_doc_view";
		assertEquals("link differs", "<a href=\"" + longUrl + "\" target=\"_blank\">" + StringUtils.abbreviateMiddle(longUrl, "...", 50) + "</a>",
				noteHelper.addLinks(longUrl));
	}
}
