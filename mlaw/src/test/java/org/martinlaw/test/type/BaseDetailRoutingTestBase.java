package org.martinlaw.test.type;

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
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.martinlaw.bo.BaseDetail;
import org.martinlaw.test.KewTestsBase;
import org.martinlaw.test.TestBoInfo;
import org.martinlaw.util.SearchTestCriteria;

/**
 * holds the common routing tests for children of {@link BaseDetail}
 * @author mugo
 *
 */
public abstract class BaseDetailRoutingTestBase extends KewTestsBase implements TestBoInfo {

	private Log log = LogFactory.getLog(getClass());

	public BaseDetailRoutingTestBase() {
		super();
	}

	@Test
	public void testBaseDetailRouting() throws InstantiationException, IllegalAccessException {
		BaseDetail type = getDataObject();
		try {
			testMaintenanceRoutingInitToFinal(getDocTypeName(), type);
		} catch (Exception e) {
			log .error("test failed", e);
			fail("test routing " + getDocTypeName() + " caused an exception");
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", type.getName());
		Collection<? extends BaseDetail> result = getBoSvc().findMatching(getDataObjectClass(), params);
		assertEquals(1, result.size());
	}
	
	/**
	 * allow superclasses to populate data objects
	 * @return a populated data object
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	protected BaseDetail getDataObject() throws InstantiationException, IllegalAccessException {
		BaseDetail type = getDataObjectClass().newInstance();
		String name = "resale agreement";
		type.setName(name);
		return type;
	}

	@Test
	public void testBaseDetailMaintDocPerms() {
		testCreateMaintain(getDataObjectClass(), getDocTypeName());
	}

	/**
	 * test that ContractTypeDocument doc search works
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws WorkflowException 
	 */
	@Test
	public void testBaseDetailRoutingDocSearch() throws WorkflowException,
		InstantiationException, IllegalAccessException {
		BaseDetail type = getDataObjectClass().newInstance();
		type.setName("permanent for testing purposes");
		final String docType = getDocTypeName();
		testMaintenanceRoutingInitToFinal(docType, type);
		
		BaseDetail type2 = getDataObjectClass().newInstance();
		type2.setName("supply of rain and shine");
		testMaintenanceRoutingInitToFinal(docType, type2);
		
		// no document criteria given, so both documents should be found
		SearchTestCriteria crit1 = new SearchTestCriteria();
		crit1.setExpectedDocuments(2);
		// search for name
		SearchTestCriteria crit2 = new SearchTestCriteria();
		crit2.setExpectedDocuments(1);
		crit2.getFieldNamesToSearchValues().put("name", "permanent*");
		// search for non-existent name
		SearchTestCriteria crit3 = new SearchTestCriteria();
		crit3.setExpectedDocuments(0);
		crit3.getFieldNamesToSearchValues().put("name", "*temp*");
		
		List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
		crits.add(crit1);
		crits.add(crit2);
		crits.add(crit3);
		getTestUtils().runDocumentSearch(crits, docType);
	}


	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#testInitiatorFYI()
	 */
	/**
	 * most {@link BaseDetail} objects do not need routing so skip this test
	 */
	@Override
	public void testInitiatorFYI() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#getDataObjectClass()
	 */
	@Override
	public Class<? extends BaseDetail> getDataObjectClass() {
		return null;
	}

}