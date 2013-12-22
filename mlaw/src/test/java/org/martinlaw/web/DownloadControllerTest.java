/**
 * 
 */
package org.martinlaw.web;

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


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krad.bo.Attachment;
import org.kuali.rice.krad.service.AttachmentService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.springframework.util.FileCopyUtils;

/**
 * @author mugo
 *
 */
public class DownloadControllerTest {

	private DownloadController controller;
	private String fileContent;
	private HttpServletResponse response;
	private ByteArrayInputStream attInputStream;
	private ServletOutputStream servletOutputStream;
	private Attachment att;
	private long attLength;
	private String attMime;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		controller = new DownloadController();
		AttachmentService attSvc = mock(AttachmentService.class);
		controller.setAttachmentService(attSvc);
		fileContent = "God is good";
		attInputStream = new ByteArrayInputStream(fileContent.getBytes());
		when(attSvc.retrieveAttachmentContents(any(Attachment.class))).thenReturn(attInputStream);
		response = mock(HttpServletResponse.class);
		servletOutputStream = mock(ServletOutputStream.class);
		when(response.getOutputStream()).thenReturn(servletOutputStream);
		// setup attachment
		att = mock(Attachment.class);
		when(att.getAttachmentFileName()).thenReturn("filename.ext");
		attLength = 1024l;
		when(att.getAttachmentFileSize()).thenReturn(attLength);
		attMime = "text/plain";
		when(att.getAttachmentMimeTypeCode()).thenReturn(attMime);
	}
 
	/**
	 * Test method for {@link org.martinlaw.web.DownloadController#downloadConveyanceAttachment(org.kuali.rice.krad.web.form.UifFormBase, org.springframework.validation.BindingResult, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 */
	@Test
	@Ignore()
	public void testDownloadConveyanceAttachment() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.martinlaw.web.DownloadController#downloadAttachmentAsStream(javax.servlet.http.HttpServletResponse, org.kuali.rice.krad.bo.Attachment)}.
	 * @throws IOException 
	 */
	@Test()
	@Ignore("tested through the others")
	public void testDownloadAttachmentAsStream() throws IOException {
		// call the test target
		//controller.downloadAttachmentAsStream(response, att);
		verify(response).setContentType(attMime);
		verify(response).setContentLength((int) attLength);
		verify(servletOutputStream, atLeastOnce()).write(Arrays.copyOfRange(fileContent.getBytes(), 0, FileCopyUtils.BUFFER_SIZE), 0, fileContent.length());
		verify(servletOutputStream, atLeastOnce()).close();
	}

	/**
	 * Test method for {@link org.martinlaw.web.DownloadController#downloadAttachmentById(org.kuali.rice.krad.web.form.UifFormBase, org.springframework.validation.BindingResult, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 * @throws IOException 
	 */
	@Test
	public void testDownloadCaseAttachment() throws IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("attachmentId")).thenReturn("1001");
		BusinessObjectService boSvc = mock(BusinessObjectService.class);
		controller.setBusinessObjectService(boSvc);
		when(boSvc.findBySinglePrimaryKey(Attachment.class, 1001l)).thenReturn(att);
		controller.downloadAttachmentById(null, null, request, response);
		testDownloadAttachmentAsStream();
	}
	
	/**
	 * Test method for {@link org.martinlaw.web.CalendarController#uidMatchesPattern(java.lang.String)}.
	 */
	@Test
	public void testUidMatchesPattern() {
		assertTrue("pattern should have matched", 
				controller.uidMatchesPattern("1001-org.martinlaw.bo.courtcase.Event@mlaw.co.ke"));
		assertFalse("pattern should not have matched", 
				controller.uidMatchesPattern("1001+org.martinlaw.bo.courtcase.Event@mlaw.co.ke"));
		assertFalse("pattern should not have matched", 
				controller.uidMatchesPattern("1001-org.martinlaw.bo.courtcase.Event"));
	}

}
