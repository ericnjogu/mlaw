/**
 * 
 */
package org.martinlaw.auth;

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

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.mail.EmailBody;
import org.kuali.rice.core.api.mail.EmailFrom;
import org.kuali.rice.core.api.mail.EmailSubject;
import org.kuali.rice.core.api.mail.EmailTo;
import org.kuali.rice.core.api.mail.Mailer;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.EntityContract;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.identity.email.EntityEmailBo;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.KRADConstants;
import org.martinlaw.MartinlawConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * changes the success handling to display an activation message for openid users, send activation email
 * @author mugo
 *
 */
public class OpenIDSuccessAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private String openIdActivationStatusUrl;
	private Logger log = Logger.getLogger(getClass());
	private EntityInfoService entityInfoService;
	private Mailer mailer;
	private BusinessObjectService businessObjectService;
	private IdentityService identityService;
	private ConfigurationService configurationService;

	/**
	 * used to redirect the user to a page where they will see their open id activation status
	 * @return the openIdActivationStatusUrl
	 */
	public String getOpenIdActivationStatusUrl() {
		return openIdActivationStatusUrl;
	}

	/**
	 * @param openIdActivationStatusUrl the openIdActivationStatusUrl to set
	 */
	public void setOpenIdActivationStatusUrl(String openIdActivationStatusUrl) {
		this.openIdActivationStatusUrl = openIdActivationStatusUrl;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails user =  (UserDetails)authentication.getPrincipal();
			if (user.getUsername().equalsIgnoreCase(MartinlawConstants.OPENID_UNACTIVATED_USERNAME)) {
				HttpSession session = request.getSession();
				session.setAttribute(MartinlawConstants.OPENID_ACTIVATION_MESSAGE, getActivationMessage());
				getRedirectStrategy().sendRedirect(request, response, getOpenIdActivationStatusUrl());
			} else {
				super.onAuthenticationSuccess(request, response, authentication);
			}
		} else {//when will the principal not be a userdetails obj?
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
	
	public String getActivationMessage() {
		/*check if the email can be retrieved from openID attributes
		 *  if it it belongs to a registered user, send the activation email in a separate thread
		 */
		OpenIDAuthenticationToken token = (OpenIDAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		// get the email attribute
		String email = null;
		if (token == null) {
			log.error("OpenIDAuthenticationToken is null");
			return "Email address was not found in response " + MartinlawConstants.OPENID_ERROR_MSG_INDICATOR;
		} else {
			List<OpenIDAttribute> attributes = token.getAttributes();
			for (OpenIDAttribute attr: attributes) {
				if (StringUtils.contains(attr.getName(), "email")) {
					email = attr.getValues().get(0);
					break;
				}
			}
		}
		
		if (email == null) {
			log.error("Email attribute was not found in attributes");
			return "Email address was not found in response " + MartinlawConstants.OPENID_ERROR_MSG_INDICATOR;
		} else {
			EntityContract entity = getEntityInfoService().getEntityByEmail(email);
			
			if (entity == null) {
				return "Email address '" + email + "' is not associated with an existing user"
						 + MartinlawConstants.OPENID_ERROR_MSG_INDICATOR;
			} else {
				if (emailSetupOk()) {
					createAndEmailActivation(email, token.getIdentityUrl(), entity);
					return entity.getDefaultName().getFirstName() + 
						", an activation email has been sent to '" + email + "' from address '" +
						getConfigurationService().getPropertyValueAsString(MartinlawConstants.EmailParameters.USERNAME_PROPERTY) 
						+ "'. If none comes to your inbox or spam after a few minutes, contact support";
				} else {
					return "An activation email could not be sent to '" + email +
							"' due to a missing email configuration" + MartinlawConstants.OPENID_ERROR_MSG_INDICATOR;
				}
			}
		}
	}
	
	/**
	 * @return the entityInfoService
	 */
	public EntityInfoService getEntityInfoService() {
		if (entityInfoService == null) {
			entityInfoService = new EntityInfoService();
		}
		return entityInfoService;
	}

	/**
	 * @param entityInfoService the entityInfoService to set
	 */
	public void setEntityInfoService(EntityInfoService entityInfoService) {
		this.entityInfoService = entityInfoService;
	}
	
	public class EntityInfoService {
		/**
		 * retrieve an entity with the email given
		 * @param email - an email address to search with
		 * @return the entity if found, null if not
		 */
		public EntityContract getEntityByEmail(String email) {
			Map<String, String> emailCrit = new HashMap<String, String>();
			emailCrit.put("emailAddress", email);
			Collection<EntityEmailBo> results = getBusinessObjectService().findMatching(EntityEmailBo.class, emailCrit);
			if (results.size() == 0) {
				log.error("email '" + email + "' was not found");
				return null;
			} else {
				// return entity from first email
				String entityId = null;
				for (EntityEmailBo emailBo: results) {
					entityId = emailBo.getEntityId();
					break;
				}
				return getIdentityService().getEntity(entityId);
			}
		}
		
		/**
		 * get the entity name in a mock friendy way
		 * @param entity - the entity to retrieve the principal name from
		 * @return the principal name
		 */
		public String getPrincipalName(EntityContract entity) {
			return entity.getPrincipals().get(0).getPrincipalName();
		}
	}
	
	/**
	 * check that the configuration service has the variables needed to send email
	 * @return true if all variables found, false if not
	 */
	public boolean emailSetupOk() {
		boolean ok = true;
		// values derived from org.kuali.rice.core.mail.MailSenderFactoryBean
		String[] emailConfigs = {MartinlawConstants.EmailParameters.USERNAME_PROPERTY, 
				MartinlawConstants.EmailParameters.PASSWORD_PROPERTY, MartinlawConstants.EmailParameters.PORT_PROPERTY, 
				MartinlawConstants.EmailParameters.HOST_PROPERTY};
		for (String config: emailConfigs) {
			if (StringUtils.isEmpty(getConfigurationService().getPropertyValueAsString(config))) {
				log.error("configuration service is missing email config '" + config + "'");
				ok = false;
			}
		}
		return ok;
	}
	
	/**
	 * create and send email activation
	 * @param email - the email to send to
	 * @param openID - the openID url
	 * @param entity - the user details fetched
	 */
	public void createAndEmailActivation(final String email, String openID, final EntityContract entity) {
		OpenidActivation activation = new OpenidActivation();
		final String activationId = UUID.randomUUID().toString();
		activation.setId(activationId);
		activation.setDestination(email);
		activation.setOpenid(openID);
		activation.setEntityId(entity.getId());
		getBusinessObjectService().save(activation);
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					getMailer().sendEmail(
							new EmailFrom(getConfigurationService().getPropertyValueAsString(
									MartinlawConstants.EmailParameters.USERNAME_PROPERTY)), 
							new EmailTo(email), 
							new EmailSubject("mLaw activation email"), 
							getActivationEmailBody(
									getConfigurationService().getPropertyValueAsString(KRADConstants.APPLICATION_URL_KEY),
									activationId,
									entity.getDefaultName().getFirstName()),
							true);
				} catch (Exception e) {
					log.error("could not send activation email", e);
				}
			}
		});
		t1.start();
	}
	
	
	/**
	 * create an email message with activation url
	 * 
	 * @param applicationUrl - the url to access the application
	 * @param activationId - to be used as a token
	 * @param firstName - used in salutation
	 * @return the message with the parameters substituted
	 * @throws IOException
	 */
	public EmailBody getActivationEmailBody(String applicationUrl,
			String activationId, String firstName) throws IOException {
		String template = IOUtils.toString(getClass().getResourceAsStream(MartinlawConstants.OPENID_ACTIVATION_TEMPLATE));
		Map<String, String> params = new HashMap<String, String>();
		params.put(MartinlawConstants.ActivationTemplateParameters.FIRST_NAME, firstName);
		params.put(MartinlawConstants.ActivationTemplateParameters.TOKEN, activationId);
		params.put(KRADConstants.APPLICATION_URL_KEY, applicationUrl);
		
		return new EmailBody(StrSubstitutor.replace(template, params));
	}
	
	/**
	 * retrieve {@link Mailer} in a mockable way
	 * @return
	 */
	public Mailer getMailer() {
		if (mailer == null) {
			mailer = CoreApiServiceLocator.getMailer();
		}
		return mailer;
	}
	
	/**
	 * @param mailer the mailer to set
	 */
	public void setMailer(Mailer mailer) {
		this.mailer = mailer;
	}

	/**
	 * @return the configuration service in mockable way
	 */
	public ConfigurationService getConfigurationService() {
		if (configurationService == null) {
			configurationService = KRADServiceLocator.getKualiConfigurationService();
		}
		return configurationService;
	}

	/**
	 * @return the businessObjectService
	 */
	public BusinessObjectService getBusinessObjectService() {
		if (businessObjectService == null) {
			businessObjectService = KRADServiceLocator.getBusinessObjectService();
		}
		return businessObjectService;
	}

	/**
	 * @param businessObjectService the boSvc to set
	 */
	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	/**
	 * @return the identityService
	 */
	public IdentityService getIdentityService() {
		if (identityService == null) {
			identityService = KimApiServiceLocator.getIdentityService();
		}
		return identityService;
	}

	/**
	 * @param identityService the identityService to set
	 */
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	/**
	 * @param configurationService the configurationService to set
	 */
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}
}
