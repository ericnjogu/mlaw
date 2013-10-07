/**
 * 
 */
package org.martinlaw.test;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.Status;
import org.martinlaw.bo.StatusScope;
import org.martinlaw.bo.contract.Contract;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.keyvalues.ScopedKeyValuesUif;
import org.springframework.dao.DataIntegrityViolationException;
import static org.mockito.Mockito.when;

/**
 * various unit tests for {@link Status}
 * 
 * @author mugo
 *
 */
public class StatusBOTest extends MartinlawTestsBase {
	@Test
	/**
	 * test that default annex types are retrieved ok
	 */
	public void testStatusRetrieve() {
		List<Status> caseStatuses = (List<Status>) getBoSvc().findAll(Status.class);
		assertNotNull("default status list should not be null", caseStatuses);
		assertEquals("number of default statuses differs", 5, caseStatuses.size());
		Status status = getBoSvc().findBySinglePrimaryKey(Status.class, new Long(1003));
		assertNotNull("status should not be null", status);
		assertEquals("status text differs", "closed", status.getStatus());
		assertTrue("no scope has been set", status.getScope().isEmpty());
		
		testRetrievedScopedStatus(new Long(1002), "hearing", 1,	CourtCase.class.getCanonicalName());
		testRetrievedScopedStatus(new Long(1004), "documents missing", 2, Conveyance.class.getCanonicalName());
	}

	/**
	 * test a retrieved status which has a scope set
	 * @param primaryKey - the pk to retrieve the status
	 * @param statusText - the status text/name
	 * @param scopeSize - the size of scope list
	 * @param firstScopeCanonicalName - the qualified class name to expect in the first scope
	 */
	public void testRetrievedScopedStatus(final Long primaryKey, final String statusText, final int scopeSize,
			final String firstScopeCanonicalName) {
		Status status;
		status = getBoSvc().findBySinglePrimaryKey(Status.class, primaryKey);
		assertEquals("status text differs", statusText, status.getStatus());
		assertFalse("status scope has been set", status.getScope().isEmpty());
		assertEquals("scope size differs", scopeSize, status.getScope().size());
		assertEquals("status first scope class name differs", firstScopeCanonicalName, 
				status.getScope().get(0).getQualifiedClassName());
	}
	
	@Test
	/**
	 * tests CRUD on annex type
	 */
	public void testStatusCRUD() {
		// create
		Status status = new Status();
		status.setStatus("pending");
		//test scopes
		StatusScope scope1 = new StatusScope();
		final String canonicalName1 = Matter.class.getCanonicalName();
		scope1.setQualifiedClassName(canonicalName1);
		status.getScope().add(scope1);
		StatusScope scope2 = new StatusScope();
		final String canonicalName2 = Contract.class.getCanonicalName();
		scope2.setQualifiedClassName(canonicalName2);
		status.getScope().add(scope2);
		
		getBoSvc().save(status);
		//refresh
		status.refresh();
		// retrieve
		assertEquals("the Status does not match", "pending", status.getStatus());
		assertFalse("scope should not be empty", status.getScope().isEmpty());
		assertEquals("scope size differs", 2, status.getScope().size());
		assertEquals("class name differs", canonicalName1, status.getScope().get(0).getQualifiedClassName());
		assertEquals("class name differs", canonicalName2, status.getScope().get(1).getQualifiedClassName());
		//update
		status.setStatus("appealed");
		status.getScope().remove(0);
		getBoSvc().save(status);
		//refresh
		status.refresh();
		assertEquals("the Status does not match", "appealed", status.getStatus());
		assertEquals("scope size differs", 1, status.getScope().size());
		assertEquals("class name differs", canonicalName2, status.getScope().get(0).getQualifiedClassName());
		// delete
		getBoSvc().delete(status);
		assertNull("status should have been deleted", getBoSvc().findBySinglePrimaryKey(Status.class, status.getId()));
		Map<String, String> criteria = new HashMap<String, String>();
		criteria.put("typeId", String.valueOf(status.getId()));
		assertTrue("scopes should have been deleted", getBoSvc().findMatching(StatusScope.class, criteria).isEmpty());
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * tests that annex type generates errors when non-nullable fields are blank
	 */
	public void testStatusNullableFields() {
		Status status = new Status();
		status.setId(25l);
		getBoSvc().save(status);
	}
	
	@Test
	/**
	 * test that status type key values returns the correct number
	 */
	public void testMatterStatusKeyValues() {
		MaintenanceDocumentForm form = getTestUtils().createMockMaintenanceDocForm();
		
		
		ScopedKeyValuesUif kv = new ScopedKeyValuesUif();
		kv.setScopedClass(Status.class);
		
		Maintainable newMaintainableObject = form.getDocument().getNewMaintainableObject();
		when(newMaintainableObject.getDataObject()).thenReturn(new CourtCase());
		String comment = "expected 3 statuses with court case scope and two that apply to all (empty)";
		assertEquals(comment, 5, kv.getKeyValues(form).size());
		
		when(newMaintainableObject.getDataObject()).thenReturn(new Contract());
		comment = "expected the two that apply to all (empty)";
		assertEquals(comment, 2, kv.getKeyValues(form).size());
		
		// TODO  adapt for matter
		/*comment = "expected the two that apply to all (empty), plus a blank one";
		getTestUtils().testMatterStatusKeyValues(new OpinionStatusKeyValues(), comment, 3);*/
		
		when(newMaintainableObject.getDataObject()).thenReturn(new Conveyance());
		comment = "expected one status with conveyance scope, the two that apply to all (empty)";
		assertEquals(comment, 3, kv.getKeyValues(form).size());
	}
	
	@Test
	/**
	 * test that the {@link Status} is loaded into the data dictionary
	 */
	public void testStatusAttributes() {
		testBoAttributesPresent(Status.class.getCanonicalName());
		Class<Status> dataObjectClass = Status.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}
}
