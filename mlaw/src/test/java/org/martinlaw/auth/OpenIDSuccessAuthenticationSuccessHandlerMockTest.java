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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.mail.EmailBody;
import org.kuali.rice.core.api.mail.EmailFrom;
import org.kuali.rice.core.api.mail.EmailSubject;
import org.kuali.rice.core.api.mail.EmailTo;
import org.kuali.rice.core.api.mail.Mailer;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.kim.api.identity.entity.EntityContract;
import org.kuali.rice.kim.impl.identity.affiliation.EntityAffiliationBo;
import org.kuali.rice.kim.impl.identity.affiliation.EntityAffiliationTypeBo;
import org.kuali.rice.kim.impl.identity.email.EntityEmailBo;
import org.kuali.rice.kim.impl.identity.entity.EntityBo;
import org.kuali.rice.kim.impl.identity.name.EntityNameBo;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.auth.OpenIDSuccessAuthenticationSuccessHandler.EntityInfoService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

/**
 * @author mugo
 *
 */
public class OpenIDSuccessAuthenticationSuccessHandlerMockTest {

	@Test
	/**
	 * Test method for {@link org.martinlaw.auth. OpenIDSuccessAuthenticationSuccessHandler#emailSetupOk()}
	 */
	public void testEmailSetupOk() throws IOException {
		ConfigurationService cfgSvc = mock(ConfigurationService.class);
		when(cfgSvc.getPropertyValueAsString(anyString())).thenReturn("");
		OpenIDSuccessAuthenticationSuccessHandler successHander = new OpenIDSuccessAuthenticationSuccessHandler();
		successHander.setConfigurationService(cfgSvc);
		assertFalse("no email config params exist", successHander.emailSetupOk());
		when(cfgSvc.getPropertyValueAsString(MartinlawConstants.EmailParameters.HOST_PROPERTY)).thenReturn("host");
		assertFalse("not all email config params exist", successHander.emailSetupOk());
		when(cfgSvc.getPropertyValueAsString(MartinlawConstants.EmailParameters.PORT_PROPERTY)).thenReturn("port");
		assertTrue("all email config params exist", successHander.emailSetupOk());
	}
	
	@Test
	/**
	 * Test method for {@link org.martinlaw.auth.OpenIDSuccessAuthenticationSuccessHandler#getActivationEmailBody(String, String, String)}
	 */
	public void testGetActivationEmailBody() throws IOException {
		String expected = IOUtils.toString(getClass().getResourceAsStream("openid-activation-test.html"));
		OpenIDSuccessAuthenticationSuccessHandler sucessHandler = new OpenIDSuccessAuthenticationSuccessHandler();
		String actual = sucessHandler.getActivationEmailBody("http://localhost/mlaw", "xyz", "Simon").getBody();
		assertEquals("email body differs", expected, actual);
	}
	
