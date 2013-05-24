/**
 * 
 */
package org.martinlaw.web.auth;

import java.io.IOException;
import java.sql.Timestamp;

import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.identity.external.EntityExternalIdentifierBo;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.auth.OpenidActivation;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * deals with an activation request
 * @author mugo
 *
 */
@Controller
public class OpenIdActivationController {
	public static final String ACCOUNT_ALREADY_ACTIVATED = "Account already activated. Please login";
	public static final String OPEN_ID_ACTIVATION_URL = "/openid-login.jsp?mlaw_openid_activation=true";
	public static final String ACTIVATION_SUCCESS = "Thank you for activating your account. Please login";
	public static final String INVALID_TOKEN = "activation token is not valid" + MartinlawConstants.OPENID_ERROR_MSG_INDICATOR;
	public static final String TOKEN_NOT_FOUND = "activation token was not found" + MartinlawConstants.OPENID_ERROR_MSG_INDICATOR;
	private BusinessObjectService businessObjectService;
	private IdentityService identityService;
	private DefaultRedirectStrategy redirectStrategy;
	/**
	 * activate an openID url using the token supplied
	 * @param token
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/activate-openid", method = RequestMethod.GET)
    public String activate(@RequestParam("token") String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if (token == null) {
			session.setAttribute(MartinlawConstants.OPENID_ACTIVATION_MESSAGE, TOKEN_NOT_FOUND);
		} else {
			OpenidActivation activation = getBusinessObjectService().findBySinglePrimaryKey(OpenidActivation.class, token);
			if (activation == null) {
				session.setAttribute(MartinlawConstants.OPENID_ACTIVATION_MESSAGE, INVALID_TOKEN);
			} else if (activation.getActivated() == null)  {
				EntityExternalIdentifierBo idBo = new EntityExternalIdentifierBo();
				idBo.setEntityId(activation.getEntityId());
				idBo.setExternalIdentifierTypeCode(MartinlawConstants.OPENID_TYPE_CODE);
				idBo.setExternalId(activation.getOpenid());
				getIdentityService().addExternalIdentifierToEntity(EntityExternalIdentifierBo.to(idBo));
				
				session.setAttribute(MartinlawConstants.OPENID_ACTIVATION_MESSAGE, ACTIVATION_SUCCESS);
				activation.setActivated(new Timestamp(System.currentTimeMillis()));
				getBusinessObjectService().save(activation);
			} else {
				session.setAttribute(MartinlawConstants.OPENID_ACTIVATION_MESSAGE, ACCOUNT_ALREADY_ACTIVATED);
			}
		}
		getRedirectStrategy().sendRedirect(request, response, OPEN_ID_ACTIVATION_URL);
		
		return null;
	}
	
	//pub
	
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
	 * @return the redirectStrategy
	 */
	public DefaultRedirectStrategy getRedirectStrategy() {
		if (redirectStrategy == null) {
			redirectStrategy = new DefaultRedirectStrategy();
		}
		return redirectStrategy;
	}

	/**
	 * @param redirectStrategy the redirectStrategy to set
	 */
	public void setRedirectStrategy(DefaultRedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
}
