/**
 * 
 */
package org.martinlaw.web;

import javax.servlet.ServletContextEvent;

import org.kuali.rice.core.web.listener.KualiInitializeListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * exposes the web application context for use by spring security filters
 * @author mugo
 *
 */
public class MartinlawInitializeListener extends KualiInitializeListener {

	/* (non-Javadoc)
	 * @see org.kuali.rice.core.web.listener.KualiInitializeListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce);
		sce.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, getContext());
	}

}
