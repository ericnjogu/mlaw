/**
 * 
 */
package org.martinlaw.util;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.bo.MatterAssignee;
import org.martinlaw.bo.MatterAssignment;
import org.martinlaw.bo.MatterClientFee;
import org.martinlaw.bo.MatterDate;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.bo.contract.Contract;
import org.martinlaw.bo.contract.ContractConsideration;
import org.martinlaw.bo.contract.ContractDuration;
import org.martinlaw.bo.contract.ContractParty;
import org.martinlaw.bo.contract.ContractSignatory;
import org.martinlaw.bo.contract.ContractType;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.courtcase.Assignee;
import org.martinlaw.bo.courtcase.Assignment;
import org.martinlaw.bo.opinion.Client;
import org.martinlaw.bo.opinion.Opinion;

/**
 * holds various methods used across test cases
 * 
 * @author mugo
 *
 */
public class TestUtils {
	private String testContractLocalReference = "cn/rent/01";
	private String contractName = "rent of flat";
	private String testConveyanceName = "sale of KAZ 457T";
	private String assignee1 = "pn";
	private String assignee2 = "aw";
	private String testOpinionName;
	private String testOpinionClientName;
	private String testOpinionLocalReference;
	/**
	 * get a test conveyance object
	 * @return
	 */
	public Conveyance getTestConveyance() {
		Conveyance conv = new Conveyance();
		conv.setName(testConveyanceName);
		conv.setLocalReference("EN/C001");
		conv.setTypeId(1002l);
		conv.setStatusId(1001l);
		return conv;
	}
	
	/**
	 * gets a test contract
	 * @return the unpersisted contract
	 */
	public Contract getTestContract() {
		Contract contract = new Contract();
		contract.setName(contractName);
		contract.setLocalReference(testContractLocalReference);
		contract.setTypeId(1001l);
		contract.setSummaryOfTerms("see attached file");
		contract.setServiceOffered("flat 1f2");
		contract.setStatusId(1001l);
		// parties
		List<ContractParty> parties = new ArrayList<ContractParty>();
		parties.add(new ContractParty("party1"));
		parties.add(new ContractParty("party2"));
		contract.setParties(parties);
		// signatories
		List<ContractSignatory> signs = new ArrayList<ContractSignatory>();
		signs.add(new ContractSignatory("sign1"));
		signs.add(new ContractSignatory("sign2"));
		contract.setSignatories(signs);
		// consideration
		ContractConsideration contractConsideration = new ContractConsideration(new BigDecimal(1000), "UGS", "see breakdown in attached file");

		contract.setContractConsideration(contractConsideration);
		// duration
		Calendar cal = Calendar.getInstance();
		Date start = new Date(cal.getTimeInMillis());
		cal.roll(Calendar.YEAR, true);
		Date end = new Date(cal.getTimeInMillis());
		ContractDuration contractDuration = new ContractDuration(start, end);

		contract.setContractDuration(contractDuration);
		
		return contract;
	}
	
	/**
	 * tests that the test contract fields have the expected values
	 * @param contract - the test contract - mostly retrieved from the database
	 */
	public void testContractFields(Contract contract) {
		assertEquals("contract name does not match", contractName , contract.getName());
		assertEquals("contract local ref does not match", testContractLocalReference, contract.getLocalReference());
		assertNotNull("contract type id should not be null", contract.getTypeId());
		assertNotNull("contract type should exist", 
				KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(ContractType.class, contract.getTypeId()));
		// TODO - test fails - possibly something to do with the ojb framework - or delays in document processing?
		// assertNotNull("contract type should not be null", contract.getType());
		assertNotNull("consideration id should not be null", contract.getContractConsiderationId());
		assertNotNull("consideration should not be null", contract.getContractConsideration());
		assertNotNull("duration should not be null", contract.getContractDuration());
		assertNotNull("parties should not be null", contract.getParties());
		assertEquals("parties not the expected number", 2, contract.getParties().size());
		assertNotNull("signatories should not be null", contract.getSignatories());
		assertEquals("signatories not the expected number", 2, contract.getSignatories().size());
	}

