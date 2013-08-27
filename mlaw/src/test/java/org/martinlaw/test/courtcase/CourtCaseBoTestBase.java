package org.martinlaw.test.courtcase;

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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.LookupService;
import org.martinlaw.bo.MartinlawPerson;
import org.martinlaw.bo.Status;
import org.martinlaw.bo.courtcase.Client;
import org.martinlaw.bo.courtcase.Consideration;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.bo.courtcase.CourtCaseType;
import org.martinlaw.bo.courtcase.CourtCaseWitness;
import org.martinlaw.bo.courtcase.Event;
import org.martinlaw.bo.courtcase.Work;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

public abstract class CourtCaseBoTestBase extends MartinlawTestsBase {

	private Log log = LogFactory.getLog(getClass());

	public CourtCaseBoTestBase() {
		super();
	}

	/**
	 * test saving, retrieving a case BO
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCaseRetrieveEdit() throws Exception {
	    //BusinessObjectService boSvc = KRADServiceLocator.getBusinessObjectService();
	    CourtCase kase = getBoSvc().findBySinglePrimaryKey(getDataObjectClass(), getDataObjectPrimaryKey());
	    assertNotNull("court case should not be null", kase);
	    testRetrievedCourtCaseFields(kase);
	    //change fields
	    kase.setCourtReference(null);
	    kase.setLocalReference("L1");
	    //TODO change values of collection members and test
	    //boSvc.save(annexDoc); 	  	
	    //boSvc.save(annex);
	    kase.refresh();
	}

	/**
	 * convenience method to test court case fields
	 * @param kase - the object to test
	 */
	protected void testRetrievedCourtCaseFields(CourtCase kase) {
		assertEquals("court ref differs", "c1", kase.getCourtReference());
	    assertEquals("local ref differs", "l1", kase.getLocalReference());
	    assertEquals("parties differ", "Barca vs Man U (2011)", kase.getName());
	    //status
	    assertNotNull(kase.getStatus());
	    assertEquals("status differs", "hearing", kase.getStatus().getStatus());
	    //case client
	    List<Client> clients = kase.getClients();
	    assertEquals("number of clients differs", 2, clients.size());
	    Client client = clients.get(0);
	    assertEquals("client principal name differs", "client1", client.getPrincipalName());
	    assertEquals("client first name differs", "Client", client.getPerson().getFirstName());
	    // witness
	    List<CourtCaseWitness> witnesses = kase.getWitnesses();
	    assertEquals(1, witnesses.size());
	    CourtCaseWitness witness = witnesses.get(0);
	    assertEquals("witness principal name differs", "witness1", witness.getPrincipalName());
	    assertEquals("witness first name differs", "Witness",witness.getPerson().getFirstName());
	    //hearing date
	    List<Event> dates = kase.getEvents();
	    assertEquals("number of dates differs", 1, dates.size());
	    getTestUtils().testRetrievedMatterEventFields(kase.getEvents().get(0));
	
	    /*testFeeFields(kase.getFees().get(0));*/
	    // attachments
	    assertEquals("number of attachments not the expected quantity", 2, kase.getAttachments().size());
	    assertEquals("first attachment name differs", "submission.pdf", kase.getAttachments().get(0).getAttachmentFileName());
	    assertEquals("second attachment name differs", "pleading.odt", kase.getAttachments().get(1).getAttachmentFileName());
	    // assignment
	    getTestUtils().testAssignees(kase.getAssignees());
	    // work
	    List<Work> work = kase.getWork();
	    getTestUtils().testWorkList(work);
	    // consideration
	    assertEquals("number of considerations differs", 1, kase.getConsiderations().size());
	    getTestUtils().testRetrievedConsiderationFields(kase.getConsiderations().get(0));
	    // type
	    assertNotNull("case type should not be null", kase.getType());
	    assertEquals("case type id differs", new Long(10001), kase.getType().getId());
	    // client
	    getTestUtils().testMatterClient(kase, "Client");
	}

