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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import org.kuali.rice.core.api.datetime.DateTimeService;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kew.api.document.search.DocumentSearchCriteria;
import org.kuali.rice.kew.api.document.search.DocumentSearchResults;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.datadictionary.validation.result.ConstraintValidationResult;
import org.kuali.rice.krad.datadictionary.validation.result.DictionaryValidationResult;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.EventType;
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.MatterAssignee;
import org.martinlaw.bo.MatterClient;
import org.martinlaw.bo.MatterConsideration;
import org.martinlaw.bo.MatterEvent;
import org.martinlaw.bo.MatterEventTest;
import org.martinlaw.bo.MatterTransactionDoc;
import org.martinlaw.bo.MatterType;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.bo.Status;
import org.martinlaw.bo.contract.Contract;
import org.martinlaw.bo.contract.ContractDuration;
import org.martinlaw.bo.contract.ContractParty;
import org.martinlaw.bo.contract.ContractSignatory;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.bo.courtcase.CourtCaseWitness;
import org.martinlaw.bo.courtcase.LandCase;
import org.martinlaw.keyvalues.ScopedKeyValuesUif;

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
	private String testOpinionLocalReference;
	private Log log = LogFactory.getLog(getClass());
	private String testClientPrincipalName = "clerk2";
	private String testClientFirstName = "Clerk";
	/**
	 * get a test conveyance object
	 * @return
	 */
	public Conveyance getTestConveyance() {
		Conveyance conv = new Conveyance();
		conv.setName(getTestConveyanceName());
		conv.setLocalReference("EN/C001");
		conv.setTypeId(10010l);
		conv.setStatusId(10033l);
		conv.setClientPrincipalName(testClientPrincipalName);
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
		contract.setTypeId(10006l);
		contract.setSummaryOfTerms("see attached file");
		contract.setServiceOffered("flat 1f2");
		contract.setStatusId(10033l);
		contract.setClientPrincipalName(testClientPrincipalName);
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
	public MatterConsideration getTestConsideration	() 
			throws InstantiationException, IllegalAccessException {
		MatterConsideration consideration = new MatterConsideration();
		consideration.setAmount(new BigDecimal(1000));
		consideration.setCurrency("KES");
		consideration.setDescription("see breakdown in attached file");
		consideration.setConsiderationTypeId(10014l);
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
		assertNotNull("matter type id should not be null", contract.getTypeId());
		assertNotNull("matter type should exist", 
				KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(MatterType.class, contract.getTypeId()));
		assertNotNull("matter type should not be null", contract.getType());
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
		
		assertNotNull("event type should not be null", event.getType());
		assertEquals("event type name differs", "Hearing", event.getType().getName());
	}
	
	/**
	 * creates a test {@link MatterAssignee}
	 * 
	 * @return the test object
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public MatterAssignee getTestAssignee() {
		MatterAssignee assignee = new MatterAssignee();
		
		assignee.setPrincipalName(assignee1);
		assignee.setMatterId(1004l);
		
		return assignee;
	}

	/**
	 * @return the assignee1
	 */
	public String getTestAssigneePrincipalName() {
		return assignee1;
	}

	/**
	 * @return the testOpinionLocalReference
	 */
	public String getTestOpinionLocalReference() {
		return testOpinionLocalReference;
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
	public void testConsiderationFields(MatterConsideration consideration) {
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
	public void testRetrievedConsiderationFields(MatterConsideration consideration) {
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
	public MatterTransactionDoc populateMatterTransactionDocData(MatterTransactionDoc transaction) {
		transaction.setAmount(new BigDecimal(2000l));
		String clientPrincipalName = "pkk";
		transaction.setClientPrincipalName(clientPrincipalName);
		transaction.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
		transaction.setTransactionTypeId(10026l);
		MatterConsideration consideration;
		try {
			consideration = getTestConsideration();
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
	 * create and populate a matter date for integration testing
	 * @param d - the event class
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public <D extends MatterEvent> D getTestMatterEventIT(Class<D> d) throws InstantiationException, IllegalAccessException {
		D date = d.newInstance();
		date.setComment("must attend");
		date.setMatterId(1001l);
		date.setTypeId(10029l);
		date.setStartDate(new Timestamp(System.currentTimeMillis()));
		date.setLocation("makadara");
		return date;
	}
	
	/**
	 * create and populate a matter date for unit/mock testing
	 * @param e - the event class
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public <E extends MatterEvent> E getTestMatterEventUnt(Class<E> e) throws InstantiationException, IllegalAccessException {
		E event = e.newInstance();
		final Timestamp date = new Timestamp(System.currentTimeMillis());
		event.setStartDate(date);
		final String location = "Afraha";
		event.setLocation(location);
		EventType eventType = new EventType();
		final String eventName = "Demo";
		eventType.setName(eventName);
		event.setType(eventType);
		
		SimpleDateFormat sdf = new SimpleDateFormat(MartinlawConstants.DEFAULT_TIMESTAMP_FORMAT);
		final String formattedDate = sdf.format(event.getStartDate());
		
		DateTimeService dtSvc = mock(DateTimeService.class);
		when(dtSvc.toDateTimeString(same(event.getStartDate()))).thenReturn(formattedDate);
		event.setDateTimeService(dtSvc);
		
		return event;
	}
	
	
	
	/**
	 * get a populated test court case
	 * @param localRef - the local reference to set
	 * @param courtRef - the court number to set
	 * @param klass - the class to instantiate
	 * @return the test object
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public <C extends Matter> Matter getTestCourtCase(String localRef, String courtRef, Class<C> klass) throws InstantiationException, IllegalAccessException {
		CourtCase caseBo = (CourtCase) getTestMatter(localRef, klass);
		caseBo.setCourtReference(courtRef);
		caseBo.setTypeId(10004l);
		// witnesses
		addWitnesses(caseBo);
		
		return caseBo;
	}
	
	/**
	 * get a populated test matter
	 * @param localRef - the local reference to set
	 * @param courtRef - the court number to set
	 * @return the test object
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public <M extends Matter> Matter getTestMatter(String localRef, Class<M> klass) throws InstantiationException, IllegalAccessException {
		//set up test status
		Status status = new Status();
		status.setName("Testing");
		getBoSvc().save(status);
		status.refresh();
		assertNotNull(status.getId());
		//create new case bo
		Matter matter = klass.newInstance();
		
		matter.setLocalReference(localRef);
		matter.setClientPrincipalName("client1");
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
		matter.setName(name);
		matter.setStatus(status);
		// side step validation error - error.required
		matter.setStatusId(status.getId());
		matter.setTypeId(10011l);
		// clients & witnesses
		addClients(matter);
		
		return matter;
	}
	
	/**
	 * add test clients to a matter
	 * @param kase
	 */
	public void addClients(Matter kase) {
		//create and save client, witness
		MatterClient cl1 = new MatterClient();
		cl1.setMatterId(kase.getId());
		cl1.setPrincipalName("Joseph Ndungu");
		MatterClient cl2 = new MatterClient();
		cl2.setMatterId(kase.getId());
		cl2.setPrincipalName("Joseph Thube");

		kase.getClients().add(cl1);
		kase.getClients().add(cl2);
	}
	
	/**
	 * add test witnesses to a matter
	 * @param kase
	 */
	public void addWitnesses(CourtCase kase) {
		CourtCaseWitness wit = new CourtCaseWitness();
		wit.setCourtCaseId(kase.getId());
		wit.setPrincipalName("Thomas Kaberi Gitau");
		kase.getWitnesses().add(wit);
	}
	
	/**
	 * creates a test object of type {@link MatterEvent} containing info useful for testing templating
	 * @see org.martinlaw.bo.MatterEventTest#testToIcalendar() 
	 * @see org.martinlaw.bo.MatterEventMaintainableTest#testCreateNotificationMessage()
	 * @return
	 */
	public MatterEvent getTestMatterEventForStringTemplates() {
		MatterEvent caseEvent = new MatterEvent();
		caseEvent.setId(1001l);
		EventType eventType = new EventType();
		eventType.setName("judgement");
		caseEvent.setType(eventType);
		
		CourtCase theCase = new CourtCase();
		theCase.setLocalReference("my/cases/1");
		theCase.setName("water vs fire");
		theCase.setCourtReference("No 12 of 2015");
		theCase.setConcreteClass(CourtCase.class.getCanonicalName());
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
		MatterEvent caseDate = (MatterEvent) getTestMatterEventForStringTemplates();
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
		final int transactionTypeId = 10028;
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
	
	/**
	 * confirm the {@link Matter#getClient()} has the expected test values
	 * @param matter - the matter to test
	 * @param expectedFirstName - the expected first name of the principal
	 */
	public void testMatterClient(Matter matter, String expectedFirstName) {
		assertNotNull("client object should not be null", matter.getClient());
		assertNotNull("client principal name should not be null", matter.getClientPrincipalName());
		assertEquals("client given name differs", expectedFirstName, matter.getClient().getFirstName());
	}

	/**
	 * @return the testClientPrincipalName
	 */
	public String getTestClientPrincipalName() {
		return testClientPrincipalName;
	}

	/**
	 * @param testClientPrincipalName the testClientPrincipalName to set
	 */
	public void setTestClientPrincipalName(String testClientPrincipalName) {
		this.testClientPrincipalName = testClientPrincipalName;
	}

	/**
	 * @return the testClientFirstName
	 */
	public String getTestClientFirstName() {
		return testClientFirstName;
	}

	/**
	 * @param testClientFirstName the testClientFirstName to set
	 */
	public void setTestClientFirstName(String testClientFirstName) {
		this.testClientFirstName = testClientFirstName;
	}
	
	/**
	 * creates and populats a mock transaction object
	 * TODO <p>only sets the amount currently</p>
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public MatterTransactionDoc getMockTransaction(BigDecimal amount) throws InstantiationException, IllegalAccessException {
		MatterTransactionDoc tx = mock(MatterTransactionDoc.class);
		when(tx.getAmount()).thenReturn(amount);
		return tx;
	}

	/**
	 * convenience method to test the test assignee created in {@link #getTestAssignee()}
	 * @param assignee - the object to test
	 */
	public void testAssigneeFields(MatterAssignee assignee) {
		assertEquals("principal name differs", getTestAssigneePrincipalName(), assignee.getPrincipalName());
		assertNotNull("matter should not be null", assignee.getMatterId());
		assertTrue("assignee should be active", assignee.getActive());
		assertTrue("assignee should not have physical file", assignee.getHasPhysicalFile());	
	}
	
	/**
	 * a convenience method to create a mock maintenance doc form and populate mock objects
	 * <p> so that form.getDocument().getNewMaintainableObject().getDataObject() can work</p>
	 * @return
	 */
	public MaintenanceDocumentForm createMockMaintenanceDocForm() {
		MaintenanceDocumentForm form = mock(MaintenanceDocumentForm.class);
		MaintenanceDocument doc = mock(MaintenanceDocument.class);
		when(form.getDocument()).thenReturn(doc);
		Maintainable maintainable = mock(Maintainable.class);
		when(doc.getNewMaintainableObject()).thenReturn(maintainable);
		return form;
	}
	
	/**
	 * test the scope key values
	 * @param dataObjectName - a descriptive name to use in test messages
	 * @param expectedCourtCaseScopeCount - the expected number of objects with court case scope
	 * @param expectedContractScopeCount - the expected number of objects with Contract scope
	 * @param expectedConveyanceScopeCount - the expected number of objects with Conveyance scope
	 * @param expectedEmptyScopeCount - the number of objects with no scope
	 * @param expectedMatterScopeCount - the expected number of objects with matter scope
	 * @param expectedLandCaseScopeCount - the expected number of objects with court case scope
	 * @param scopedClass - the object whose scope is being tested
	 */
	public void testScopeKeyValues(final String dataObjectName,
			final int expectedCourtCaseScopeCount,
			final int expectedContractScopeCount,
			final int expectedConveyanceScopeCount,
			final int expectedEmptyScopeCount,
			final int expectedMatterScopeCount,
			final int expectedLandCaseScopeCount,
			final Class<? extends BusinessObject> scopedClass) {
		String commentTemplate = "expected %s %s with %s scope plus %s with empty scope";
		ScopedKeyValuesUif kv = new ScopedKeyValuesUif();
		kv.setScopedClass(scopedClass);
		
		MaintenanceDocumentForm form = createMockMaintenanceDocForm();
		Maintainable newMaintainableObject = form.getDocument().getNewMaintainableObject();
		
		
		when(newMaintainableObject.getDataObject()).thenReturn(new CourtCase());
		String comment = String.format(commentTemplate, expectedCourtCaseScopeCount, dataObjectName, "court case", expectedEmptyScopeCount);
		assertEquals(comment, expectedCourtCaseScopeCount + expectedEmptyScopeCount, kv.getKeyValues(form).size());
		
		when(newMaintainableObject.getDataObject()).thenReturn(new LandCase());
		comment = String.format(commentTemplate, expectedLandCaseScopeCount, dataObjectName, "land case", expectedEmptyScopeCount);
		assertEquals(comment, expectedLandCaseScopeCount + expectedEmptyScopeCount, kv.getKeyValues(form).size());
		
		comment = String.format(commentTemplate, expectedContractScopeCount, dataObjectName, "contract", expectedEmptyScopeCount);
		when(newMaintainableObject.getDataObject()).thenReturn(new Contract());
		assertEquals(comment, expectedContractScopeCount + expectedEmptyScopeCount, kv.getKeyValues(form).size());
		
		comment = String.format(commentTemplate, expectedMatterScopeCount, dataObjectName, "matter", expectedEmptyScopeCount);
		when(newMaintainableObject.getDataObject()).thenReturn(new Matter());
		assertEquals(comment, expectedMatterScopeCount + expectedEmptyScopeCount, kv.getKeyValues(form).size());
		
		comment = String.format(commentTemplate, expectedConveyanceScopeCount, dataObjectName, "Conveyance", expectedEmptyScopeCount);
		when(newMaintainableObject.getDataObject()).thenReturn(new Conveyance());
		assertEquals(comment, expectedConveyanceScopeCount + expectedEmptyScopeCount, kv.getKeyValues(form).size());
	}

}
