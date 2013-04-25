/**
 * 
 */
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

import java.util.ArrayList;
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
import org.martinlaw.bo.courtcase.Event;
import org.martinlaw.bo.courtcase.CourtCaseWitness;
import org.martinlaw.bo.courtcase.Work;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test the data dictionary
 * 
 * @author mugo
 */
//@BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
public class CourtCaseBOTest extends MartinlawTestsBase {
	private Log log = LogFactory.getLog(getClass());

	/**
	 * test saving, retrieving a case BO
	 * 
	 * @throws Exception
	 */
	@Test
    public void testCaseRetrieveEdit() throws Exception {
        //BusinessObjectService boSvc = KRADServiceLocator.getBusinessObjectService();
        CourtCase kase = getBoSvc().findBySinglePrimaryKey(CourtCase.class, new Long(1001));
        assertNotNull(kase);
        testCourtCaseFields(kase);
        //change fields
        kase.setCourtReference(null);
        kase.setLocalReference("L1");
        //TODO change values of collection members and test
        //boSvc.save(annexDoc); 	  	
        //boSvc.save(annex);
        kase.refresh();
	}

	private void testCourtCaseFields(CourtCase kase) {
		assertEquals("c1", kase.getCourtReference());
        assertEquals("l1", kase.getLocalReference());
        assertEquals("Barca vs Man U (2011)", kase.getName());
        //status
        assertNotNull(kase.getStatus());
        assertEquals("hearing", kase.getStatus().getStatus());
        assertEquals(Status.COURT_CASE_TYPE.getKey(), kase.getStatus().getType());
        //case client
        List<Client> clients = kase.getClients();
        assertEquals(2, clients.size());
        Client client = clients.get(0);
        assertEquals("client1", client.getPrincipalName());
        assertEquals("Client", client.getPerson().getFirstName());
        //witness
        List<CourtCaseWitness> witnesses = kase.getWitnesses();
        assertEquals(1, witnesses.size());
        CourtCaseWitness witness = witnesses.get(0);
        assertEquals("witness1", witness.getPrincipalName());
        assertEquals("Witness",witness.getPerson().getFirstName());
        //hearing date
        List<Event> dates = kase.getEvents();
        assertEquals(1,dates.size());
        getTestUtils().testRetrievedMatterEventFields(kase.getEvents().get(0));

        /*testFeeFields(kase.getFees().get(0));*/
        // attachments
        assertEquals("number of attachments not the expected quantity", 2, kase.getAttachments().size());
        assertEquals("first attachment name differs", "submission.pdf", kase.getAttachments().get(0).getAttachmentFileName());
        assertEquals("second attachment name differs", "pleading.odt", kase.getAttachments().get(1).getAttachmentFileName());
        // assignment
        getTestUtils().testAssignees(kase.getAssignees());
        //work
        List<Work> work = kase.getWork();
        getTestUtils().testWorkList(work);
        //consideration
        getTestUtils().testRetrievedConsiderationFields(kase.getConsiderations().get(0));
	}

	
	
	@Test
	/**
	 * create a new court case, save it, add collection objects and verify existence
	 * 
	 * @throws Exception
	 */
	public void testCaseSaveRetrieve() throws Exception {
		CourtCase kase = new CourtCase();
		kase.setLocalReference("local1");

		Status status = new Status();
		String statusText = "filed";
		status.setStatus(statusText);
		status.setType(Status.COURT_CASE_TYPE.getKey());
		// save status since it is not updated from the court case - ojb config to prevent object modified errors when the status is changed
		getBoSvc().save(status);
		status.refresh();
		kase.setStatusId(status.getId());
		String name = "Future vs Past";
		kase.setName(name);
		getBoSvc().save(kase);
		
		kase = getBoSvc().findBySinglePrimaryKey(CourtCase.class, kase.getId());
		kase.refreshNonUpdateableReferences(); //without this, case status (object) is null
		assertEquals("court reference should be null", null, kase.getCourtReference());
		assertEquals("case name differs", name, kase.getName());
		assertEquals("local reference differs", "local1", kase.getLocalReference());
		assertEquals("clients size differs", 0, kase.getClients().size());
		assertEquals("witnesses size differs", 0, kase.getWitnesses().size());
		assertNotNull("status should not be null", kase.getStatus());
		assertEquals(statusText, kase.getStatus().getStatus());
		log.debug("Created case with id " + kase.getId());
		assertNotNull(kase.getId());
		//create and save client, witness
		Client cl = new Client();
		cl.setMatterId(kase.getId());
		cl.setPrincipalName("somename");//TODO needs to be verified (business rule?)
		List<Client> clts = new ArrayList<Client>(1);
		clts.add(cl);
		kase.setClients(clts);
		
		CourtCaseWitness wit = new CourtCaseWitness();
		wit.setCourtCaseId(kase.getId());
		wit.setPrincipalName("someothername");
		List<CourtCaseWitness> wits = new ArrayList<CourtCaseWitness>(1);
		wits.add(wit);
		kase.setWitnesses(wits);
		
		kase.getConsiderations().add((Consideration) getTestUtils().getTestConsideration(Consideration.class));
		
		getBoSvc().save(kase);
		
		kase = getBoSvc().findBySinglePrimaryKey(kase.getClass(), kase.getId());
		assertNotNull("clients should not be null", kase.getClients());
		assertEquals("number of clients expected differs", 1, kase.getClients().size());
		assertNotNull("witnesses should not be null",kase.getWitnesses());
		assertEquals("number of witnesses expected differs", 1, kase.getWitnesses().size());
		getTestUtils().testConsiderationFields(kase.getConsiderations().get(0));
	}

