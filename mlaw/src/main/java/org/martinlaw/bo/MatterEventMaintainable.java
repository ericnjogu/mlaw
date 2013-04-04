/**
 * 
 */
package org.martinlaw.bo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.ken.api.KenApiConstants;
import org.kuali.rice.ken.api.service.SendNotificationService;
import org.kuali.rice.ken.bo.NotificationChannelBo;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;
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
		/*only send notification when document is fully processed, when the matter event has a valid matter ref,
		 * when the matter event has a valid id and if the channel exists*/
		Map<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put("name", MartinlawConstants.NotificationTemplatePlaceholders.CALENDAR_CHANNEL_NAME);
		List<NotificationChannelBo> channels = (List<NotificationChannelBo>) KRADServiceLocator.getBusinessObjectService().findMatching(NotificationChannelBo.class, fieldValues);
		final MatterEvent matterEvent = (MatterEvent)getDataObject();
		//refresh matter event to populate the generated id
		matterEvent.refresh();
		if (channels.isEmpty()) {
			log.warn("event notification will not be sent because channel '" + 
					MartinlawConstants.NotificationTemplatePlaceholders.CALENDAR_CHANNEL_NAME + "' is not present");
		}
		if (matterEvent.getId() == null) {
			log.warn("event notification will not be sent because channel matterEvent.getId() is null");
		}
		if (documentHeader.getWorkflowDocument().isFinal() && matterEvent.getMatter() != null &&  matterEvent.getId() != null
				&& !channels.isEmpty()) {
			try {
				
				// create message
				String message = createNotificationMessage(getMaintenanceAction(), getDocumentNumber(), matterEvent, 
						IOUtils.toString(getClass().getResourceAsStream(MartinlawConstants.VCALENDAR_NOTIFICATION_TEMPLATE_MSG_FILE)), 
						getKualiConfigurationService().getPropertyValueAsString(KRADConstants.APPLICATION_URL_KEY));
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
	 * @param applicationUrl TODO
	 * @return the message
	 */
	public String createNotificationMessage(String maintenanceAction, String documentNumber, 
			MatterEvent matterEvent, String template, String applicationUrl) {
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
		notificationDetails.put(KRADConstants.APPLICATION_URL_KEY, applicationUrl);
		
		notificationDetails.put(MartinlawConstants.NotificationTemplateParameters.UID, 
				matterEvent.getEventUID());
		
		return StrSubstitutor.replace(template, notificationDetails);
	}

	/**
	 * a mock friendly way to retrieve the configuration service
	 * @return
	 */
	public ConfigurationService getKualiConfigurationService() {
		return KRADServiceLocator.getKualiConfigurationService();
	}

}
