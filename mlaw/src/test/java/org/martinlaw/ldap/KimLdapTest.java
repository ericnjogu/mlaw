/**
 * 
 */
package org.martinlaw.ldap;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.dao.impl.LdapPrincipalDaoImpl;
import org.kuali.rice.kim.ldap.PrincipalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * test whether KIM is getting principal info from LDAP OK
 * @author enjogu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class KimLdapTest {
	@Autowired
	private LdapPrincipalDaoImpl ldapDao;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		 ParameterService paramService = mock(ParameterService.class);
		 String fieldMapping = "principals.active.Y=mlaw";
		 when(paramService.getParameterValueAsString("KR-SYS", "Config", "KIM_TO_LDAP_FIELD_MAPPINGS")).thenReturn(fieldMapping);
		 ldapDao.setParameterService(paramService);
		 ((PrincipalMapper)ldapDao.getContextMappers().get("Principal")).setParameterService(paramService);
	}
	
	@Test
	//@Ignore
	public void testGet_ldapStaff() {
		testGet_ldapPerson("clerk1", true);
	}
	
	@Test
	//@Ignore
	public void testGet_ldapDummy() {
		assertNull("dummy principal should be null", ldapDao.getPrincipal("dummy"));
	}
	
	@Test
	//@Ignore
	public void testGet_ldapClient() {
		testGet_ldapPerson("client1", true);
	}
	
	@Test
	@Ignore("rice default principals are not visible when rice services are not running")
	public void testGet_ricePerson() {
		testGet_ldapPerson("admin", true);
	}
	
	@Test
	public void testGet_ldapLawyer() {
		testGet_ldapPerson("lawyer1", true);
	}
	
	@Test
	public void testGet_ldapWitness() {
		testGet_ldapPerson("witness1", false);
	}

	/**
	 * 
	 * @param principalName - the principal name to test
	 * @param isActive - whether the principal is active or not
	 */
	public void testGet_ldapPerson(String principalName, boolean isActive) {
		
		Principal person = ldapDao.getPrincipal(principalName);
		assertNotNull("ldap principal should not be null", person);
		assertEquals("ldap principal id differs from given username", principalName, person.getPrincipalId());
		assertEquals("ldap principal name differs from given username", principalName, person.getPrincipalName());
		assertEquals("principal should be active", isActive, person.isActive());
		// TODO - getEntityDefault/Info does not work yet
		//KimEntityDefaultInfo info = idSvc.getEntityDefaultInfoByPrincipalName(username);
		//assertNotNull(info);
		//TODO - extend LdapPrincipalDaoImpl and add lookups for email and department
	}
}
