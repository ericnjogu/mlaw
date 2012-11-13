/**
 * 
 */
package org.martinlaw.ldap;

import javax.naming.directory.SearchControls;

import org.kuali.rice.kim.dao.impl.LdapPrincipalDaoImpl;

/**
 * overrides some methods in the parent to allow for testing without the rice runtime
 * 
 * @author mugo
 *
 */
public class TestLdapPrincipalDaoImpl extends LdapPrincipalDaoImpl {

	/* (non-Javadoc)
	 * @see org.kuali.rice.kim.dao.impl.LdapPrincipalDaoImpl#getSearchControls()
	 */
	@Override
	protected SearchControls getSearchControls() {
		SearchControls retval = new SearchControls();
        retval.setCountLimit(100l);
        retval.setSearchScope(SearchControls.SUBTREE_SCOPE);
        return retval;
	}

}
