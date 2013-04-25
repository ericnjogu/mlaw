package org.martinlaw.test;

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

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.exception.ValidationException;
import org.martinlaw.bo.MatterConsideration;
import org.martinlaw.util.SearchTestCriteria;
/**
 * holds common routing tests for children of {@link MatterConsideration}
 * @author mugo
 *
 */
public abstract class MatterConsiderationRoutingTestBase extends KewTestsBase {

	protected Log log = LogFactory.getLog(getClass());

	/**
	 * test that the maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	@Test
	public void testMatterConsiderationMaintDocPerms() {
		testCreateMaintain(getDataObjectClass(), getDocType());
	}

	/**
	 * tests maintenance doc routing
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws WorkflowException 
	 * 
	 */
	@Test
	public void testMatterConsiderationRouting() throws InstantiationException,
			IllegalAccessException, WorkflowException {
		MatterConsideration<?> consideration = getTestUtils().getTestConsideration(getDataObjectClass());
		this.testMaintenanceRoutingInitToFinal(getDocType(), consideration);
	}

	/**
	 * verifies that the business rules create an error for a non existent court case id on save
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws WorkflowException
	 */
	@Test(expected = ValidationException.class)
	public void testMatterConsiderationRouting_InvalidMatterId()
			throws InstantiationException, IllegalAccessException,
			WorkflowException {
		MatterConsideration<?> consideration = getTestUtils().getTestConsideration(getDataObjectClass());
		consideration.setMatterId(3000l);
		
		/*//initiate as the clerk
		Document doc = getPopulatedMaintenanceDocument(getDocType(), testDate, "clerk1");
		KRADServiceLocatorWeb.getDocumentService().saveDocument(doc);*/
		testMaintenanceRoutingInitToFinal(getDocType(), consideration);
	}

	/**
	 * verifies that the required fields are validated on route not save
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws WorkflowException
	 */
	@Test
	public void testMatterConsiderationRouting_required_validated_onroute()
			throws InstantiationException, WorkflowException,
			IllegalAccessException {
		MatterConsideration<?> consideration = getTestUtils().getTestConsideration(getDataObjectClass());
		// required on route
		consideration.setAmount(null);
		consideration.setCurrency(null);
		
		//initiate as the clerk
		Document doc = getPopulatedMaintenanceDocument(getDocType(), consideration, "clerk1");
		testRouting_required_validated_onroute(doc);
	}

	@Test
	public void testMatterConsideration_doc_search() {
		try {
			// route 2 docs first
			MatterConsideration<?> consideration1 = getTestUtils().getTestConsideration(getDataObjectClass());
			final String docType = getDocType();
			testMaintenanceRoutingInitToFinal(docType, consideration1);
			
			MatterConsideration<?> consideration2 = getTestUtils().getTestConsideration(getDataObjectClass());
			consideration2.setAmount(new BigDecimal(7500));
			consideration2.setConsiderationTypeId(1003l);
			testMaintenanceRoutingInitToFinal(docType, consideration2);
			
			
			// blank document search
			SearchTestCriteria crit1 = new SearchTestCriteria();
			crit1.setExpectedDocuments(2);
			// search description
			SearchTestCriteria crit2 = new SearchTestCriteria();
			crit2.setExpectedDocuments(2);
			crit2.getFieldNamesToSearchValues().put("description", "*attach*");
			// search for consideration amount
			SearchTestCriteria crit3 = new SearchTestCriteria();
			crit3.setExpectedDocuments(0);
			crit3.getFieldNamesToSearchValues().put("amount", ">8000");
			// search for consideration amount range
			SearchTestCriteria crit4 = new SearchTestCriteria();
			crit4.setExpectedDocuments(2);
			crit4.getFieldNamesToSearchValues().put("amount", "1000 .. 7500");
			// search for consideration amount
			SearchTestCriteria crit5 = new SearchTestCriteria();
			crit5.setExpectedDocuments(1);
			crit5.getFieldNamesToSearchValues().put("amount", "1000");
			// search for consideration type
			SearchTestCriteria crit6 = new SearchTestCriteria();
			crit6.setExpectedDocuments(1);
			crit6.getFieldNamesToSearchValues().put("considerationTypeId", "1003");
			
			List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
			crits.add(crit1);
			crits.add(crit2);
			crits.add(crit3);
			crits.add(crit4);
			crits.add(crit5);
			crits.add(crit6);
			getTestUtils().runDocumentSearch(crits, docType);
		} catch (Exception e) {
			log.error("test failed", e);
			fail("exception occurred");
		}
	}
	
	/**
	 * 
	 * @return the doc type being tested
	 */
	public String getDocType() {
		return "ConsiderationMaintenanceDocument";
	}
	
	/**
	 * 
	 * @return the data object (BO) class
	 */
	public abstract Class<? extends MatterConsideration<?>> getDataObjectClass();

}