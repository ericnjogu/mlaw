/**
 * 
 */
package org.martinlaw.test.courtcase;

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
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.LookupService;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.Fee;
import org.martinlaw.bo.MartinlawPerson;
import org.martinlaw.bo.Status;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.bo.courtcase.CourtCaseClient;
import org.martinlaw.bo.courtcase.CourtCaseDate;
import org.martinlaw.bo.courtcase.CourtCaseFee;
import org.martinlaw.bo.courtcase.CourtCaseWitness;
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

	@Override
	protected List<Lifecycle> getSuiteLifecycles() {
		List<Lifecycle> suiteLifecycles = super.getSuiteLifecycles();
		 // needs to be here rather in loadData() since it leads to 'object modified' OJB exceptions
		suiteLifecycles.add(new org.kuali.rice.test.lifecycles.KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/kim/users.xml"));
		//groups do not appear to be needed for testing business objects
		//suiteLifecycles.add(new KEWXmlDataLoaderLifecycle("classpath:org/martinlaw/kim/groups.xml"));
		return suiteLifecycles;
	}

	/* (non-Javadoc)
	 * @see org.kuali.test.KNSTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		//transactions are rolled back, no need to clear manually
		//new SQLDataLoader("classpath:org/martinlaw/bo/clear-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/date-type-default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/case-date-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/note-atts-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/court-case-assignment-test-data.sql", ";").runSql();
		//bo xml files loaded from martinlaw-ModuleBeans(imported in CourtCaseBOTest-context.xml) as part of the data dictionary config
	}

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
        List<CourtCaseClient> clients = kase.getClients();
        assertEquals(1, clients.size());
        CourtCaseClient client = clients.get(0);
        assertEquals("client1", client.getPrincipalName());
        assertEquals("Client", client.getPerson().getFirstName());
        //witness
        List<CourtCaseWitness> witnesses = kase.getWitnesses();
        assertEquals(1, witnesses.size());
        CourtCaseWitness witness = witnesses.get(0);
        assertEquals("witness1", witness.getPrincipalName());
        assertEquals("Witness",witness.getPerson().getFirstName());
        //hearing date
        List<CourtCaseDate> dates = kase.getDates();
        assertEquals(1,dates.size());
        getTestUtils().testMatterDateFields(kase.getDates().get(0));
        //client fees
        assertEquals(2, kase.getFees().size());
        testFeeFields(kase.getFees().get(0));
        // attachments
        assertEquals("number of attachments not the expected quantity", 2, kase.getAttachments().size());
        assertEquals("first attachment name differs", "submission.pdf", kase.getAttachments().get(0).getAttachmentFileName());
        assertEquals("second attachment name differs", "pleading.odt", kase.getAttachments().get(1).getAttachmentFileName());
        // assignment
        getTestUtils().testAssignees(kase.getAssignees());
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
		status.setStatus("filed");
		status.setType(Status.COURT_CASE_TYPE.getKey());
		// save status since it is not updated from the court case - ojb config to prevent object modified errors when the status is changed
		getBoSvc().save(status);
		kase.setStatus(status);
		kase.setName("Good vs Evil");
		getBoSvc().save(kase);
		kase = getBoSvc().findBySinglePrimaryKey(CourtCase.class, kase.getId());
		assertEquals(null, kase.getCourtReference());
		assertEquals("local1", kase.getLocalReference());
		assertEquals(0,kase.getClients().size());
		assertEquals(0,kase.getWitnesses().size());
		assertEquals("filed", kase.getStatus().getStatus());
		log.debug("Created case with id " + kase.getId());
		assertNotNull(kase.getId());
		//create and save client, witness
		CourtCaseClient cl = new CourtCaseClient();
		cl.setMatterId(kase.getId());
		cl.setPrincipalName("somename");//TODO needs to be verified (business rule?)
		List<CourtCaseClient> clts = new ArrayList<CourtCaseClient>(1);
		clts.add(cl);
		kase.setClients(clts);
		
		CourtCaseWitness wit = new CourtCaseWitness();
		wit.setCourtCaseId(kase.getId());
		wit.setPrincipalName("someothername");
		List<CourtCaseWitness> wits = new ArrayList<CourtCaseWitness>(1);
		wits.add(wit);
		kase.setWitnesses(wits);
		
		getBoSvc().save(kase);
		
		kase = getBoSvc().findBySinglePrimaryKey(kase.getClass(), kase.getId());
		assertNotNull(kase.getClients());
		assertEquals(1, kase.getClients().size());
		assertNotNull(kase.getWitnesses());
		assertEquals(1, kase.getWitnesses().size());
	}

	@Test
	public void testCaseLookup() {
		//adapted from  org.kuali.rice.kns.service.LookupServiceTest
		LookupService lookupService = KRADServiceLocatorWeb.getLookupService();
        @SuppressWarnings("rawtypes")
		Map formProps = new HashMap();
        @SuppressWarnings("unchecked")
		Collection<CourtCase> cases = lookupService.findCollectionBySearchHelper(CourtCase.class, formProps, false);
        assertEquals(1, cases.size());
	}
	
	@Test
	/**
	 * tests retrieving a client present in the db, then CRUD ops
	 */
	public void testCaseClient() {
		CourtCaseClient person = new CourtCaseClient();
		person.setMatterId(1001l);
		testMartinlawPersonCRUD(new CourtCaseClient(), "client1", person);
	}

	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testCaseClientNullableFields() {
		CourtCaseClient caseClient = new CourtCaseClient();
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
		testBoAttributesPresent(CourtCaseClient.class.getCanonicalName());
		Class<CourtCaseClient> dataObjectClass = CourtCaseClient.class;
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
	 * test that a court case fee can be retrieved from the database by the primary key
	 */
	public void testCourtCaseFeeRetrieval() {
		Fee fee = getBoSvc().findBySinglePrimaryKey(CourtCaseFee.class, new Long(1001));
		assertNotNull(fee);
		testFeeFields(fee);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * tests that annex type generates errors when non-nullable fields are blank
	 */
	public void testCourtCaseFeeNullableFields() {
		CourtCaseFee fee = new CourtCaseFee();
		//fee.setId(25l);
		getBoSvc().save(fee);
	}
	
	@Test
	/**
	 * tests court case fee CRUD
	 */
	public void testCourtCaseFeeCRUD() {
		CourtCaseFee fee = new CourtCaseFee();
		fee.setMatterId(1001l);
		testFeeCRUD(fee, fee.getClass());
	}
	
	@Test
	/**
	 * test how a name is returned for clients/witnesses who are not yet created as KIM principals
	 */
	public void testCourtCasePersonName() {
		MartinlawPerson client = new CourtCaseClient();
		client.setPrincipalName("clientX");
		// should there be an error here - if the principalName does not represent a existing principal? 
		assertNotNull(client.getPerson().getName());
		assertNull("should be null", client.getPerson().getPrincipalId());
	}
}
