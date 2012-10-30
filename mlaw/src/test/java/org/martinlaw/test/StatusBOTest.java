/**
 * 
 */
package org.martinlaw.test;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012 Eric Njogu (kunadawa@gmail.com)
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
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.Status;
import org.martinlaw.keyvalues.CourtCaseStatusKeyValues;
import org.springframework.dao.DataIntegrityViolationException;

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
		assertNotNull(caseStatuses);
		assertEquals(5, caseStatuses.size());
		Status status = getBoSvc().findBySinglePrimaryKey(Status.class, new Long(1003));
		assertNotNull(status);
		assertEquals("closed", status.getStatus());
		assertEquals(Status.ANY_TYPE.getKey(), status.getType());
	}
	
	@Test
	/**
	 * tests CRUD on annex type
	 */
	public void testStatusCRUD() {
		// create
		Status status = new Status();
		status.setStatus("pending");
		status.setType(Status.ANY_TYPE.getKey());
		getBoSvc().save(status);
		//refresh
		status.refresh();
		// retrieve
		assertEquals("the Status does not match", "pending", status.getStatus());
		//update
		status.setStatus("appealed");
		getBoSvc().save(status);
		//refresh
		status.refresh();
		assertEquals("the Status does not match", "appealed", status.getStatus());
		// delete
		getBoSvc().delete(status);
		assertNull(getBoSvc().findBySinglePrimaryKey(Status.class, status.getId()));
	}
	
	/**
	 * convenience method to retrieve and verify the value of a CourtCaseStatus
	 * @param id - the CourtCaseStatus id
	 * @param value - the expected value
	 * @return - the retrieved CourtCaseStatus
	 */
	protected Status retrieveandVerifyCourtCaseStatusValue(long id, String value) {
		Status CourtCaseStatusRetrieved = null;
		CourtCaseStatusRetrieved = getBoSvc().findBySinglePrimaryKey(Status.class, id);
		assertNotNull("the new annex type should have been saved to the db", CourtCaseStatusRetrieved);
		assertEquals("the annex type value does not match", value, CourtCaseStatusRetrieved.getStatus());
		return CourtCaseStatusRetrieved;
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
	 * test that court case status type key values returns the correct number
	 */
	public void testCourtCaseStatusKeyValues() {
		CourtCaseStatusKeyValues keyValues = new CourtCaseStatusKeyValues();
		// expected 2 court case types and two of any type, plus a blank one
		assertEquals(5, keyValues.getKeyValues().size());
	}	
	
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
	}
}
