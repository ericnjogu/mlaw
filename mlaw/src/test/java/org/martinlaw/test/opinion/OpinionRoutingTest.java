/**
 * 
 */
package org.martinlaw.test.opinion;

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
import org.martinlaw.bo.opinion.Opinion;
import org.martinlaw.test.KewTestsBase;
import org.martinlaw.util.SearchTestCriteria;

/**
 * tests routing for {@link Opinion}
 * @author mugo
 *
 */
public class OpinionRoutingTest extends KewTestsBase {
	private Log log = LogFactory.getLog(getClass());

	@Test
	/**
	 * test that OpinionMaintenanceDocument routes to clerk then lawyer on submit
	 */
	public void testOpinionRouting() {
		Opinion testOpinion = getTestUtils().getTestOpinion();
		try {
			testMaintenanceRoutingInitToFinal(getDocTypeName(), testOpinion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("test failed", e);
			fail("test routing OpinionMaintenanceDocument caused an exception");
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("localReference", getTestUtils().getTestOpinionLocalReference());
		Collection<Opinion> result = getBoSvc().findMatching(Opinion.class, params);
		assertEquals(1, result.size());
		for (Opinion opinion: result) {
			getTestUtils().testOpinionFields(opinion);
		}
	}
	
	@Test
	/**
	 * test that a opinion maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	public void testOpinionTypeMaintDocPerms() {
		testCreateMaintain(Opinion.class, getDocTypeName());
	}
	
	@Test
	/**
	 * test that OpinionMaintenanceDocument routes to clerk then lawyer on submit
	 */
	public void testOpinionDocSearch() throws WorkflowException, InstantiationException, IllegalAccessException {
		Opinion testOpinion = getTestUtils().getTestOpinion();
		final String docType = getDocTypeName();
		testMaintenanceRoutingInitToFinal(docType, testOpinion);
		
		Opinion testOpinion2 = getTestUtils().getTestOpinion();
		testOpinion2.setLocalReference("MY/FIRM/OPINIONS/2014/012");
		//testOpinion2.getConsiderations().add(new Consideration(new BigDecimal(45000), "BGP", null));
		testMaintenanceRoutingInitToFinal(docType, testOpinion2);
		
		SearchTestCriteria crit1 = new SearchTestCriteria();
		crit1.setExpectedDocuments(2);
		// search for local reference
		SearchTestCriteria crit2 = new SearchTestCriteria();
		crit2.setExpectedDocuments(1);
		crit2.getFieldNamesToSearchValues().put("localReference", testOpinion.getLocalReference());
		
		List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
		crits.add(crit1);
		crits.add(crit2);
		getTestUtils().runDocumentSearch(crits, docType);
	}

	public String getDocTypeName() {
		return "OpinionMaintenanceDocument";
	}
}
