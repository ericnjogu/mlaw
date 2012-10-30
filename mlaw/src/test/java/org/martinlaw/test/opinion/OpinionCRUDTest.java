/**
 * 
 */
package org.martinlaw.test.opinion;

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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.opinion.Opinion;
import org.martinlaw.bo.opinion.Client;
import org.martinlaw.bo.opinion.Fee;
import org.martinlaw.test.MartinlawTestsBase;

/**
 * various CRUD tests for {@link Opinion} and dependent objects
 * @author mugo
 *
 */
public class OpinionCRUDTest extends MartinlawTestsBase {

	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-fee-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-work-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/opinion-assignment-test-data.sql", ";").runSql();
	}
	
	/**
	 * tests that the data inserted via sql can be retrieved ok
	 */
	@Test
	public void testOpinionRetrieve() {
		Opinion opinion = getBoSvc().findBySinglePrimaryKey(Opinion.class, 1001l);
		assertNotNull("opinion should have been found", opinion);
		assertEquals("opinion name differs", "legal opinion regarding the sale of brown elephant", opinion.getName());
		
		assertNotNull("opinion status should not be null", opinion.getStatus());
		
		assertNotNull("opinion clients should not be null", opinion.getClients());
		assertEquals("opinion clients not the expected number", 2, opinion.getClients().size());
		assertEquals("opinion client name not the expected value", "client1", opinion.getClients().get(0).getPrincipalName());

		getTestUtils().testAssignees(opinion.getAssignees());
		
		getTestUtils().testClientFeeList(opinion.getFees());
		
		getTestUtils().testWorkList(opinion.getWork());
	}
	
	/**
	 * tests  {@link Opinion} CRUD
	 */
	@Test
	public void testOpinionCRUD() {
		// C
		Opinion opinion = getTestUtils().getTestOpinion();
		
		getBoSvc().save(opinion);
		
		// R
		opinion.refresh();
		getTestUtils().testOpinionFields(opinion);
		
		// U
		String summary = "see attached file";
		opinion.setSummary(summary);
		opinion.refresh();
		assertEquals("opinion summary differs", summary, opinion.getSummary());
		
		// D
		getBoSvc().delete(opinion);
		assertNull("opinion should not exist", getBoSvc().findBySinglePrimaryKey(Opinion.class, opinion.getId()));
		Map<String, Object> criteria = new HashMap<String, Object>(1);
		criteria.put("matterId", opinion.getId());
		assertEquals("opinion clients should have been deleted", 0, getBoSvc().findMatching(Client.class, criteria).size());
		assertEquals("opinion fees should have been deleted", 0, getBoSvc().findMatching(Fee.class, criteria).size());
	}
	
}
