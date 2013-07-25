/**
 * 
 */
package org.martinlaw.util;

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
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.directory.shared.ldap.util.ReflectionToStringBuilder;
import org.joda.time.DateTime;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kew.api.document.search.DocumentSearchCriteria;
import org.kuali.rice.kew.api.document.search.DocumentSearchResults;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.datadictionary.validation.result.ConstraintValidationResult;
import org.kuali.rice.krad.datadictionary.validation.result.DictionaryValidationResult;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.EventType;
import org.martinlaw.bo.MatterAssignee;
import org.martinlaw.bo.MatterAssignment;
import org.martinlaw.bo.MatterConsideration;
import org.martinlaw.bo.MatterEvent;
import org.martinlaw.bo.MatterEventTest;
import org.martinlaw.bo.MatterTransactionDoc;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.bo.Status;
import org.martinlaw.bo.contract.Contract;
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
import org.martinlaw.bo.courtcase.CourtCaseWitness;
import org.martinlaw.bo.courtcase.Event;
import org.martinlaw.bo.opinion.Opinion;
import org.martinlaw.keyvalues.ScopedKeyValuesBase;

/**
 * holds various methods used across test cases
 * 
 * @author mugo
 *
 */
public class TestUtils {
	private String testContractLocalReference = "CN/RENT/01";
	private String contractName = "rent of flat";
	private String testConveyanceName = "sale of KAZ 457T";
	private String assignee1 = "pn";
	private String assignee2 = "aw";
	private String testOpinionName;
	private String testOpinionClientName;
	private String testOpinionLocalReference;
	private Log log = LogFactory.getLog(getClass());
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
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Contract getTestContract()  {
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
		// consideration - use default considerations
		/*try {
			contract.getConsiderations().add((Consideration) getTestConsideration(Consideration.class));
		} catch (Exception e) {
			//fail("could not add consideration");
			log.error(e);
		}*/
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
	 * @return a test consideration
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public MatterConsideration<? extends MatterTransactionDoc> getTestConsideration
	(Class<? extends MatterConsideration<? extends MatterTransactionDoc>> klass) 
			throws InstantiationException, IllegalAccessException {
		MatterConsideration<? extends MatterTransactionDoc> consideration = klass.newInstance();
		consideration.setAmount(new BigDecimal(1000));
		consideration.setCurrency("KES");
		consideration.setDescription("see breakdown in attached file");
		consideration.setConsiderationTypeId(10003l);
		consideration.setMatterId(1001l);
		return consideration;
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
		assertFalse("considerations should not be empty", contract.getConsiderations().isEmpty());
		testConsiderationFields(contract.getConsiderations().get(0));
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
	public void testRetrievedMatterEventFields(MatterEvent event) {
		assertNotNull("event should not be null", event);
		assertEquals("first hearing date",event.getComment());
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(event.getStartDate().getTime());
		assertEquals("event year differs", 2011,cal.get(Calendar.YEAR));
		assertEquals("event month differs", Calendar.JUNE, cal.get(Calendar.MONTH));
		assertEquals("event date differs", 1, cal.get(Calendar.DATE));
		assertEquals("location differs", "nakuru", event.getLocation());
		assertTrue("event should be active", event.getActive());
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
		testOpinionLocalReference = "EN/OP/01";
		opinion.setLocalReference(testOpinionLocalReference);
		opinion.setStatusId(1001l);
		
		org.martinlaw.bo.opinion.Client client = new org.martinlaw.bo.opinion.Client();
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
	 * test a list of {@link MatterTransactionDoc}, created via sql
	 * 
	 * @param txDocs - the list
	 */
	public void testMatterTransactionDocList(List<? extends MatterTransactionDoc> txDocs) {
		assertNotNull("transaction list should not be null", txDocs);
		assertFalse("transactions should not be empty", txDocs.isEmpty());
		assertEquals("expected number of transactions differs", 2, txDocs.size());
		assertEquals("client name differs", "mawanja", txDocs.get(0).getClientPrincipalName());
		assertEquals("amount differs", 0, new BigDecimal(10000.00).compareTo(txDocs.get(1).getAmount()));
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
	 * @param consideration - a default consideration (created in code then saved to db)
	 */
	public void testConsiderationFields(MatterConsideration<?> consideration) {
		consideration.refreshNonUpdateableReferences();//to retrieve the consideration type
		assertEquals("consideration amount differs", 0, consideration.getAmount().compareTo(new BigDecimal(0)));
		//String desc = "see breakdown in attached file";
		assertEquals("consideration description differs", MartinlawConstants.DefaultConsideration.LEGAL_FEE_DESCRIPTION,
				consideration.getDescription());
		assertEquals("consideration currency differs", MartinlawConstants.DefaultConsideration.CURRENCY,
				consideration.getCurrency());
		assertEquals("consideration type id differs", MartinlawConstants.DefaultConsideration.LEGAL_FEE_TYPE_ID,
				consideration.getConsiderationTypeId());
		assertNotNull("consideration type should not be null", consideration.getConsiderationType());
		assertEquals("consideration type name differs", "Legal fee", consideration.getConsiderationType().getName());
	}
	
	/**
	 * test that the  {@link MatterConsideration} has the expected field values
	 * @param consideration - the test object - retrieved (from db)
	 */
	public void testRetrievedConsiderationFields(MatterConsideration<?> consideration) {
		assertEquals("consideration amount differs", 0, consideration.getAmount().compareTo(new BigDecimal(41000)));
		String desc = "to be paid in 2 installments";
		assertEquals("consideration description differs", desc, consideration.getDescription());
		assertEquals("consideration currency differs", "TZS", consideration.getCurrency());
		assertNotNull("consideration type should not be null", consideration.getConsiderationType());
		assertEquals("consideration type name differs", "Legal fee", consideration.getConsiderationType().getName());
		testMatterTransactionDocList(consideration.getTransactions());
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
	 * populates a transaction doc with test data
	 * 
	 * @param transaction - the transaction to populate, one of the several {@code MatterTransaction} descendants
	 */
	@SuppressWarnings("unchecked")
	public MatterTransactionDoc populateMatterTransactionDocData(MatterTransactionDoc transaction) {
		transaction.setAmount(new BigDecimal(2000l));
		String clientPrincipalName = "pkk";
		transaction.setClientPrincipalName(clientPrincipalName);
		transaction.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
		transaction.setTransactionTypeId(1001l);
		MatterConsideration<?> consideration;
		try {
			consideration = getTestConsideration((Class<? extends MatterConsideration<MatterTransactionDoc>>) transaction.getClass().getDeclaredField("consideration").getType());
			getBoSvc().save(consideration);
			consideration.refresh();
			transaction.setConsiderationId(consideration.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return transaction;
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
	 * populate a descendant of {@code MatterTransactionDoc} with test data
	 * @param klass - the doc to be populated
	 * @param tx - the descendant of {@code MatterTransaction} to be populated
	 * @return - the populated doc
	 * @throws WorkflowException 
	 */
	public MatterTransactionDoc populateTransactionDocForRouting(Class<? extends MatterTransactionDoc> klass) throws WorkflowException {
		MatterTransactionDoc doc = (MatterTransactionDoc) KRADServiceLocatorWeb.getDocumentService().getNewDocument(klass);
		doc.getDocumentHeader().setDocumentDescription("testing");
		doc.setMatterId(1001l);
		populateMatterTransactionDocData(doc);
		return doc;
	}

	/**
	 * create and populate a matter date for testing
	 * @param d
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public <D extends MatterEvent> D getTestMatterEvent(Class<D> d) throws InstantiationException, IllegalAccessException {
		D date = d.newInstance();
		date.setComment("must attend");
		date.setMatterId(1001l);
		date.setTypeId(1001l);
		date.setStartDate(new Timestamp(System.currentTimeMillis()));
		date.setLocation("makadara");
		return date;
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
		getBoSvc().save(status);
		status.refresh();
		assertNotNull(status.getId());
		//create new case bo
		CourtCase caseBo = new CourtCase();
		
		caseBo.setLocalReference(localRef);
		caseBo.setCourtReference(courtRef);
		String name = "Fatuma Zainab Mohammed vs \n" + 
				"Ghati Dennitah \n"+
				"IEBC\n" +
				"Benson Njau (Kuria East Returning Officer)\n" +
				"Lilina Liluma (Returning Officer Awendo Constituency)\n" +
				"Moses Omondo Daula (Returning Officer Nyatike Constituency)\n"+
				"Jakton Nyonje (Returning Officer Oriri Constituency)\n" +
				"Noah Bowen (Rongo Constituency)\n" +
				"Alex Uyuga (Returning officer Suna East Constituency)\n" +
				"Jairus Obago (Returning Officer Migori County)\n" +
				"Adam Mohamed (Returning officer Kuria West Constituency)\n";
		caseBo.setName(name);
		caseBo.setStatus(status);
		caseBo.setTypeId(10004l);
		// side step validation error - error.required
		caseBo.setStatusId(status.getId());
		// clients & witnesses
		addClientsAndWitnesses(caseBo);
		return caseBo;
	}
	
	/**
	 * add test clients and witnesses
	 * @param kase
	 */
	public void addClientsAndWitnesses(CourtCase kase) {
		//create and save client, witness
		org.martinlaw.bo.courtcase.Client cl1 = new org.martinlaw.bo.courtcase.Client();
		cl1.setMatterId(kase.getId());
		cl1.setPrincipalName("Joseph Ndungu");
		org.martinlaw.bo.courtcase.Client cl2 = new org.martinlaw.bo.courtcase.Client();
		cl2.setMatterId(kase.getId());
		cl2.setPrincipalName("Joseph Thube");

		kase.getClients().add(cl1);
		kase.getClients().add(cl2);
		
		CourtCaseWitness wit = new CourtCaseWitness();
		wit.setCourtCaseId(kase.getId());
		wit.setPrincipalName("Thomas Kaberi Gitau");
		kase.getWitnesses().add(wit);
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
	public MatterEvent getTestMatterEventForStringTemplates() {
		Event caseEvent = new Event();
		caseEvent.setId(1001l);
		EventType eventType = new EventType();
		eventType.setName("judgement");
		caseEvent.setType(eventType);
		
		CourtCase theCase = new CourtCase();
		theCase.setLocalReference("my/cases/1");
		theCase.setName("water vs fire");
		theCase.setCourtReference("No 12 of 2015");
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
		caseEvent.setLocation("Milimani");
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
	
	/**
	 * get a populated transaction doc for testing CRUD ops
	 * @param txDoc - the transaction document class being tested 
	 * @param tx - the transaction being tested
	 * @param documentNumber - the document number to be used
	 * @return the test object
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <A extends MatterTransactionDoc> MatterTransactionDoc 
		getTestTransactionDocForCRUD(Class<A> txDoc, String documentNumber) throws InstantiationException, IllegalAccessException {
		MatterTransactionDoc transactionDoc = txDoc.newInstance();
		transactionDoc.setDocumentNumber(documentNumber);
		transactionDoc.getDocumentHeader().setDocumentNumber(documentNumber);
		transactionDoc.getDocumentHeader().setDocumentDescription("cash");
		transactionDoc.setMatterId(1001l);
		populateMatterTransactionDocData(transactionDoc);
		
		return transactionDoc;
	}
	
	/**
	 * test doc search for descendants of {@link MatterTransactionDoc}
	 * @param txDocClass - the document class
	 * @param docType TODO
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws WorkflowException 
	 */
	public void testMatterTransactionDocSearch(Class<? extends MatterTransactionDoc> txDocClass, String docType) throws InstantiationException, IllegalAccessException, WorkflowException {
		// route some test documents then search
		GlobalVariables.setUserSession(new UserSession("lawyer1"));
		MatterTransactionDoc txDoc1 = populateTransactionDocForRouting(txDocClass);
		testTransactionalRoutingInitToFinal(txDoc1);
		
		MatterTransactionDoc txDoc2 = populateTransactionDocForRouting(txDocClass);
		txDoc2.setAmount(new BigDecimal(50001));
		final String clientPrincipalName = "kyaloda";
		txDoc2.setClientPrincipalName(clientPrincipalName);
		testTransactionalRoutingInitToFinal(txDoc2);
		
		MatterTransactionDoc txDoc3 = populateTransactionDocForRouting(txDocClass);
		txDoc3.setAmount(new BigDecimal(45000));
		txDoc3.setClientPrincipalName("sirarthur");
		final int transactionTypeId = 1003;
		txDoc3.setTransactionTypeId(transactionTypeId);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			txDoc3.setDate(new Date(sdf.parse("21-Apr-2013").getTime()));
		} catch (ParseException e) {
			log.error(e);
			fail("unable to set date");
		}
		testTransactionalRoutingInitToFinal(txDoc3);
		
		// no document criteria given, so all documents should be found
		SearchTestCriteria crit1 = new SearchTestCriteria();
		crit1.setExpectedDocuments(3);
		// search for principal name
		SearchTestCriteria crit2 = new SearchTestCriteria();
		crit2.setExpectedDocuments(1);
		crit2.getFieldNamesToSearchValues().put("clientPrincipalName", clientPrincipalName);
		// search for amount range
		SearchTestCriteria crit3 = new SearchTestCriteria();
		crit3.setExpectedDocuments(2);
		crit3.getFieldNamesToSearchValues().put("amount", "45000 .. 55000");
		// search for transaction type
		SearchTestCriteria crit4 = new SearchTestCriteria();
		crit4.setExpectedDocuments(1);
		crit4.getFieldNamesToSearchValues().put("transactionTypeId", String.valueOf(transactionTypeId));
		// search for date range
		SearchTestCriteria crit5 = new SearchTestCriteria();
		crit5.setExpectedDocuments(1);
		crit5.getFieldNamesToSearchValues().put("date", "15 apr 2013 .. 22 apr 2013");
		// search consideration id
		SearchTestCriteria crit6 = new SearchTestCriteria();
		crit6.setExpectedDocuments(1);
		crit6.getFieldNamesToSearchValues().put("considerationId", String.valueOf(txDoc1.getConsiderationId()));
		
		List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
		crits.add(crit1);
		crits.add(crit2);
		crits.add(crit3);
		crits.add(crit4);
		crits.add(crit5);
		crits.add(crit6);
		runDocumentSearch(crits, docType);	
	}
	
	/**
	 * tests routing by lawyer so that doc goes to final
	 * this avoids 'user not authorized exceptions' that appear when document search is activated
	 * @param txDoc - the populated transactional document
	 */
	public void testTransactionalRoutingInitToFinal(Document doc)
			throws WorkflowException {
		
		// approve as lawyer1
		GlobalVariables.setUserSession(new UserSession("lawyer1"));
		/*doc = KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(doc.getDocumentNumber());
		assertTrue("document should be enroute", doc.getDocumentHeader().getWorkflowDocument().isEnroute());*/
		KRADServiceLocatorWeb.getDocumentService().routeDocument(doc, "approved", null);
		
		//retrieve again to confirm status
		doc = KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(doc.getDocumentNumber());
		assertTrue("document should have been approved", doc.getDocumentHeader().getWorkflowDocument().isApproved());
		assertTrue("document should be final", doc.getDocumentHeader().getWorkflowDocument().isFinal());
	}
	
	/**
	 * convenience method for running a document search
	 * 
	 * @param testCriteria holds info on the fields, search values and expected number of documents to find
	 * @return the list of results
	 */
	public List<DocumentSearchResults> runDocumentSearch(List<SearchTestCriteria> testCriteria, String docType) {
		DocumentSearchCriteria.Builder criteria = DocumentSearchCriteria.Builder.create();
		criteria.setDocumentTypeName(docType);
		criteria.setDateCreatedFrom(new DateTime(2013, 1, 1, 0, 0));
		ArrayList<DocumentSearchResults> resultsList = new ArrayList<DocumentSearchResults>(testCriteria.size());
		for (SearchTestCriteria crit: testCriteria) {
			criteria.getDocumentAttributeValues().clear();
			for (String fieldName: crit.getFieldNamesToSearchValues().keySet()) {
				criteria.addDocumentAttributeValue(fieldName,  crit.getFieldNamesToSearchValues().get(fieldName));
			}
			DocumentSearchResults results = KEWServiceLocator.getDocumentSearchService().lookupDocuments(
					KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName("clerk1").getPrincipalId(),
					criteria.build());
			assertEquals("expected number of documents not found for " + crit.toString(),
					crit.getExpectedDocuments(), results.getSearchResults().size());
			resultsList.add(results);
		}
		return resultsList;
	}
	
	/**
	 * test that court case status type key values returns the correct number
	 */
	public void testMatterStatusKeyValues(ScopedKeyValuesBase kv, String comment, int kvSize) {
		assertEquals(comment, kvSize, kv.getKeyValues().size());
	}
	
	/**
	 * carry out a validation while checking for the expected number of errors
	 * @param bo
	 * @param expectedErrors
	 * @param attributeName
	 */
	public void validate(Object bo, final int expectedErrors, final String attributeName) {
		try {
			DictionaryValidationResult result = KRADServiceLocatorWeb.getDictionaryValidationService().validate(bo,
					bo.getClass().getCanonicalName(), attributeName, true);
			final Iterator<ConstraintValidationResult> iterator = result.iterator();
			while (iterator.hasNext()) {
				final ConstraintValidationResult validationResult = iterator.next();
				// using error level to avoid having to configure logging
				log.error(ReflectionToStringBuilder.toString(validationResult));
			}
			assertEquals("expected number of errors differ", expectedErrors, result.getNumberOfErrors());
		} catch (Exception e) {
			log.error("exception occured", e);
			fail("exception occured");
		}
	}

}
