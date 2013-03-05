/**
 * 
 */
package org.martinlaw.bo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.ken.api.KenApiConstants;
import org.kuali.rice.ken.api.service.SendNotificationService;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.util.KRADConstants;
import org.martinlaw.MartinlawConstants;

/**
 * customizes the save function for children of {@link MatterEvent}
 * @author mugo
 *
 */
public class MatterEventMaintainable extends MaintainableImpl {
	Log log = LogFactory.getLog(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 4804930632329838049L;

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.maintenance.MaintainableImpl#prepareForSave()
	 */
	/**
	 * overrides the parent method to set the modified timestamp
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void prepareForSave() {
		((MatterEvent)getDataObject()).setDateModified(new Timestamp(System.currentTimeMillis()));
		super.prepareForSave();
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.maintenance.MaintainableImpl#doRouteStatusChange(org.kuali.rice.krad.bo.DocumentHeader)
	 */
	@Override
	public void doRouteStatusChange(DocumentHeader documentHeader) {
		// send notification on channels
		// only send notification when document is fully processed.
		if (documentHeader.getWorkflowDocument().isProcessed()) {
			@SuppressWarnings("rawtypes")
			final MatterEvent matterEvent = (MatterEvent)getDataObject();
			
			try {
				// create message
				String message = createNotificationMessage(getMaintenanceAction(), getDocumentNumber(), matterEvent, 
						IOUtils.toString(getClass().getResourceAsStream(MartinlawConstants.VCALENDAR_NOTIFICATION_TEMPLATE_MSG_FILE)));
				//populate notification
				String notificationXml = matterEvent.toNotificationXML(
						IOUtils.toString(getClass().getResourceAsStream(MartinlawConstants.EVENT_NOTIFICATION_TEMPLATE_XML)), 
						MartinlawConstants.NotificationTemplatePlaceholders.CALENDAR_CHANNEL_NAME, 
						MartinlawConstants.NotificationTemplatePlaceholders.CALENDAR_PRODUCER_NAME, message);
				// send notification
				final SendNotificationService sendNotificationService = (SendNotificationService) GlobalResourceLoader.getService(
						new QName(KenApiConstants.Namespaces.KEN_NAMESPACE_2_0, "sendNotificationService"));
				sendNotificationService.invoke(notificationXml);
			} catch (Exception e) {
				log.error("could not create or send matter event notification", e);
			}
		}
		
		super.doRouteStatusChange(documentHeader);
	}

	/**
	 * creates a notification message from the context info
	 * @param maintenanceAction - the maintenance action being undertaken e.g. {@link KRADConstants.MAINTENANCE_EDIT_ACTION} 
	 * @param documentNumber - the document number
	 * @param matterEvent - the object being maintained
	 * @param template TODO
	 * @return the message
	 */
	public String createNotificationMessage(String maintenanceAction, String documentNumber, 
			@SuppressWarnings("rawtypes") MatterEvent matterEvent, String template) {
		// create notification message
		Map<String, String> notificationDetails = new HashMap<String, String>();
		
		if (StringUtils.equals(maintenanceAction, KRADConstants.MAINTENANCE_EDIT_ACTION)) {
			notificationDetails.put(MartinlawConstants.NotificationTemplateParameters.ACTION, 
					MartinlawConstants.NotificationTemplatePlaceholders.EDIT_ACTION);
		} else {//new or copy
			notificationDetails.put(MartinlawConstants.NotificationTemplateParameters.ACTION, 
					MartinlawConstants.NotificationTemplatePlaceholders.NEW_ACTION);
		}
		
		notificationDetails.put(KRADConstants.PARAMETER_DOC_ID, documentNumber);
		
		notificationDetails.put(MartinlawConstants.NotificationTemplateParameters.UID, 
				matterEvent.getEventUID());
		
		return StrSubstitutor.replace(template, notificationDetails);
	}

}
