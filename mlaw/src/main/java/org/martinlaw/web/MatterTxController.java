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



import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.TransactionalDocumentControllerBase;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.TransactionalDocumentFormBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krad.web.form.UifFormManager;
import org.martinlaw.MartinlawConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * a controller for transactional documents
 * 
 * @author mugo
 *
 */
@Controller
@RequestMapping(value = "/" + MartinlawConstants.RequestMappings.TX)
public class MatterTxController extends TransactionalDocumentControllerBase {
	Log log = LogFactory.getLog(getClass());
	
	/**
	 * gets the doc type name from the request string if present
	 */
	@Override
	protected TransactionalDocumentFormBase createInitialForm(HttpServletRequest request) {
		TransactionalDocumentFormBase form = new MatterTxForm();
		
		String docTypeName = request.getParameter(KRADConstants.DOCUMENT_TYPE_NAME);
		if (docTypeName == null) {
			// lookups supply the form key
			UifFormManager uifFormManager = (UifFormManager) request.getSession().getAttribute(UifParameters.FORM_MANAGER);
			if (uifFormManager != null && request.getParameter(UifParameters.FORM_KEY) != null) {
				UifFormBase sessionForm = uifFormManager.getSessionForm(request.getParameter(UifParameters.FORM_KEY));
				docTypeName = ((DocumentFormBase) sessionForm).getDocTypeName();
				log.debug("retrieved doc type name from session form");
			}
		}
		if (docTypeName == null) {
			throw new RiceRuntimeException("expected parameter " + KRADConstants.DOCUMENT_TYPE_NAME + " for use in form.setDocTypeName()");
		} else {
			form.setDocTypeName(docTypeName);
		}
		
		return form;
	}
}
