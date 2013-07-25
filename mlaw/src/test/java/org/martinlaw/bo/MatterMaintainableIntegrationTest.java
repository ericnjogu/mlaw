/**
 * 
 */
package org.martinlaw.bo;

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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.name.EntityNameContract;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.identity.affiliation.EntityAffiliationBo;
import org.kuali.rice.kim.impl.identity.name.EntityNameBo;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.test.MartinlawTestsBase;

/**
 * tests {@link org.martinlaw.bo.MatterMaintainable}
 * @author mugo
 */
public class MatterMaintainableIntegrationTest extends MartinlawTestsBase {

	private MatterMaintainable maintainable;
	private IdentityService idSvc;
	
	/**
	 * Test method for {@link org.martinlaw.bo.MatterMaintainable#createPrincipal(java.lang.String, java.lang.String, String)}.
	 */
	@Test
	public void testCreatePrincipal_four_names() {
		final String firstName = "Eric";
		final String middleName1 = "Felix";
		final String middleName2 = "Mugo";
		final String lastName = "Njogu";
		final String namesFromUser = " " + firstName +"	 " + middleName1 + "		" + middleName2 + " " + lastName + " 	";
		maintainable.createPrincipal(maintainable.createPrincipalName(namesFromUser), 
				MartinlawConstants.AffiliationCodes.WITNESS, namesFromUser);
		final EntityNameContract entityName = testCreatePrincipal("eric_felix_mugo_njogu", MartinlawConstants.AffiliationCodes.WITNESS);
		
		assertEquals("first name differs", firstName, entityName.getFirstName());
		assertEquals("middle name differs", middleName1 + " " + middleName2, entityName.getMiddleName());
		assertEquals("last name differs", lastName, entityName.getLastName());
	}
	
	/**
	 * Test method for {@link org.martinlaw.bo.MatterMaintainable#createPrincipal(java.lang.String, java.lang.String, String)}.
	 */
	@Test
	public void testCreatePrincipal_three_names() {
		final String firstName = "DAVID";
		final String middleName = "Njogu";
		final String lastName = "Mungai";
		final String namesFromUser = " " + firstName +"	 " + middleName + "		" + lastName + " ";
		maintainable.createPrincipal(maintainable.createPrincipalName(namesFromUser), 
				MartinlawConstants.AffiliationCodes.CLIENT, namesFromUser);
		final EntityNameContract entityName = testCreatePrincipal("david_njogu_mungai", MartinlawConstants.AffiliationCodes.CLIENT);
		
		assertEquals("first name differs", firstName, entityName.getFirstName());
		assertEquals("last name differs", middleName, entityName.getMiddleName());
		assertEquals("last name differs", lastName, entityName.getLastName());
	}

	/**
	 * Test method for {@link org.martinlaw.bo.MatterMaintainable#createPrincipal(java.lang.String, java.lang.String, String)}.
	 */
	@Test
	public void testCreatePrincipal_two_names() {
		final String firstName = "Naomi";
		final String lastName = "Mathenge";
		final String namesFromUser = " " + firstName +"	  	" + lastName + " ";
		maintainable.createPrincipal(maintainable.createPrincipalName(namesFromUser), 
				MartinlawConstants.AffiliationCodes.CLIENT, namesFromUser);
		final EntityNameContract entityName = testCreatePrincipal("naomi_mathenge", MartinlawConstants.AffiliationCodes.CLIENT);
		
		assertEquals("first name differs", firstName, entityName.getFirstName());
		assertEquals("last name differs", lastName, entityName.getLastName());
	}
	
	/**
	 * Test method for {@link org.martinlaw.bo.MatterMaintainable#createPrincipal(java.lang.String, java.lang.String, String)}.
	 */
	@Test
	public void testCreatePrincipal_one_name() {
		final String firstName = "ADRIAN";
		final String namesFromUser = " " + firstName +"	  	 ";
		maintainable.createPrincipal(maintainable.createPrincipalName(namesFromUser), MartinlawConstants.AffiliationCodes.WITNESS,
				namesFromUser);
		final EntityNameContract entityName = testCreatePrincipal("adrian", MartinlawConstants.AffiliationCodes.WITNESS);
		
		assertEquals("first name differs", firstName, entityName.getFirstName());
	}

	/**
	 * common method to test principal creation
	 * @param expectedPrincipalName - the expected principal name
	 * @param expectedAffilCode TODO
	 * @return the entity name for further tests
	 */
	public EntityNameContract testCreatePrincipal(String expectedPrincipalName, String expectedAffilCode) {
		Principal principal = idSvc.getPrincipalByPrincipalName(expectedPrincipalName);
		assertNotNull("principal should have been created", principal);
		
		Person person = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(expectedPrincipalName);
		assertNotNull("person should have been created", person);
		
		EntityDefault entity = idSvc.getEntityDefault(principal.getEntityId());
		assertNotNull("entity should have been created", entity);
		
		Map<String, String> criteria = new HashMap<String, String>(); 
		criteria.put("entityId", entity.getEntityId());
		Collection<EntityAffiliationBo> results = getBoSvc().findMatching(EntityAffiliationBo.class, criteria);
		assertEquals("number of affiliations differ", 1, results.size());
		EntityAffiliationBo affilBo = results.iterator().next();
		assertNotNull("affiliation type code should not be null", affilBo.getAffiliationTypeCode());
		assertEquals("affiliation code differs", expectedAffilCode, affilBo.getAffiliationTypeCode());
		assertTrue("affiliation should be active", affilBo.isActive());
		assertTrue("affiliation should be the default", affilBo.isDefaultValue());
		
		Collection<EntityNameBo> names = getBoSvc().findMatching(EntityNameBo.class, criteria);
		assertEquals("number of names differ", 1, names.size());
		
		final EntityNameBo entityName = names.iterator().next();
		assertTrue("name should be active",entityName.isActive());
		assertTrue("name should be default",entityName.isDefaultValue());
		return entityName;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.MartinlawTestsBase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		maintainable = new MatterMaintainable();
		idSvc = KimApiServiceLocator.getIdentityService();
	}
	
	/**
	 * tests {@link org.martinlaw.bo.MatterMaintainable#replacePrincipalName(MartinlawPerson, String)}
	 *//*
	@Test
	public void testReplacePrincipalName() {
		Client person = new Client();
		person.setPrincipalName("clerk of the senate");
		person.setMatterId(1001l);
		getBoSvc().save(person);
		
		final String principalName = "clerk22";
		maintainable.replacePrincipalName(person, principalName);
		
		Map<String, String> criteria = new HashMap<String, String>(); 
		criteria.put("principalName", principalName);
		assertEquals("principal name should have been replaced", 1,  getBoSvc().findMatching(Client.class, criteria).size());
	}
*/
}
