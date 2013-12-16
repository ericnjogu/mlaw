/**
 * 
 */
package org.martinlaw.web;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012, 2013 Eric Njogu (kunadawa@gmail.com)
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
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.MatterEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * allows access to calendar information from matter dates
 * @author mugo
 *
 */
@Controller
@RequestMapping(value = "/calendar")
public class CalendarController {
	private Log log = LogFactory.getLog(getClass());
	private DownloadUtils downloadUtils;

	/**
	 * 
	 */
	public CalendarController() {
		setDownloadUtils(new DownloadUtils());
	}
	
	/**
	 * downloads a date with the given uid as a vevent
	 * @param request - the servlet request
	 * @param response - the servlet response
	 * @param uid - the event's uid which is in the form id-class e.g. 101-org.mlaw.mydate
	 * @return null, while copying the vevent to the response out
	 * @throws IOException
	 */
	@RequestMapping(value="/date/{uid}/")
	public String downloadDate(HttpServletRequest request,  HttpServletResponse response, 
			@PathVariable String uid) throws IOException {
		MatterEvent matterEvent = getMatterDate(uid);
		String template = IOUtils.toString(MatterEvent.class.getResourceAsStream(MartinlawConstants.VCALENDAR_TEMPLATE_FILE));
		if (StringUtils.isEmpty(template)) {
			throw new RuntimeException("The vcalendar template has not been defined in '" + MartinlawConstants.VCALENDAR_TEMPLATE_FILE + "'");
		}
		String calendar = matterEvent.toIcalendar(template);
		InputStream is = new ByteArrayInputStream(calendar.getBytes());
		String fileName = matterEvent.getMatter().getLocalReference() + "-" + matterEvent.getType().getName();
		getDownloadUtils().downloadAsStream(response, is, "text/calendar", calendar.length(),  fileName + ".ics");
		return null;
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
}
