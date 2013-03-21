/**
 * 
 */
package org.martinlaw.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.identity.external.EntityExternalIdentifierBo;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.MartinlawConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * load a user using the open id url presented
 * @author mugo
 *
 */
public class OpenidUserDetailsService implements UserDetailsService {

	private BusinessObjectService businessObjectService;
	private IdentityService identityService;

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String openidUrl)	throws UsernameNotFoundException {
		// get the entity id from the external identifier record, use it to fetch the entity, then get first principal
		Map<String, String> extIdCrit = new HashMap<String, String>();
		extIdCrit.put("externalId", openidUrl);
		extIdCrit.put("externalIdentifierTypeCode", MartinlawConstants.OPENID_TYPE_CODE);
		Collection<EntityExternalIdentifierBo> results = getBusinessObjectService().findMatching(
				EntityExternalIdentifierBo.class, extIdCrit);
		if (results.size() == 0) {
			throw new UsernameNotFoundException("openID '" + openidUrl + "' was not found");
		} else if (results.size() > 1) {
			throw new UsernameNotFoundException("openID '" + openidUrl + "' is occurs more than once as an external id");
		}
		String entityId = null;
		for (EntityExternalIdentifierBo extId: results) {
			entityId = extId.getEntityId();
		}
		// get the first principal and return details
		Entity entity = getIdentityService().getEntity(entityId);
		Principal principal = entity.getPrincipals().get(0);
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		auths.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (principal.isActive()) {
			return new User(principal.getPrincipalName(), "password", true, true, true, true, auths);
		} else {
			return new User(principal.getPrincipalName(), "password", false, false, false, false, auths);
		}
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

}
