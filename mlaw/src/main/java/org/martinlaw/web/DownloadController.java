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


import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.rice.krad.bo.Attachment;
import org.kuali.rice.krad.service.AttachmentService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.web.controller.InquiryController;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.martinlaw.bo.conveyance.ConveyanceAttachment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	 * downloads a conveyance attachment - adapted from {@link org.kuali.rice.krad.web.controller.DocumentControllerBase#downloadAttachment}
	 * 
	 * @param uifForm - the form
	 * @param result - the binding result
	 * @param request - the http request
	 * @param response - the http response
	 * @return null
	 * @throws IOException 
	 */
	@Deprecated()//use download by attachment id below
	@RequestMapping(method = RequestMethod.POST, params = "methodToCall=downloadConveyanceAttachment")
    public ModelAndView downloadConveyanceAttachment(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
		Long conveyanceAttachmentId = null;
		try {
			conveyanceAttachmentId = Long.valueOf(uifForm.getActionParamaterValue("conveyanceAttachmentId"));
			ConveyanceAttachment convAtt = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
					ConveyanceAttachment.class, conveyanceAttachmentId);
			if (convAtt == null) {
				log.error("conveyance attachment with id '" + conveyanceAttachmentId + "' does not exist");
			} else {
				if (convAtt.getAttachment() == null) {
					log.error("no attachment was found for conveyance with id '" + conveyanceAttachmentId + "'");
				} else {
			        downloadAttachmentAsStream(response, convAtt.getAttachment());
				}
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
		}
        return null;
	}

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