	/**
	 * Test method for {@link org.martinlaw.auth.OpenIDSuccessAuthenticationSuccessHandler#getActivationMessage()}.
	 */
	@Test
	public void testGetActivationMessage() {
		OpenIDSuccessAuthenticationSuccessHandler successHandler = new OpenIDSuccessAuthenticationSuccessHandler();
		String noEmailOrTokenError = "Email address was not found in response " + MartinlawConstants.OPENID_ERROR_MSG_INDICATOR;
		// test for when no token exists
		assertEquals("no token has been setup", noEmailOrTokenError, successHandler.getActivationMessage());
		
		// prepare test authentication
		SecurityContext context = new SecurityContextImpl();
		List<String> values = new ArrayList<String>(1);
		final String emailFromOpenId = "clerk3@localhost";
		values.add(emailFromOpenId);
		List<OpenIDAttribute> attributes = new ArrayList<OpenIDAttribute>();
		
		OpenIDAuthenticationToken token = new OpenIDAuthenticationToken(null, "url", "msg", attributes);
		context.setAuthentication(token);
		SecurityContextHolder.setContext(context);
		
		BusinessObjectService boSvc = mock(BusinessObjectService.class);
		successHandler.setBusinessObjectService(boSvc);
		assertEquals("no email attribute has been set in the token", noEmailOrTokenError, successHandler.getActivationMessage());
		
		// add email attribute
		OpenIDAttribute emailAttr = new OpenIDAttribute("email", "email_type", values);
		attributes.add(emailAttr);
		// simulate not finding an entity with the email provided
		when(boSvc.findMatching(same(EntityEmailBo.class), anyMapOf(String.class, String.class))).thenReturn(
				Collections.<EntityEmailBo> emptyList());
		// mock entity info svc to return the mocked entity
		EntityInfoService entityInfoSvc = mock(org.martinlaw.auth.OpenIDSuccessAuthenticationSuccessHandler.EntityInfoService.class);
		when(entityInfoSvc.getEntityByEmail(emailFromOpenId)).thenReturn(null);
		successHandler.setEntityInfoService(entityInfoSvc);
		String expected = "Email address '" + emailFromOpenId + "' is not associated with an existing user :(";
		assertEquals("entity should not be found", expected, successHandler.getActivationMessage());
		
		// provide a mock entity object
		EntityBo entity = new EntityBo();
		EntityNameBo defaultName = new EntityNameBo();
		defaultName.setActive(true);
		defaultName.setDefaultValue(true);
		final String firstName = "Karani";
		defaultName.setFirstName(firstName);
		entity.getNames().add(defaultName);
		when(entityInfoSvc.getEntityByEmail(emailFromOpenId)).thenReturn(entity);
		// prepare the entity email to be found and so that the mock entity can be returned
		EntityEmailBo emailBo = new EntityEmailBo();
		final String entityId = "entity1";
		emailBo.setEntityId(entityId);
		List<EntityEmailBo> result = new ArrayList<EntityEmailBo>();
		result.add(emailBo);
		when(boSvc.findMatching(same(EntityEmailBo.class), anyMapOf(String.class, String.class))).thenReturn(result);
		
		// test that only active users with staff affiliation can log in
		// entity.setActive(true); - does not matter
		List<EntityAffiliationBo> affils = new ArrayList<EntityAffiliationBo>();
		entity.setAffiliations(affils);
		expected = "You are not affiliated as a staff :(";
		assertEquals("no affilitiations", expected, successHandler.getActivationMessage());
		
		// test that only active, affiliated users can log in
		EntityAffiliationBo affil = new EntityAffiliationBo();
		EntityAffiliationTypeBo affilType = mock(EntityAffiliationTypeBo.class);
		when(affilType.isEmploymentAffiliationType()).thenReturn(true);
		when(affilType.isActive()).thenReturn(true);
		affil.setAffiliationType(affilType);
		affils.add(affil);
		entity.setActive(false);
		expected = "Your account is not active :(";
		assertEquals("account inactive", expected, successHandler.getActivationMessage());
		
		entity.setActive(true);
		
		// simulate that email configs and other properties are present are ok
		ConfigurationService cfgSvc = mock(ConfigurationService.class);
		when(cfgSvc.getPropertyValueAsString(anyString())).thenReturn("niko");
		final String sendingEmail = "mlaw.unt@mlaw.co.ke";
		when(cfgSvc.getPropertyValueAsString(MartinlawConstants.EmailParameters.USERNAME_PROPERTY)).thenReturn(sendingEmail);
		successHandler.setConfigurationService(cfgSvc);
		// provide a mock mailer object
		Mailer mailer = mock(Mailer.class);
		successHandler.setMailer(mailer);
		// mock the identity service - near impossible since it returns 'final' objects, which are not mockito-able
		/*IdentityService idSvc = mock(IdentityService.class);
		when(idSvc.getEntity(entityId)).thenReturn(entity);*/
		final String principalName = "marto";
		when(entityInfoSvc.getPrincipalName(any(EntityContract.class))).thenReturn(principalName);
		
		ParameterService parameterService = mock(ParameterService.class); 
		when(parameterService.getParameterValueAsString(
				any(String.class), any(String.class), any(String.class))).thenReturn(sendingEmail);
		successHandler.setParameterService(parameterService);
		String expectedActivationMsg = firstName + ", an activation email has been sent to '" + emailFromOpenId + 
				"' from address '" + sendingEmail + "'. If none comes to your inbox or spam folder after a few minutes, " +
						"please contact support";
		assertEquals("activation message differs", expectedActivationMsg,successHandler.getActivationMessage());

		verify(boSvc).save(isA(PersistableBusinessObjectBase.class));
		verify(mailer).sendEmail(any(EmailFrom.class), any(EmailTo.class), any(EmailSubject.class), any(EmailBody.class), eq(true));
	}
}
