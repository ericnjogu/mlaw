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
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.EventType;
import org.martinlaw.bo.MatterAssignee;
import org.martinlaw.bo.MatterAssignment;
import org.martinlaw.bo.MatterClientFee;
import org.martinlaw.bo.MatterConsideration;
import org.martinlaw.bo.MatterEvent;
import org.martinlaw.bo.MatterEventTest;
import org.martinlaw.bo.MatterFee;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.bo.Status;
import org.martinlaw.bo.contract.Contract;
import org.martinlaw.bo.contract.ContractConsideration;
import org.martinlaw.bo.contract.ContractDuration;
import org.martinlaw.bo.contract.ContractParty;
import org.martinlaw.bo.contract.ContractSignatory;
import org.martinlaw.bo.contract.ContractType;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.conveyance.ConveyanceAnnexType;
import org.martinlaw.bo.conveyance.ConveyanceType;
import org.martinlaw.bo.courtcase.Assignee;
import org.martinlaw.bo.courtcase.Assignment;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.bo.courtcase.Event;
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
		conv.setName(getTestConveyanceName());
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
		ContractConsideration contractConsideration = new ContractConsideration(
				new BigDecimal(1000), "UGS", "see breakdown in attached file");

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
		assertNotNull("contract type should not be null", contract.getType());
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
	 * tests fields of {@link MatterEvent} from sql data
	 * @param event
	 */
	public void testRetrievedMatterEventFields(MatterEvent<?> event) {
		assertNotNull("event should not be null", event);
		assertEquals("first hearing date",event.getComment());
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(event.getStartDate().getTime());
		assertEquals("event year differs", 2011,cal.get(Calendar.YEAR));
		assertEquals("event month differs", Calendar.JUNE, cal.get(Calendar.MONTH));
		assertEquals("event date differs", 1, cal.get(Calendar.DATE));
		assertEquals("location differs", "nakuru", event.getLocation());
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
		assertNotNull("opinion status id should not be null", opinion.getStatusId());
		assertNotNull("opinion status should not be null", opinion.getStatus());
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
	 * test that the {@link MatterConsideration} has the expected field values
	 * @param consideration - the test object (created in code then saved to db)
	 */
	public void testConsiderationFields(MatterConsideration consideration) {
		assertEquals("consideration amount differs", 0, consideration.getAmount().compareTo(new BigDecimal(1000)));
		String desc = "see breakdown in attached spreadsheet";
		assertEquals("consideration description differs", desc, consideration.getDescription());
		assertEquals("consideration currency differs", "KES", consideration.getCurrency());
	}
	
	/**
	 * test that the  {@link MatterConsideration} has the expected field values
	 * @param consideration - the test object - retrieved (from db)
	 */
	public void testRetrievedConsiderationFields(MatterConsideration consideration) {
		assertEquals("consideration amount differs", 0, consideration.getAmount().compareTo(new BigDecimal(41000)));
		String desc = "to be paid in 2 installments";
		assertEquals("consideration description differs", desc, consideration.getDescription());
		assertEquals("consideration currency differs", "TZS", consideration.getCurrency());
	}
	
	/**
	 * users authorised by permissions
	 *  
	 * @return a list of principal names (key) and the corresponding boolean value of the authorization
	 */
	public Map<String, Boolean> getAuthUsers() {
		Map<String, Boolean> principalAuth = new HashMap<String, Boolean>();
		principalAuth.put("clerk1", true);
		principalAuth.put("lawyer1", true);
		principalAuth.put("witness1", false);
		principalAuth.put("client1", false);
		return principalAuth;
	}
	
	/**
	 * populates a fee with test data
	 * 
	 * @param fee - the fee to populate, one of the several {@code MatterFee} descendants
	 */
	public MatterFee populateMatterFeeData(MatterFee fee) {
		fee.setAmount(new BigDecimal(2000l));
		String clientPrincipalName = "pkk";
		fee.setClientPrincipalName(clientPrincipalName);
		fee.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
		return fee;
	}
	
	/**
	 * sets test data to the required fields
	 * 
	 * @param newDocument the document to populate
	 * @return the populated document
	 */
	public MatterWork populateMatterWork(MatterWork newDocument) {
		newDocument.setMatterId(1001l);
		newDocument.getDocumentHeader().setDocumentDescription("testing");
		return newDocument;
	}
	
	/**
	 * populate a descendant of {@code MatterClientFee} with test data
	 * @param doc - the doc to be populated
	 * @param fee - the descendant of {@code MatterFee} to be populated
	 * @return - the populated doc
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MatterClientFee populateClientFee(MatterClientFee doc, MatterFee fee) {
		doc.getDocumentHeader().setDocumentDescription("testing");
		doc.setMatterId(1001l);
		doc.setFee(populateMatterFeeData(fee));
		return doc;
	}

	/**
	 * create and populate a matter date for testing
	 * @param d
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public <D extends MatterEvent<?>> D getTestMatterEvent(Class<D> d) throws InstantiationException, IllegalAccessException {
		D date = d.newInstance();
		date.setComment("must attend");
		date.setMatterId(1001l);
		date.setTypeId(1001l);
		date.setStartDate(new Timestamp(System.currentTimeMillis()));
		date.setLocation("makadara");
		return date;
	}
	
	/**
	 * tests CRUD for descendants of {@link MatterEvent}
	 * @param event - the object to test with
	 * @param matterEvent - the type of {@code MatterEvent} for use in fetching from {@code #getBoSvc()}
	 */
	public <D extends MatterEvent<?>> void testMatterEventCRUD(MatterEvent<?> event, Class<D> matterEvent) {
		// C
		final Timestamp ts1 = new Timestamp(System.currentTimeMillis());
		event.setDateModified(ts1);
		getBoSvc().save(event);
		// R
		event.refresh(); // get the created pk
		// for some reason, the date modified remains null even after a refresh, so re-fetch
		event = getBoSvc().findBySinglePrimaryKey(event.getClass(), event.getId());
		assertEquals("comment differs", "must attend", event.getComment());
		SimpleDateFormat sdf =  new SimpleDateFormat("dd-MM-yy");
		assertEquals(sdf.format(Calendar.getInstance().getTime()), sdf.format(event.getStartDate()));
		// test for default date values
		assertNotNull("date created should have a default value", event.getDateCreated());
		assertNotNull("date modified should have a default value", event.getDateModified());
		
		// U
		final String comment = "must attend - dept heads only";
		event.setComment(comment);
		final Timestamp ts2 = new Timestamp(System.currentTimeMillis());
		event.setDateModified(ts2);
		assertTrue("for the update test to work, the initial and subsequent timestamps should be different", ts2.after(ts1));
		//date.refresh();
		getBoSvc().save(event);
		event = getBoSvc().findBySinglePrimaryKey(event.getClass(), event.getId());
		assertEquals("comment differs", comment, event.getComment());
		//confirm that date modified has changed
		// for some reason, the date modification test fails
		assertTrue("date should have been updated", event.getDateModified().after(ts1));
		// D
		getBoSvc().delete(event);
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("comment", comment);
		assertEquals("date should have been deleted", 0, getBoSvc().findMatching(matterEvent, map).size());
	}
	
	/**
	 * get a populated test court case
	 * @param localRef - the local reference to set
	 * @param courtRef - the court number to set
	 * @return the test object
	 */
	public CourtCase getTestCourtCase(String localRef, String courtRef) {
		//set up test status
		Status status = new Status();
		status.setStatus("Testing");
		status.setType(Status.ANY_TYPE.getKey());
		getBoSvc().save(status);
		status.refresh();
		assertNotNull(status.getId());
		//create new case bo
		CourtCase caseBo = new CourtCase();
		
		caseBo.setLocalReference(localRef);
		caseBo.setCourtReference(courtRef);
		caseBo.setName("Flesh Vs Spirit (Lifetime)");
		caseBo.setStatus(status);
		// side step validation error - error.required
		caseBo.setStatusId(status.getId());
		
		return caseBo;
	}
	
	/**
	 * get a test conveyance type object
	 * @return the test object
	 */
	public ConveyanceType getTestConveyanceType() {
		ConveyanceType convType = new ConveyanceType();
		String name = "auction";
		convType.setName(name);
		// set annex types
		List<ConveyanceAnnexType> annexTypes = new ArrayList<ConveyanceAnnexType>();
		ConveyanceAnnexType convAnnexType = new ConveyanceAnnexType();
		convAnnexType.setName("signed affidavit");
		annexTypes.add(convAnnexType);
		convAnnexType = new ConveyanceAnnexType();
		convAnnexType.setName("title deed");
		annexTypes.add(convAnnexType);
		convType.setAnnexTypes(annexTypes);
		return convType;
	}
	
	/**
	 * creates a test object of type {@link MatterEvent} containing info useful for testing templating
	 * @see org.martinlaw.bo.MatterEventTest#testToIcalendar() 
	 * @see org.martinlaw.bo.MatterEventMaintainableTest#testCreateNotificationMessage()
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public MatterEvent getTestMatterEventForStringTemplates() {
		Event caseEvent = new Event();
		caseEvent.setId(1001l);
		EventType eventType = new EventType();
		eventType.setName("judgement");
		caseEvent.setType(eventType);
		
		CourtCase theCase = new CourtCase();
		theCase.setLocalReference("my/cases/1");
		theCase.setName("water vs fire");
		caseEvent.setMatter(theCase);
		
		// create test date
		Calendar testCal = Calendar.getInstance();
		testCal.set(Calendar.YEAR, 2013);
		testCal.set(Calendar.MONTH, 1);// months start at 0
		testCal.set(Calendar.DATE, 22);
		testCal.set(Calendar.HOUR_OF_DAY, 11);
		testCal.set(Calendar.MINUTE, 19);
		testCal.set(Calendar.SECOND, 15);
		
		Date testDate = new Date(testCal.getTimeInMillis());
		
		caseEvent.setStartDate(new Timestamp(testDate.getTime()));
		Timestamp ts = new Timestamp(testCal.getTimeInMillis());
		caseEvent.setDateCreated(ts);
		caseEvent.setDateModified(ts);
		caseEvent.setLocation("milimani");
		return caseEvent;
	}
	
	/**
	 * gets a notification request in xml populated with test values
	 * @return the xml
	 * @throws IOException
	 */
	public String getTestNotificationXml() throws IOException {
		String template = IOUtils.toString(MatterEventTest.class.getResourceAsStream("event-notfn-template.xml"));
		Event caseDate = (Event) getTestMatterEventForStringTemplates();
		final String notificationXML = caseDate.toNotificationXML(template, MartinlawConstants.NotificationTemplatePlaceholders.CALENDAR_CHANNEL_NAME, 
				MartinlawConstants.NotificationTemplatePlaceholders.CALENDAR_PRODUCER_NAME, 
				"May you prosper and be in good health.");
		return notificationXML;
	}
}
