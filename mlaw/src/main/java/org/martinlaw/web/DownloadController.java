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


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.helpers.IOUtils;
import org.kuali.rice.krad.bo.Attachment;
import org.kuali.rice.krad.service.AttachmentService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.web.controller.InquiryController;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.MatterEvent;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * a controller for martinlaw
 * @author mugo
 *
 */
@Controller
@RequestMapping(value = "/inquiryWithAtts")
public class DownloadController extends InquiryController {
	private DownloadUtils downloadUtils;

	/**
	 * 
	 */
	public DownloadController() {
		super();
		setDownloadUtils(new DownloadUtils());
	}

	Log log = LogFactory.getLog(getClass());
	private AttachmentService attachmentService;
	private BusinessObjectService boSvc;


	/**
	 * download an attachment as an output stream
	 * 
	 * ideally should be in a super class
	 * 
	 * @param response - where to copy the attachment contents to
	 * @param att
	 * @throws IOException
	 */
	protected void downloadAttachmentAsStream(HttpServletResponse response,
			Attachment att) throws IOException {
		InputStream is = getAttachmentService().retrieveAttachmentContents(att);
		
		getDownloadUtils().downloadAsStream(response, is, att.getAttachmentMimeTypeCode(), 
				att.getAttachmentFileSize().intValue(), att.getAttachmentFileName());
	}
	
	/**
	 * gets a local copy of attachment service
	 * 
	 * this way, it is possible to provide a mock attachment service for testing
	 * @return
	 */
	public AttachmentService getAttachmentService() {
		if (attachmentService == null) {
			attachmentService = KRADServiceLocator.getAttachmentService();
		}
		return attachmentService;
	}

	/**
	 * downloads attachment by its primary key which is the same as the note that it belongs to
	 * 
	 * @param uifForm - the form object
	 * @param result - the binding
	 * @param request - the http request
	 * @param response - the http response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(params = "methodToCall=downloadAttById")
    public ModelAndView downloadAttachmentById(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
		try {
			Long attachmentId = Long.valueOf(request.getParameter("attachmentId"));
			// retrieve att
			Attachment att = getBusinessObjectService().findBySinglePrimaryKey(
					Attachment.class, attachmentId);
			if (att == null) {
				log.error("no attachment was found with id '" + attachmentId + "'");
			} else {
				downloadAttachmentAsStream(response, att);
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * downloads a date with the given uid as a vevent
	 * @param request - the servlet request
	 * @param response - the servlet response
	 * @param uid - the event's uid which is in the form id-class e.g. 101-org.mlaw.mydate
	 * @return null, while copying the vevent to the response out
	 * @throws IOException
	 */
	@RequestMapping(value="/date")
	public ModelAndView downloadDate(@ModelAttribute("KualiForm")  UifFormBase uifForm, HttpServletRequest request,  
			HttpServletResponse response, @RequestParam("uid") String uid) throws IOException {
		MatterEvent matterEvent = getMatterDate(uid);
		String template = IOUtils.toString(MatterEvent.class.getResourceAsStream(MartinlawConstants.VCALENDAR_TEMPLATE_FILE));
		if (StringUtils.isEmpty(template)) {
			throw new RuntimeException("The vcalendar template has not been defined in '" + MartinlawConstants.VCALENDAR_TEMPLATE_FILE + "'");
		}
		String calendar = matterEvent.toIcalendar(template);
		InputStream is = new ByteArrayInputStream(calendar.getBytes());
		String fileName = matterEvent.getMatter().getLocalReference() + "-" + matterEvent.getType().getName();
		getDownloadUtils().downloadAsStream(response, is, "text/calendar", calendar.length(),  fileName + ".ics");
		
		return getUIFModelAndView(uifForm);
	}

	/**
	 * retrieve the matter date represented by the uid
	 * @param calendarUid - the event's uid which is in the form id-class e.g. 101-org.mlaw.mydate
	 * @return
	 */
	public MatterEvent getMatterDate(String calendarUid) {
		if (!uidMatchesPattern(calendarUid)) {
			throw new RuntimeException("the provided uid - '" + calendarUid + 
					"' does not match the pattern '" + MartinlawConstants.VCALENDAR_UID_PATTERN + "'");
		}
		// retrieve id from supplied uid
		Long id = Long.valueOf(calendarUid.substring(0, calendarUid.indexOf("-")));
		String className = calendarUid.substring(calendarUid.indexOf("-") + 1, calendarUid.indexOf("@"));
		MatterEvent matterEvent = null;
		try {
			matterEvent = (MatterEvent)Class.forName(className).newInstance();
		} catch (Exception e) {
			log.error("error while casting '" + className + "' into MatterEvent");
			throw new RuntimeException(e);
		}
		MatterEvent event = (MatterEvent) KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(matterEvent.getClass(), id);
		if (event == null) {
			throw new IllegalArgumentException("The event identified by '" + calendarUid + "' was not found");
		}
		return event;
	}
	
	/**
	 * checks whether the calendar uid matches the pattern {@link MartinlawConstants#VCALENDAR_UID_PATTERN}
	 * @param uid
	 * @return
	 */
	public boolean uidMatchesPattern(String calendarUid) {
		Pattern pattern = Pattern.compile(MartinlawConstants.VCALENDAR_UID_PATTERN);
		Matcher matcher = pattern.matcher(calendarUid);
		return matcher.matches();
	}

	
	/**
	 * get the local reference to {@link BusinessObjectService}
	 * 
	 * @return the local reference, which may be a mock object during testing
	 */
	public BusinessObjectService getBusinessObjectService() {
		if (boSvc == null) {
			boSvc = KRADServiceLocator.getBusinessObjectService();
		}
		return boSvc;
	}
	
	/**
	 * set the local reference to {@link BusinessObjectService}
	 */
	public void setBusinessObjectService(BusinessObjectService boSvc) {
		this.boSvc = boSvc;
	}

	/**
	 * @param attachmentService the attachmentService to set
	 */
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	/**
	 * @return the downloadUtils
	 */
	public DownloadUtils getDownloadUtils() {
		return downloadUtils;
	}

	/**
	 * @param downloadUtils the downloadUtils to set
	 */
	public void setDownloadUtils(DownloadUtils downloadUtils) {
		this.downloadUtils = downloadUtils;
	}
}
