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


import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.martinlaw.bo.MatterAssignment;
import org.martinlaw.util.SearchTestCriteria;

import static org.junit.Assert.assertNotNull;;

public abstract class BaseAssignmentRoutingTest extends KewTestsBase {
	private Log log = LogFactory.getLog(getClass());

	/**
	 * default constructor
	 */
	public BaseAssignmentRoutingTest() {
		super();
	}
	
	/**
	 * test routing for a child of {@link MatterAssignment}
	 * @param testAssignment - the BO to use in the maintenance doc
	 * @param docType -  the doc type to use
	 */
	public void testAssignmentRouting(MatterAssignment<?, ?> testAssignment, String docType) {
		// Assignment testAssignment = getTestUtils().getTestContractAssignment();
		try {
			testMaintenanceRoutingInitToFinal(docType, testAssignment);
		} catch (Exception e) {
			log .error("test failed", e);
			fail("test routing " + docType + " caused an exception");
		}
		// confirm that BO was saved to DB
		@SuppressWarnings("rawtypes")
		MatterAssignment result = getBoSvc().findBySinglePrimaryKey(testAssignment.getClass(), testAssignment.getMatterId());
		assertNotNull("assignment should have been persisted", result);
		getTestUtils().testAssignmentFields(result);

	
	}

	/**
	 * run document search for the custom fields exposed in assignment documents
	 * @param docType
	 */
	public void runAssignmentDocumentSearch(final String docType, final String localRef,
			final String name) {
				// no document criteria given, so both documents should be found
				SearchTestCriteria crit1 = new SearchTestCriteria();
				crit1.setExpectedDocuments(2);
				// search for local reference
				SearchTestCriteria crit2 = new SearchTestCriteria();
				crit2.setExpectedDocuments(1);
				crit2.getFieldNamesToSearchValues().put("matter.localReference", localRef);
				// search for name
				SearchTestCriteria crit3 = new SearchTestCriteria();
				crit3.setExpectedDocuments(1);
				crit3.getFieldNamesToSearchValues().put("matter.name", name);
				
				List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
				crits.add(crit1);
				crits.add(crit2);
				crits.add(crit3);
				runDocumentSearch(crits, docType);
			}
}