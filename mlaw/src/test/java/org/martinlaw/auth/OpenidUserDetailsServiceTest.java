/**
 * 
 */
package org.martinlaw.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kuali.rice.kim.api.identity.external.EntityExternalIdentifier;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.identity.external.EntityExternalIdentifierBo;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author mugo
 *
 */
public class OpenidUserDetailsServiceTest extends MartinlawTestsBase {

	private UserDetailsService usrDetSvc;

	/**
	 * Test method for {@link org.martinlaw.auth.OpenidUserDetailsService#loadUserByUsername(java.lang.String)}.
	 */
	@Test
	public void testLoadUserByUsername_existing_principal() {
		UserDetails user = usrDetSvc.loadUserByUsername("http://localhost/clerk1-openid.html");
		assertEquals("username differs", "clerk1", user.getUsername());
		assertEquals("password differs", "password", user.getPassword());
		assertTrue("account should not be expired", user.isAccountNonExpired());
		assertTrue("account should be unlocked", user.isAccountNonLocked());
		assertTrue("credentials should not be expired", user.isCredentialsNonExpired());
		assertTrue("user should be enabled", user.isEnabled());
	}
	
	/**
	 * Test method for {@link org.martinlaw.auth.OpenidUserDetailsService#loadUserByUsername(java.lang.String)}.
	 */
	@Test
	public void testLoadUserByUsername_inactive_principal() {
		UserDetails user = usrDetSvc.loadUserByUsername("http://localhost/clerk2-openid.html");
		assertEquals("username differs", "clerk2", user.getUsername());
		assertEquals("password differs", "password", user.getPassword());
		assertFalse("account should be expired", user.isAccountNonExpired());
		assertFalse("account should locked", user.isAccountNonLocked());
		assertFalse("credentials should be expired", user.isCredentialsNonExpired());
		assertFalse("user should be disabled", user.isEnabled());
	}
	
	/**
	 * Test method for {@link org.martinlaw.auth.OpenidUserDetailsService#loadUserByUsername(java.lang.String)}.
	 */
	@Test(expected=UsernameNotFoundException.class)
	public void testLoadUserByUsername_not_existing_principal() {
		usrDetSvc.loadUserByUsername("http://localhost/clerk5-openid.html");
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.MartinlawTestsBase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		usrDetSvc = new OpenidUserDetailsService();
		super.setUpInternal();
		setupTestData();
	}
	
	/**
	 * use rice services to setup test external id (open id) data. sql did not work possibly due to caching (was unable to disable)
	 */
	public void setupTestData() {
		// insert open ids
		List<EntityExternalIdentifier> extIds = new ArrayList<EntityExternalIdentifier>(3);
		final String[] principalNames = {"clerk1", "clerk2", "lawyer1"};
		for (String principalName: principalNames) {
			EntityExternalIdentifierBo idBo = new EntityExternalIdentifierBo();
			idBo.setEntityId(KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName).getEntityId());
			idBo.setExternalIdentifierTypeCode(MartinlawConstants.OPENID_TYPE_CODE);
			idBo.setExternalId("http://localhost/" + principalName + "-openid.html");
			extIds.add(EntityExternalIdentifierBo.to(idBo));
		}
		for (EntityExternalIdentifier extId: extIds) {
			KimApiServiceLocator.getIdentityService().addExternalIdentifierToEntity(extId);
		}
		// disable clerk2
		KimApiServiceLocator.getIdentityService().inactivatePrincipalByName("clerk2");
	}

}