	@Test
	public void testCaseLookup() {
		//adapted from  org.kuali.rice.kns.service.LookupServiceTest
		LookupService lookupService = KRADServiceLocatorWeb.getLookupService();
        @SuppressWarnings("rawtypes")
		Map formProps = new HashMap();
        @SuppressWarnings("unchecked")
		Collection<CourtCase> cases = lookupService.findCollectionBySearchHelper(CourtCase.class, formProps, false);
        assertEquals("number of cases differs", 3, cases.size());
	}
	
	@Test
	/**
	 * tests retrieving a client present in the db, then CRUD ops
	 */
	public void testCaseClient() {
		Client person = new Client();
		person.setMatterId(1001l);
		testMartinlawPersonCRUD(new Client(), "client1", person);
	}

	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testCaseClientNullableFields() {
		Client caseClient = new Client();
		getBoSvc().save(caseClient);
	}
	
	@Test
	/**
	 * tests retrieving a client present in the db, then CRUD ops
	 */
	public void testCaseWitness() {
		CourtCaseWitness person = new CourtCaseWitness();
		person.setCourtCaseId(1001l);
		testMartinlawPersonCRUD(new CourtCaseWitness(), "witness1", person);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	\1 * tests that non nullable fields are checked
	 */
	public void testCaseWitnessNullableFields() {
		CourtCaseWitness caseClient = new CourtCaseWitness();
		getBoSvc().save(caseClient);
	}
	
	@Test
	/**
	 * test that the court case status is loaded into the data dictionary
	 */
	public void testCourtCaseStatusAttributes() {
		testBoAttributesPresent(Status.class.getCanonicalName());
		
		Class<Status> dataObjectClass = Status.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}

	@Test
	/**
	 * test that the court case is loaded into the data dictionary
	 */
	public void testCourtCaseAttributes() {
		testBoAttributesPresent(CourtCase.class.getCanonicalName());
		Class<CourtCase> dataObjectClass = CourtCase.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}
	
	@Test
	/**
	 * test that the client is loaded into the data dictionary
	 */
	public void testCourtCaseClientAttributes() {
		testBoAttributesPresent(Client.class.getCanonicalName());
		Class<Client> dataObjectClass = Client.class;
		verifyInquiryLookup(dataObjectClass);
	}
	
	@Test
	/**
	 * test that the witness is loaded into the data dictionary
	 */
	public void testWitnessAttributes() {
		testBoAttributesPresent(CourtCaseWitness.class.getCanonicalName());
		Class<CourtCaseWitness> dataObjectClass = CourtCaseWitness.class;
		verifyInquiryLookup(dataObjectClass);
	}
	
	@Test
	/**
	 * test how a name is returned for clients/witnesses who are not yet created as KIM principals
	 */
	public void testCourtCasePersonName() {
		MartinlawPerson client = new Client();
		client.setPrincipalName("clientX");
		// should there be an error here - if the principalName does not represent a existing principal? 
		assertNotNull("person name should not be null", client.getPerson().getName());
		assertNull("should be null", client.getPerson().getPrincipalId());
	}
	
	@Test
	/**
	 * test that the {@link CourtCase} is loaded into the data dictionary
	 */
	public void testConsiderationAttributes() {
		testBoAttributesPresent(CourtCase.class.getCanonicalName());
	}
}
