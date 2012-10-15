/**
 * 
 */
package org.martinlaw.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.TransactionalDocumentController;
import org.kuali.rice.krad.web.form.TransactionForm;
import org.martinlaw.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * a controller for {@link MatterWork} documents
 * @author mugo
 *
 */
@Controller
@RequestMapping(value = "/" + Constants.RequestMappings.WORK)
public class MatterWorkController extends TransactionalDocumentController {
	Log log = LogFactory.getLog(getClass());
	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.web.controller.TransactionalDocumentController#createInitialForm(javax.servlet.http.HttpServletRequest)
	 */
	/**
	 * gets the doc type name from the request string if present
	 */
	@Override
	protected TransactionForm createInitialForm(HttpServletRequest request) {
		TransactionForm form = super.createInitialForm(request);
		
		String docTypeName = request.getParameter(KRADConstants.DOCUMENT_TYPE_NAME);
		if (docTypeName == null) {
			throw new RiceRuntimeException("expected parameter " + KRADConstants.DOCUMENT_TYPE_NAME + " for use in form.setDocTypeName()");
		}
		form.setDocTypeName(docTypeName);
		
		return form;
	}
}
