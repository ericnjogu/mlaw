/**
 * 
 */
package org.martinlaw.web;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.same;
import static org.mockito.Matchers.any;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.external.EntityExternalIdentifier;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.auth.OpenidActivation;
import org.martinlaw.web.auth.OpenIdActivationController;
import org.springframework.security.web.DefaultRedirectStrategy;

/**
 * mock tests for {@link org.martinlaw.web.auth.OpenIdActivationController}
 * @author mugo
 *
 */
public class OpenIdActivationControllerTest {

	private OpenIdActivationController activationController;
	private HttpServletRequest request;
	private HttpSession session;
	private DefaultRedirectStrategy redirectStrategy;
	private BusinessObjectService boSvc;
	private IdentityService idSvc;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		activationController = new OpenIdActivationController();
		request = mock(HttpServletRequest.class);
		session = mock(HttpSession.class);
		when(request.getSession()).thenReturn(session);
		
		redirectStrategy = mock(DefaultRedirectStrategy.class);
		activationController.setRedirectStrategy(redirectStrategy);
		
		boSvc = mock(BusinessObjectService.class);
		activationController.setBusinessObjectService(boSvc);
		
		// create mock id svc
		idSvc = mock(IdentityService.class);
		activationController.setIdentityService(idSvc);
	}
	
	@After
	public void finish() throws IOException {
		verify(redirectStrategy).sendRedirect(
				same(request), any(HttpServletResponse.class), same(OpenIdActivationController.OPEN_ID_ACTIVATION_URL));
	}

	/**
	 * Test method for {@link org.martinlaw.web.auth.OpenIdActivationController#activate(java.lang.String, javax.servlet.http.HttpServletRequest)}.
	 * @throws IOException 
	 */
	@Test
	public void testActivate_null_token() throws IOException {
		activationController.activate(null, request, null);
		verify(session).setAttribute(
				same(MartinlawConstants.OPENID_ACTIVATION_MESSAGE), same(OpenIdActivationController.TOKEN_NOT_FOUND));
	}
	
	/**
	 * Test method for {@link org.martinlaw.web.auth.OpenIdActivationController#activate(java.lang.String, javax.servlet.http.HttpServletRequest)}.
	 * @throws IOException 
	 */
	@Test
	public void testActivate_invalid_token() throws IOException {
		final String token = "invalid-token";
		// simulate not finding an activation with the token provided
		when(boSvc.findBySinglePrimaryKey(OpenidActivation.class, token)).thenReturn(null);
		activationController.activate(token, request, null);
		verify(session).setAttribute(
				same(MartinlawConstants.OPENID_ACTIVATION_MESSAGE), same(OpenIdActivationController.INVALID_TOKEN));
	}
	
	/**
	 * Test method for {@link org.martinlaw.web.auth.OpenIdActivationController#activate(java.lang.String, javax.servlet.http.HttpServletRequest)}.
	 * @throws IOException 
	 */
	@Test
	public void testActivate_success() throws IOException {
		final String token = "invalid-token";
		// simulate finding an activation with the token provided
		OpenidActivation activation = new OpenidActivation();
		when(boSvc.findBySinglePrimaryKey(OpenidActivation.class, token)).thenReturn(activation);
		
		activationController.activate(token, request, null);
		
		verify(idSvc).addExternalIdentifierToEntity(any(EntityExternalIdentifier.class));
		verify(session).setAttribute(
				same(MartinlawConstants.OPENID_ACTIVATION_MESSAGE), same(OpenIdActivationController.ACTIVATION_SUCCESS));
		verify(boSvc).save(activation);
	}
	
	/**
	 * Test method for {@link org.martinlaw.web.auth.OpenIdActivationController#activate(java.lang.String, javax.servlet.http.HttpServletRequest)}.
	 * @throws IOException 
	 */
	@Test
	public void testActivate_already_activated() throws IOException {
		final String token = "invalid-token";
		// simulate finding an activated activation with the token provided
		OpenidActivation activation = new OpenidActivation();
		activation.setActivated(new Timestamp(System.currentTimeMillis()));
		when(boSvc.findBySinglePrimaryKey(OpenidActivation.class, token)).thenReturn(activation);
		
		activationController.activate(token, request, null);

		verify(session).setAttribute(
				same(MartinlawConstants.OPENID_ACTIVATION_MESSAGE), same(OpenIdActivationController.ACCOUNT_ALREADY_ACTIVATED));
	}

}