	@Test
	/**
	 * test CRUD ops
	 * @throws Exception
	 */
	public void testCaseCRUD() throws Exception {
		final String localReference = "local1";
		String statusText = "filed";
		final String typeName = "petition";
		String name = "Ghati Dennitah\n"+
						"IEBC\n" +
						"Benson Njau (Kuria East Returning Officer)\n" +
						"Lilina Liluma (Returning Officer Awendo Constituency)\n" +
						"Moses Omondo Daula (Returning Officer Nyatike Constituency)\n"+
						"Jakton Nyonje (Returning Officer Oriri Constituency)\n" +
						"Noah Bowen (Rongo Constituency)\n" +
						"Alex Uyuga (Returning officer Suna East Constituency)\n" +
						"Jairus Obago (Returning Officer Migori County)\n" +
						"Adam Mohamed (Returning officer Kuria West Constituency)\n";
		final String courtReference = "Mutomo Magistrates Court Petition No. 1 of 2013";
		CourtCase kase = getTestCourtCase(localReference, statusText, typeName, courtReference, name);
		// C
		getBoSvc().save(kase);
		// R
		kase = getBoSvc().findBySinglePrimaryKey(getDataObjectClass(), kase.getId());
		kase.refreshNonUpdateableReferences(); //without this, case status (object) is null
		assertEquals("case name differs", name, kase.getName());
		assertEquals("local reference differs", localReference, kase.getLocalReference());
		assertEquals("clients size differs", 0, kase.getClients().size());
		assertEquals("witnesses size differs", 0, kase.getWitnesses().size());
		assertNotNull("status id should not be null", kase.getStatusId());
		assertNotNull("status should not be null", kase.getStatus());
		assertEquals("court ref differs", courtReference, kase.getCourtReference());
		assertEquals("status differs", statusText, kase.getStatus().getStatus());
		assertNotNull("considerations should not be null", kase.getConsiderations());
		assertEquals("default number of considerations differs", 2, kase.getConsiderations().size());
		log.debug("Created case with id " + kase.getId());
		assertNotNull("case id should not be null", kase.getId());
		assertEquals("case type name differs", typeName, kase.getType().getName());
		assertEquals("ojb concrete class differs", getDataObjectClass().getCanonicalName(), kase.getConcreteClass());
		// U
		getTestUtils().addClientsAndWitnesses(kase);
		
		kase.getConsiderations().add((Consideration) getTestUtils().getTestConsideration(Consideration.class));
		
		getBoSvc().save(kase);
		
		kase = getBoSvc().findBySinglePrimaryKey(kase.getClass(), kase.getId());
		assertNotNull("clients should not be null", kase.getClients());
		assertEquals("number of clients expected differs", 2, kase.getClients().size());
		assertNotNull("witnesses should not be null",kase.getWitnesses());
		assertEquals("number of witnesses expected differs", 1, kase.getWitnesses().size());
		getTestUtils().testConsiderationFields(kase.getConsiderations().get(0));
		getTestUtils().testMatterClient(kase, getTestUtils().getTestClientFirstName());
		
		// D
		getBoSvc().delete(kase);
		assertNull(getBoSvc().findBySinglePrimaryKey(getDataObjectClass(), kase.getId()));
		for (Client client: kase.getClients()) {
			assertNull(getBoSvc().findBySinglePrimaryKey(Client.class, client.getId()));
		}
		assertNull(getBoSvc().findBySinglePrimaryKey(CourtCaseWitness.class, kase.getWitnesses().get(0).getId()));
		assertNull(getBoSvc().findBySinglePrimaryKey(Consideration.class, kase.getConsiderations().get(0).getId()));
	}
	
