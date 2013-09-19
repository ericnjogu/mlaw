package org.martinlaw.test.courtcase;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012, 2013 Eric Njogu (kunadawa@gmail.com)
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
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.bo.courtcase.CourtCaseType;
import org.martinlaw.bo.courtcase.CourtCaseWitness;
import org.springframework.dao.DataIntegrityViolationException;

public abstract class CourtCaseBoTestBase extends MatterBoTest {

	private String caseTypeName  = "petition";
	private String courtReference = "Mutomo Magistrates Court Petition No. 1 of 2013";

	public CourtCaseBoTestBase() {
		super();
	}
	
	/**
	 * CRUD test for {@link CourtCaseWitness}
	 */
	@Test
	public void testCaseWitness() {
		CourtCaseWitness person = new CourtCaseWitness();
		person.setCourtCaseId(1001l);
		testMartinlawPersonCRUD(CourtCaseWitness.class, "witness1", person);
	}

	/**
	 * test that required db fields for {@link CourtCaseWitness} are checked
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testCaseWitnessNullableFields() {
		CourtCaseWitness caseClient = new CourtCaseWitness();
		getBoSvc().save(caseClient);
	}

	/**
	 * test DD configuration for {@link CourtCaseWitness}
	 */
	@Test
	public void testWitnessAttributes() {
		testBoAttributesPresent(CourtCaseWitness.class.getCanonicalName());
		Class<CourtCaseWitness> dataObjectClass = CourtCaseWitness.class;
		verifyInquiryLookup(dataObjectClass);
	}


	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.MatterBoTest#testRetrievedMatterFields(org.martinlaw.bo.Matter)
	 */
	@Override
	public void testRetrievedMatterFields(Matter matter) {
		CourtCase kase = (CourtCase)matter;
		super.testRetrievedMatterFields(kase);
		
		assertEquals("court ref differs", "c1", kase.getCourtReference());
		   
	    // witness
	    List<CourtCaseWitness> witnesses = kase.getWitnesses();
	    assertEquals(1, witnesses.size());
	    CourtCaseWitness witness = witnesses.get(0);
	    assertEquals("witness principal name differs", "witness1", witness.getPrincipalName());
	    assertEquals("witness first name differs", "Witness",witness.getPerson().getFirstName());
	    
	    // type
	    assertNotNull("case type should not be null", kase.getType());
	    assertEquals("case type id differs", new Long(10001), kase.getType().getId());   
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.MatterBoTest#getTestMatter(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected Matter getTestMatter(String localReference, String statusText,
			String matterName)
			throws InstantiationException, IllegalAccessException {
		CourtCase matter = (CourtCase) super.getTestMatter(localReference, statusText, matterName);
		CourtCaseType type = new CourtCaseType();
		type.setName(caseTypeName);
		getBoSvc().save(type);
		type.refresh();
		matter.setTypeId(type.getId());
		matter.setCourtReference(courtReference);
		getTestUtils().addWitnesses(matter);
		
		return matter;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.MatterBoTest#additionalTestsForCreatedMatter(org.martinlaw.bo.Matter)
	 */
	@Override
	public void additionalTestsForCreatedMatter(Matter matter) {
		super.additionalTestsForCreatedMatter(matter);
		
		CourtCase kase = (CourtCase)matter;
		assertNotNull("witnesses should not be null", kase.getWitnesses());
		assertEquals("number of witnesses expected differs", 1, kase.getWitnesses().size());
		
		assertEquals("court ref differs", courtReference, kase.getCourtReference());
		assertEquals("case type name differs", caseTypeName, kase.getType().getName());
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.MatterBoTest#additionalTestsForDeletedMatter(org.martinlaw.bo.Matter)
	 */
	@Override
	public void additionalTestsForDeletedMatter(Matter matter) {
		super.additionalTestsForDeletedMatter(matter);
		
		assertNull("witness should have been deleted", getBoSvc().findBySinglePrimaryKey(CourtCaseWitness.class, ((CourtCase)matter).getWitnesses().get(0).getId()));
	}

}