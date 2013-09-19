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


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.martinlaw.bo.MatterAssignee;
import org.martinlaw.util.SearchTestCriteria;

public class MatterAssigneeRoutingTest extends KewTestsBase {
	private Log log = LogFactory.getLog(getClass());

	/**
	 * default constructor
	 */
	public MatterAssigneeRoutingTest() {
		super();
	}
	
	/**
	 * test routing for {@link MatterAssignee}
	 */
	public void testAssigneeRouting() {
		MatterAssignee testAssignee = getTestUtils().getTestAssignee();
		try {
			testMaintenanceRoutingInitToFinal(getDocTypeName(), testAssignee);
		} catch (Exception e) {
			log .error("test failed", e);
			fail("test routing " + getDocTypeName() + " caused an exception");
		}
		// confirm that BO was saved to DB
		MatterAssignee result = getBoSvc().findBySinglePrimaryKey(testAssignee.getClass(), testAssignee.getMatterId());
		assertNotNull("assignment should have been persisted", result);
		getTestUtils().testAssigneeFields(result);

	
	}

	/**
	 * run document search for the custom fields exposed in assignment documents
	 * @param docType
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws WorkflowException 
	 */
	public void runAssignmentDocumentSearch() throws WorkflowException, InstantiationException, IllegalAccessException {
		MatterAssignee testAssignee = getTestUtils().getTestAssignee();
		testAssignee.setActive(false);
		testMaintenanceRoutingInitToFinal(getDocTypeName(), testAssignee);
		
		MatterAssignee testAssignee2 = getTestUtils().getTestAssignee();
		testAssignee2.setMatterId(1003l);
		testAssignee2.setHasPhysicalFile(true);
		final String principalName = "eric";
		testAssignee2.setPrincipalName(principalName);
		testMaintenanceRoutingInitToFinal(getDocTypeName(), testAssignee2);
		
		String localRef = "EN/CN/002";
		String name = "*supply*";
		// test for active, hasphysical file
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
		// search using has physical file
		SearchTestCriteria crit4 = new SearchTestCriteria();
		crit4.setExpectedDocuments(1);
		crit4.getFieldNamesToSearchValues().put("hasPhysicalFile", "Y");
		// search using active
		SearchTestCriteria crit5 = new SearchTestCriteria();
		crit5.setExpectedDocuments(1);
		crit5.getFieldNamesToSearchValues().put("active", "Y");
		// search using principal name
		SearchTestCriteria crit6 = new SearchTestCriteria();
		crit6.setExpectedDocuments(1);
		crit6.getFieldNamesToSearchValues().put("principalName", principalName);
		
		
		List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
		crits.add(crit1);
		crits.add(crit2);
		crits.add(crit3);
		crits.add(crit4);
		crits.add(crit5);
		crits.add(crit6);
		getTestUtils().runDocumentSearch(crits, getDocTypeName());
	}

	/**
	 * test permissions
	 *//*
	@Test
	public void testMatterAssigneeMaintDocPerms() {
		testCreateMaintain(MatterAssignee.class, getDocTypeName());
	}
*/
	@Override
	public String getDocTypeName() {
		return "MatterAssigneeMaintenanceDocument";
	}

	@Override
	public Class<?> getDataObjectClass() {
		return MatterAssignee.class;
	}
}