	/**
	 * create a test court case or descendant
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	protected CourtCase getTestCourtCase(String localReference, String statusText, String caseTypeName, 
			String courtReference, String parties) throws InstantiationException, IllegalAccessException {
		CourtCase kase = getDataObjectClass().newInstance();
		kase.setLocalReference(localReference);
	
		Status status = new Status();
		status.setStatus(statusText);
		// save status since it is not updated from the court case - ojb config to prevent object modified errors when the status is changed
		getBoSvc().save(status);
		status.refresh();
		kase.setStatusId(status.getId());
		kase.setClientPrincipalName(getTestUtils().getTestClientPrincipalName());
		
		CourtCaseType type = new CourtCaseType();
		type.setName(caseTypeName);
		getBoSvc().save(type);
		type.refresh();
		kase.setTypeId(type.getId());
		kase.setName(parties);
		kase.setCourtReference(courtReference);
		
		return kase;
	}

	@Test
	public void testCaseLookup() {
		//adapted from  org.kuali.rice.kns.service.LookupServiceTest
		LookupService lookupService = KRADServiceLocatorWeb.getLookupService();
	    @SuppressWarnings("rawtypes")
		Map formProps = new HashMap();
	    @SuppressWarnings("unchecked")
	    // includes any land cases
		Collection<CourtCase> cases = lookupService.findCollectionBySearchHelper(getDataObjectClass(), formProps, false);
	    assertEquals("number of cases differs", getExpectedLookupCount(), cases.size());
	}

	@Test
	public void testCaseClient() {
		Client person = new Client();
		person.setMatterId(1001l);
		testMartinlawPersonCRUD(new Client(), "client1", person);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testCaseClientNullableFields() {
		Client caseClient = new Client();
		getBoSvc().save(caseClient);
	}

	@Test
	public void testCaseWitness() {
		CourtCaseWitness person = new CourtCaseWitness();
		person.setCourtCaseId(1001l);
		testMartinlawPersonCRUD(new CourtCaseWitness(), "witness1", person);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testCaseWitnessNullableFields() {
		CourtCaseWitness caseClient = new CourtCaseWitness();
		getBoSvc().save(caseClient);
	}

	@Test
	public void testCourtCaseStatusAttributes() {
		testBoAttributesPresent(Status.class.getCanonicalName());
		
		Class<Status> dataObjectClass = Status.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	public void testCourtCaseAttributes() {
		testBoAttributesPresent(getDataObjectClass().getCanonicalName());
		verifyMaintDocDataDictEntries(getDataObjectClass());
	}

	@Test
	public void testCourtCaseClientAttributes() {
		testBoAttributesPresent(Client.class.getCanonicalName());
		Class<Client> dataObjectClass = Client.class;
		verifyInquiryLookup(dataObjectClass);
	}

	@Test
	public void testWitnessAttributes() {
		testBoAttributesPresent(CourtCaseWitness.class.getCanonicalName());
		Class<CourtCaseWitness> dataObjectClass = CourtCaseWitness.class;
		verifyInquiryLookup(dataObjectClass);
	}

	@Test
	public void testCourtCasePersonName() {
		MartinlawPerson client = new Client();
		client.setPrincipalName("clientX");
		// should there be an error here - if the principalName does not represent a existing principal? 
		assertNotNull("person name should not be null", client.getPerson().getName());
		assertNull("should be null", client.getPerson().getPrincipalId());
	}

	@Test
	public void testCaseAttributes() {
		testBoAttributesPresent(getDataObjectClass().getCanonicalName());
	}
	
	/**
	 * 
	 * @return the data object (BO) class
	 */
	public abstract Class<? extends CourtCase> getDataObjectClass();
	
	/**
	 * 
	 * @return the primary key to retrive the data object from the db
	 */
	public abstract Long getDataObjectPrimaryKey();
	
	/**
	 * 
	 * @return the expected number of results on lookup
	 */
	public abstract int getExpectedLookupCount();
	
	/**
	 * for the court case created for CRUD, additional tests/verification
	 */
	public void additionalTestsForCreatedCourtCase(CourtCase kase){
		//default does nothing;
	}

}