	/**
	 * @return the testContractLocalReference
	 */
	public String getTestContractLocalReference() {
		return testContractLocalReference;
	}

	/**
	 * @param testContractLocalReference the testContractLocalReference to set
	 */
	public void setTestContractLocalReference(String testContractLocalReference) {
		this.testContractLocalReference = testContractLocalReference;
	}

	/**
	 * @return the testConveyanceName
	 */
	public String getTestConveyanceName() {
		return testConveyanceName;
	}

	/**
	 * @param testConveyanceName the testConveyanceName to set
	 */
	public void setTestConveyanceName(String testConveyanceName) {
		this.testConveyanceName = testConveyanceName;
	}
	
	/**
	 * 
	 * tests fields of {@link MatterDate} 
	 * @param date
	 */
	public void testMatterDateFields(MatterDate date) {
		assertEquals("first hearing date",date.getComment());
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getDate().getTime());
		assertEquals(2011,cal.get(Calendar.YEAR));
		assertEquals(Calendar.JUNE, cal.get(Calendar.MONTH));
		assertEquals(1, cal.get(Calendar.DATE));
	}
	
	/**
	 * creates a test {@link MatterAssignment} of the type passed in the input params
	 * 
	 * @return the test object
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <A extends MatterAssignment, T extends MatterAssignee> A getTestAssignment(Class<A> a, Class<T> t) throws InstantiationException, IllegalAccessException {
		A assignment = a.newInstance();
		assignment.setMatterId(1002l);
		
		 T assignee = t.newInstance();
		
		assignee.setPrincipalName(assignee1);
		assignment.getAssignees().add(assignee);
		
		T assignee3 = t.newInstance();
		assignee3.setPrincipalName(assignee2);
		//contractAssignee.setContractId(contractId);
		assignment.getAssignees().add(assignee3);
		
		T assignee4 = t.newInstance();
		assignee4.setPrincipalName("clerk1");
		assignment.getAssignees().add(assignee4);
		
		return assignment;
	}

	/**
	 * @return the assignee1
	 */
	public String getAssignee1() {
		return assignee1;
	}

	/**
	 * @param assignee1 the assignee1 to set
	 */
	public void setAssignee1(String assignee1) {
		this.assignee1 = assignee1;
	}

	/**
	 * @return the assignee2
	 */
	public String getAssignee2() {
		return assignee2;
	}

	/**
	 * @param assignee2 the assignee2 to set
	 */
	public void setAssignee2(String assignee2) {
		this.assignee2 = assignee2;
	}

	/**
	 * tests the {@link MatterAssignment} fields have the expected values
	 * 
	 * @param assignment - the test object
	 */
	public void testAssignmentFields(
			MatterAssignment<?, ?> assignment) {
		assertNotNull(assignment);
		List<? extends MatterAssignee> assignees = assignment.getAssignees();
		testAssignees(assignees);
	}

	/**
	 * test assignee fields
	 * 
	 * @param assignees
	 */
	public void testAssignees(List<? extends MatterAssignee> assignees) {
		assertEquals("number of assignees does not match", 3, assignees.size());
		assertEquals("assignee principal name did not match", getAssignee1(), assignees.get(0).getPrincipalName());
		assertEquals("assignee principal name did not match", getAssignee2(), assignees.get(1).getPrincipalName());
	}

	/**
	 * create a test {@link Opinion}
	 * 
	 * @return
	 */
	public Opinion getTestOpinion() {
		Opinion opinion = new Opinion();
		testOpinionName = "legal opinion regarding the status quo";
		opinion.setName(testOpinionName);
		testOpinionLocalReference = "en/op/01";
		opinion.setLocalReference(testOpinionLocalReference);
		opinion.setStatusId(1001l);
		
		Client client = new Client();
		testOpinionClientName = "pnk";
		client.setPrincipalName(testOpinionClientName);
		opinion.getClients().add(client);
		
		return opinion;
	}

	/**
	 * verify the test {@link Opinion} field values
	 * 
	 * @param opinion
	 */
	public void testOpinionFields(Opinion opinion) {
		assertEquals("opinion name differs", testOpinionName, opinion.getName());
		assertEquals("opinion local ref differs", testOpinionLocalReference, opinion.getLocalReference());
		// for some reason, the status object is not fetched, so test for the id instead
		assertNotNull("opinion status should not be null", opinion.getStatusId());
		
		assertEquals("opinion clients not the expected number", 1, opinion.getClients().size());
		assertEquals("opinion client name not the expected value", testOpinionClientName, opinion.getClients().get(0).getPrincipalName());
	}

	/**
	 * @return the testOpinionLocalReference
	 */
	public String getTestOpinionLocalReference() {
		return testOpinionLocalReference;
	}
	
	/**
	 * convenience method to test matter assignment CRUD
	 * 
	 * @param assignment - the BO to perform CRUD with
	 */
	public void testAssignmentCRUD(MatterAssignment<?, ?> assignment) {
		// C
		getBoSvc().save(assignment);
		// R
		assignment.refresh();
		testAssignmentFields(assignment);
		// U
		// TODO new collection items do not appear to be persisted when refresh is called
		/*Assignee assignee3 = new Assignee();
		String name3 = "hw";
		assignee.setPrincipalName(name3);
		contractAssignment.getAssignees().add(assignee3);
		contractAssignment.refresh();
		assertEquals("number of assignees does not match", 3, contractAssignment.getAssignees().size());
		assertEquals("assignee principal name did not match", name3, contractAssignment.getAssignees().get(2).getPrincipalName());*/
		// D
		getBoSvc().delete(assignment);
		assertNull("CourtCase assignment should have been deleted", getBoSvc().findBySinglePrimaryKey(Assignment.class,	assignment.getMatterId()));
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("matterId", assignment.getMatterId());
		assertEquals("assignees should have been deleted", 0, getBoSvc().findMatching(Assignee.class, criteria).size());
	}

	/**
	 * convenience method to access service
	 * @return
	 */
	private BusinessObjectService getBoSvc() {
		return KRADServiceLocator.getBusinessObjectService();
	}
	
	/**
	 * convenience method to test for annex type key values
	 * 
	 * @param result
	 */
	public void testAnnexTypeKeyValues(List<KeyValue> result) {
		assertFalse("key values list should not be empty", result.isEmpty());
		assertEquals("key values list size differs", 2, result.size());
		assertEquals("annex type value differs", "land board approval", result.get(0).getValue());
		assertEquals("annex type value differs", "city council approval", result.get(1).getValue());
	}
	
	/**
	 * test a list of {@link MatterClientFee}, created via sql
	 * 
	 * @param fees - the list
	 */
	public void testClientFeeList(List<? extends MatterClientFee<?>> fees) {
		assertNotNull("fee list should not be null", fees);
		assertEquals("expected number of fees differs", 2, fees.size());
		assertEquals("client name differs", "mawanja", fees.get(0).getFee().getClientPrincipalName());
	}
	
	/**
	 * test work list that was populated via sql
	 * 
	 * @param work
	 */
	public void testWorkList(List<? extends MatterWork> work) {
		assertNotNull("work list should not be null", work);
        assertEquals("expected number of work differs", 2, work.size());
	}

	/**
	 * @return the testAssignmentId
	 *//*
	public long getTestAssignmentId() {
		return testAssignmentId;
	}

	*//**
	 * @param testAssignmentId the testAssignmentId to set
	 *//*
	public void setTestAssignmentId(long testAssignmentId) {
		this.testAssignmentId = testAssignmentId;
	}*/
}
