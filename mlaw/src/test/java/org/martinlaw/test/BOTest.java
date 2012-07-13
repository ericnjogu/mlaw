/**
 * 
 */
package org.martinlaw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.datadictionary.DataObjectEntry;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.LookupService;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.ConveyanceAnnex;
import org.martinlaw.bo.ConveyanceAnnexType;
import org.martinlaw.bo.ConveyanceAttachment;
import org.martinlaw.bo.ConveyanceType;
import org.martinlaw.bo.CourtCase;
import org.martinlaw.bo.CourtCaseClient;
import org.martinlaw.bo.CourtCaseFee;
import org.martinlaw.bo.CourtCasePerson;
import org.martinlaw.bo.CourtCaseWitness;
import org.martinlaw.bo.Fee;
import org.martinlaw.bo.HearingDate;
import org.martinlaw.bo.MartinlawPerson;
import org.martinlaw.bo.Status;
import org.martinlaw.keyvalues.ConveyanceStatusKeyValues;
import org.martinlaw.keyvalues.CourtCaseStatusKeyValues;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * test the data dictionary
 * 
 * @author mugo
 */
//@BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
public class BOTest extends MartinlawTestsBase {
	private Log log = LogFactory.getLog(getClass());
	private org.kuali.rice.krad.service.BusinessObjectService boSvc;

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
		new SQLDataLoader("classpath:org/martinlaw/bo/sql/default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/bo/insert-test-data.sql", ";").runSql();
		//bo xml files loaded from martinlaw-ModuleBeans(imported in BOTest-context.xml) as part of the data dictionary config
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.test.KEWTestCase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		boSvc = KRADServiceLocator.getBusinessObjectService();
	}

	/**
	 * test saving, retrieving a case BO
	 * 
	 * @throws Exception
	 */
	@Test
    public void testCaseRetrieveEdit() throws Exception {
        //BusinessObjectService boSvc = KRADServiceLocator.getBusinessObjectService();
        CourtCase kase = boSvc.findBySinglePrimaryKey(CourtCase.class, new Long(1001));
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
        List<HearingDate> dates = kase.getHearingDates();
        assertEquals(1,dates.size());
        testHearingDateFields(kase.getHearingDates().get(0));
        //client fees
        assertEquals(2, kase.getFees().size());
        testCourtCaseFeeFields(kase.getFees().get(0));
	}
	
	@Test
	/**
	 * create a new court case, save it, add collection objects and verify existence
	 * 
	 * @throws Exception
	 */
	public void testCaseSaveRetrieve() throws Exception {
		//delete sample data
		//new SQLDataLoader("classpath:org/martinlaw/bo/clear-test-data.sql", ";").runSql();
		CourtCase kase = new CourtCase();
		kase.setLocalReference("local1");
		Status status = new Status();
		status.setStatus("filed");
		status.setType(Status.COURT_CASE_TYPE.getKey());
		kase.setStatus(status);
		kase.setName("Good vs Evil");
		boSvc.save(kase);
		kase = boSvc.findBySinglePrimaryKey(CourtCase.class, kase.getId());
		assertEquals(null, kase.getCourtReference());
		assertEquals("local1", kase.getLocalReference());
		assertEquals(0,kase.getClients().size());
		assertEquals(0,kase.getWitnesses().size());
		assertEquals("filed", kase.getStatus().getStatus());
		log.debug("Created case with id " + kase.getId());
		assertNotNull(kase.getId());
		//create and save client, witness
		CourtCaseClient cl = new CourtCaseClient();
		cl.setCourtCaseId(kase.getId());
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
		
		boSvc.save(kase);
		
		kase = boSvc.findBySinglePrimaryKey(kase.getClass(), kase.getId());
		assertNotNull(kase.getClients());
		assertEquals(1, kase.getClients().size());
		assertNotNull(kase.getWitnesses());
		assertEquals(1, kase.getWitnesses().size());
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.rice.test.RiceTestCase#getBaseDir()
	 */
	/*@Override
	protected String getBaseDir() {
		// TODO Auto-generated method stub
		return "/home/mugo/apps/rice-1.0.3.1-src/impl";
	}*/

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
		testCourtCasePersonCRUD(new CourtCaseClient(), "client1");
	}

	/**
	 * a common method to perform CRUD on objects with {@link CourtCasePerson} as the parent
	 * 
	 * @param principalName - the principal name e.g. enjogu
	 */
	protected <T extends BusinessObject> void testCourtCasePersonCRUD(T t, String principalName) {
		MartinlawPerson casePersonRetrieve = (MartinlawPerson) boSvc.findBySinglePrimaryKey(t.getClass(), new Long(1001));
		assertNotNull(casePersonRetrieve);
		assertEquals(principalName, casePersonRetrieve.getPrincipalName());
		// C
		CourtCasePerson casePerson = (CourtCasePerson) t;
		casePerson.setPrincipalName("mkoobs");
		casePerson.setCourtCaseId(1001l);
		boSvc.save(casePerson);
		// R
		casePerson = (CourtCasePerson) boSvc.findBySinglePrimaryKey(t.getClass(), casePerson.getId());
		assertNotNull(casePerson);
		// U
		casePerson.setPrincipalName("mogs");
		boSvc.save(casePerson);
		casePerson.refresh();
		// D
		boSvc.delete(casePerson);
		assertNull((MartinlawPerson) boSvc.findBySinglePrimaryKey(t.getClass(), casePerson.getId()));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testCaseClientNullableFields() {
		CourtCaseClient caseClient = new CourtCaseClient();
		boSvc.save(caseClient);
	}
	
	@Test
	/**
	 * tests retrieving a client present in the db, then CRUD ops
	 */
	public void testCaseWitness() {
		testCourtCasePersonCRUD(new CourtCaseWitness(), "witness1");
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * tests that non nullable fields are checked
	 */
	public void testCaseWitnessNullableFields() {
		CourtCaseWitness caseClient = new CourtCaseWitness();
		boSvc.save(caseClient);
	}
	
	@Test
	/**
	 * test that the court case status is loaded into the data dictionary
	 */
	public void testCourtCaseStatusAttributes() {
		testBoAttributesPresent("org.martinlaw.bo.Status");
		
		Class<Status> dataObjectClass = Status.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}
	
	@Test
	/**
	 * test that {@link ConveyanceType} is loaded into the data dictionary
	 */
	public void testConveyanceTypeAttributes() {
		testBoAttributesPresent("org.martinlaw.bo.ConveyanceType");
		
		Class<ConveyanceType> dataObjectClass = ConveyanceType.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}
	
	@Test
	/**
	 * test that {@link ConveyanceAnnexType} is loaded into the data dictionary
	 */
	public void testConveyanceAnnexTypeAttributes() {
		testBoAttributesPresent("org.martinlaw.bo.ConveyanceAnnexType");
		
		Class<ConveyanceAnnexType> dataObjectClass = ConveyanceAnnexType.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}
	
	@Test
	/**
	 * test that {@link ConveyanceAttachment} is loaded into the data dictionary
	 */
	public void testConveyanceAttachmentAttributes() {
		testBoAttributesPresent("org.martinlaw.bo.ConveyanceAttachment");
	}
	
	@Test
	/**
	 * test that {@link ConveyanceAnnex} is loaded into the data dictionary
	 */
	public void testConveyanceAnnexAttributes() {
		testBoAttributesPresent("org.martinlaw.bo.ConveyanceAnnex");
	}
	

	/**
	 * check for lookup, inquiry, maint view definitions, maintenance entry def
	 * 
	 * @param dataObjectClass - the data object class
	 */
	protected void verifyMaintDocDataDictEntries(
			Class<?> dataObjectClass) {
		verifyInquiryLookup(dataObjectClass);
		assertTrue(KRADServiceLocatorWeb.getViewDictionaryService().isMaintainable(dataObjectClass));
		assertNotNull(KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getMaintenanceDocumentEntryForBusinessObjectClass(dataObjectClass));
	}

	/**
	 * check for lookup, inquiry view definitions
	 * 
	 * @param dataObjectClass - the data object class used in the definitions
	 */
	protected void verifyInquiryLookup(Class<?> dataObjectClass) {
		assertTrue(KRADServiceLocatorWeb.getViewDictionaryService().isInquirable(dataObjectClass));
		assertTrue(KRADServiceLocatorWeb.getViewDictionaryService().isLookupable(dataObjectClass));
	}

	/**
	 * test whether a business object entry can be retrieved. contrived after null pointer exceptions
	 * with court case status
	 * could be modified to return the bo entry for specific tests
	 * 
	 * @param className - fully qualified class name
	 */
	private void testBoAttributesPresent(String className) {
		DataObjectEntry dataObject = KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getDataObjectEntry(className);
		assertNotNull(dataObject);
		assertNotNull(dataObject.getAttributeNames());
	}
	
	@Test
	/**
	 * test that the court case is loaded into the data dictionary
	 */
	public void testCourtCaseAttributes() {
		testBoAttributesPresent("org.martinlaw.bo.CourtCase");
		Class<CourtCase> dataObjectClass = CourtCase.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}
	
	@Test
	/**
	 * test that the client is loaded into the data dictionary
	 */
	public void testClientAttributes() {
		testBoAttributesPresent("org.martinlaw.bo.CourtCaseClient");
		Class<CourtCaseClient> dataObjectClass = CourtCaseClient.class;
		verifyInquiryLookup(dataObjectClass);
	}
	
	@Test
	/**
	 * test that the witness is loaded into the data dictionary
	 */
	public void testWitnessAttributes() {
		testBoAttributesPresent("org.martinlaw.bo.CourtCaseWitness");
		Class<CourtCaseWitness> dataObjectClass = CourtCaseWitness.class;
		verifyInquiryLookup(dataObjectClass);
	}
	
	@Test
	/**
	 * validate data dictionary as it does not seem to happen when unit test env is started up
	 * adapted from org.kuali.rice.kns.config.KNSConfigurer
	 */
	public void validateDataDictionary() {
		KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().validateDD(true);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * tests non nullable fields are checked
	 */
	public void testHearingDateNullableFields() {
		HearingDate date = new HearingDate();
		boSvc.save(date);
	}
	
	private void testHearingDateFields(HearingDate hd) {
		assertEquals("first hearing date",hd.getComment());
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(hd.getDate().getTime());
		assertEquals(2011,cal.get(Calendar.YEAR));
		assertEquals(Calendar.JUNE, cal.get(Calendar.MONTH));
		assertEquals(1, cal.get(Calendar.DATE));
	}
	
	@Test
	/**
	 * tests that a hearing date, inserted via an sql script in {@link #loadSuiteTestData()} can be retrieved
	 */
	public void testHearingDateRetrieve() {
		HearingDate date = boSvc.findBySinglePrimaryKey(HearingDate.class, new Long(1001));
		assertNotNull(date);
		testHearingDateFields(date);
	}
	
	@Test
	/**
	 * tests that hearing date CRUD ops
	 */
	public void testHearingDateCRUD() {
		Date date = new Date(Calendar.getInstance().getTimeInMillis());
		HearingDate hearingDate = new HearingDate(date, "soon", 1001l);
		// C
		boSvc.save(hearingDate);
		// R
		hearingDate =  boSvc.findBySinglePrimaryKey(HearingDate.class, hearingDate.getId());
		// U
		hearingDate.setComment("later");
		boSvc.save(hearingDate);
		hearingDate.refresh();
		assertEquals("later", hearingDate.getComment());
		// D
		boSvc.delete(hearingDate);
		assertNull(boSvc.findBySinglePrimaryKey(HearingDate.class, hearingDate.getId()));
	}
	
	@Test
	/**
	 * test that a court case fee can be retrieved from the database by the primary key
	 */
	public void testCourtCaseFeeRetrieval() {
		Fee fee = boSvc.findBySinglePrimaryKey(CourtCaseFee.class, new Long(1001));
		assertNotNull(fee);
		testCourtCaseFeeFields(fee);
	}
	
	/**
	 * convenience method to verify fee attribute values
	 * 
	 * @param fee - the test fee
	 */
	private void testCourtCaseFeeFields(Fee fee) {
		log.info("fee amount is: " + fee.getAmount().toPlainString());
		assertEquals("2500.58", fee.getAmount().toPlainString());
		assertEquals("received from karateka", fee.getDescription());
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(fee.getDate().getTime());
		assertEquals(2011,cal.get(Calendar.YEAR));
		assertEquals(Calendar.JUNE, cal.get(Calendar.MONTH));
		assertEquals(12, cal.get(Calendar.DATE));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * tests that annex type generates errors when non-nullable fields are blank
	 */
	public void testFeeNullableFields() {
		CourtCaseFee fee = new CourtCaseFee();
		fee.setId(25l);
		boSvc.save(fee);
	}
	
	@Test
	/**
	 * tests court case fee CRUD
	 */
	public void testCourtCaseFeeCRUD() {
		CourtCaseFee fee = new CourtCaseFee();
		/*long id = 25l;
		fee.setId(id);*/
		BigDecimal amount = new BigDecimal(1000);
		fee.setAmount(amount);
		fee.setCourtCaseId(1001l);
		fee.setDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		// leave description blank
		//save
		boSvc.save(fee);
		//retrieve
		fee = boSvc.findBySinglePrimaryKey(CourtCaseFee.class, fee.getId());
		//fee.refresh();
		assertNotNull(fee);
		assertEquals(0, amount.compareTo(new BigDecimal(1000)));
		//edit
		amount = new BigDecimal(900); //discount 
		fee.setAmount(amount);
		boSvc.save(fee);
		//confirm change
		// fee = boSvc.findBySinglePrimaryKey(CourtCaseFee.class, id);
		fee.refresh();
		assertEquals(0, amount.compareTo(new BigDecimal(900)));
		//delete
		boSvc.delete(fee);
		assertNull(boSvc.findBySinglePrimaryKey(CourtCaseFee.class, fee.getId()));
		
	}
	
	@Test
	/**
	 * test that default annex types are retrieved ok
	 */
	public void testStatusRetrieve() {
		List<Status> caseStatuses = (List<Status>) boSvc.findAll(Status.class);
		assertNotNull(caseStatuses);
		assertEquals(5, caseStatuses.size());
		Status status = boSvc.findBySinglePrimaryKey(Status.class, new Long(1003));
		assertNotNull(status);
		assertEquals("closed", status.getStatus());
		assertEquals(Status.ANY_TYPE.getKey(), status.getType());
	}
	
	@Test
	/**
	 * tests CRUD on annex type
	 */
	public void testStatusCRUD() {
		// create
		Status status = new Status();
		status.setStatus("pending");
		status.setType(Status.ANY_TYPE.getKey());
		boSvc.save(status);
		//refresh
		status.refresh();
		// retrieve
		assertEquals("the courtCaseStatus does not match", "pending", status.getStatus());
		//update
		status.setStatus("appealed");
		boSvc.save(status);
		//refresh
		status.refresh();
		assertEquals("the courtCaseStatus does not match", "appealed", status.getStatus());
		// delete
		boSvc.delete(status);
		assertNull(boSvc.findBySinglePrimaryKey(Status.class, status.getId()));
	}
	
	/**
	 * convenience method to retrieve and verify the value of a CourtCaseStatus
	 * @param id - the CourtCaseStatus id
	 * @param value - the expected value
	 * @return - the retrieved CourtCaseStatus
	 */
	protected Status retrieveandVerifyCourtCaseStatusValue(long id, String value) {
		Status CourtCaseStatusRetrieved = null;
		CourtCaseStatusRetrieved = boSvc.findBySinglePrimaryKey(Status.class, id);
		assertNotNull("the new annex type should have been saved to the db", CourtCaseStatusRetrieved);
		assertEquals("the annex type value does not match", value, CourtCaseStatusRetrieved.getStatus());
		return CourtCaseStatusRetrieved;
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * tests that annex type generates errors when non-nullable fields are blank
	 */
	public void testCourtCaseStatusNullableFields() {
		Status courtCaseStatus = new Status();
		courtCaseStatus.setId(25l);
		boSvc.save(courtCaseStatus);
	}
	
	@Test
	/**
	 * test CRUD for {@link ConveyanceAnnexType}
	 */
	public void testConveyanceAnnexTypeCRUD() {
		// retrieve object populated via sql script
		ConveyanceAnnexType convAnnexType = boSvc.findBySinglePrimaryKey(ConveyanceAnnexType.class, 1001l);
		assertNotNull(convAnnexType);
		assertEquals("land board approval", convAnnexType.getName());
		//C
		convAnnexType = new ConveyanceAnnexType();
		convAnnexType.setName("signed affidavit");
		boSvc.save(convAnnexType);
		//R
		convAnnexType.refresh();
		//U
		convAnnexType.setDescription("signed before a commissioner of oaths");
		convAnnexType.refresh();
		assertNotNull(convAnnexType.getDescription());
		//D
		boSvc.delete(convAnnexType);
		assertNull(boSvc.findBySinglePrimaryKey(ConveyanceAnnexType.class, convAnnexType.getId()));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * test that non nullable fields for {@link ConveyanceAnnexType} throw a DB exception
	 */
	public void testConveyanceAnnexTypeNullableFields() {
		ConveyanceAnnexType convAnnexType = new ConveyanceAnnexType();
		boSvc.save(convAnnexType);
	}
	
	@Test
	/**
	 * test CRUD for {@link ConveyanceAnnexType}
	 */
	public void testConveyanceTypeCRUD() {
		// retrieve object populated via sql script
		ConveyanceType convType = boSvc.findBySinglePrimaryKey(ConveyanceType.class, 1001l);
		assertNotNull(convType);
		assertEquals("Sale of Urban Land", convType.getName());
		//C
		convType = new ConveyanceType();
		convType.setName("Lease of Gov Land");
		boSvc.save(convType);
		//R
		convType.refresh();
		//U
		convType.setDescription("hiring land from go.ke");
		convType.refresh();
		assertNotNull(convType.getDescription());
		//D
		boSvc.delete(convType);
		assertNull(boSvc.findBySinglePrimaryKey(ConveyanceAnnexType.class, convType.getId()));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * test that non nullable fields for {@link ConveyanceAnnexType} throw a DB exception
	 */
	public void testConveyanceTypeNullableFields() {
		ConveyanceType convType = new ConveyanceType();
		boSvc.save(convType);
	}
	
	@Test
	/**
	 * test how a name is returned for clients/witnesses who are not yet created as KIM principals
	 */
	public void testCourtCasePersonName() {
		MartinlawPerson client = new CourtCaseClient();
		client.setPrincipalName("clientX");
		// should there be an error here - if the principalName does not represent a valid person? 
		assertNotNull(client.getPerson().getName());
	}
	
	@Test
	/**
	 * test that court case status type key values returns the correct number
	 */
	public void testCourtCaseStatusKeyValues() {
		CourtCaseStatusKeyValues keyValues = new CourtCaseStatusKeyValues();
		// expected 2 court case types and two of any type, plus a blank one
		assertEquals(5, keyValues.getKeyValues().size());
	}
	
	@Test
	/**
	 * test that conveyance status type key values returns the correct number
	 */
	public void testConveyanceStatusKeyValues() {
		ConveyanceStatusKeyValues keyValues = new ConveyanceStatusKeyValues();
		// expected one Conveyance type and two of 'any' type, plus a blank one
		assertEquals(4, keyValues.getKeyValues().size());
	}
	
	@Test
	/**
	 * test CRUD operations on {@link ConveyanceAttachment}
	 */
	public void testConveyanceAttachmentCRUD() {
		// retrieve object inserted via sql
		ConveyanceAttachment convAtt = boSvc.findBySinglePrimaryKey(ConveyanceAttachment.class, 1001l);
		// C
		assertNotNull(convAtt);
		convAtt = new ConveyanceAttachment();
		convAtt.setAttachmentId(1001l);
		convAtt.setConveyanceAnnexId(1001l);
		boSvc.save(convAtt);
		// R
		convAtt.refresh();
		// U
		convAtt.setAttachmentId(1002l);
		convAtt.refresh();
		// D
		boSvc.delete(convAtt);
		assertNull(boSvc.findBySinglePrimaryKey(ConveyanceAttachment.class, convAtt.getId()));	
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * test that null fields cause an error
	 */
	public void testConveyanceAttachmentNullableFields() {
		ConveyanceAttachment convAtt = new ConveyanceAttachment();
		boSvc.save(convAtt);
	}
	
	@Test
	/**
	 * test CRUD operations on {@link ConveyanceAnnex}
	 */
	public void testConveyanceAnnexCRUD() {
		// check number of existing conveyance atts
		int existingConvAtts = boSvc.findAll(ConveyanceAttachment.class).size();
		// retrieve object inserted via sql
		ConveyanceAnnex convAnnex = boSvc.findBySinglePrimaryKey(ConveyanceAnnex.class, 1001l);
		// C
		assertNotNull(convAnnex);
		convAnnex = new ConveyanceAnnex();
		convAnnex.setConveyanceAnnexTypeId(1001l);
		convAnnex.setConveyanceId(1001l);
		// add attachments
		List<ConveyanceAttachment> atts = new ArrayList<ConveyanceAttachment>();
		ConveyanceAttachment convAtt = new ConveyanceAttachment();
		convAtt.setAttachmentId(1001l);
		atts.add(convAtt);
		convAtt = new ConveyanceAttachment();
		convAtt.setAttachmentId(1002l);
		atts.add(convAtt);
		convAnnex.setAttachments(atts);
		boSvc.save(convAnnex);
		// R
		convAnnex.refresh();
		assertEquals(2, convAnnex.getAttachments().size());
		assertEquals(existingConvAtts + 2, boSvc.findAll(ConveyanceAttachment.class).size());
		// U
		boSvc.delete(convAnnex.getAttachments().get(0));
		convAnnex.refresh();
		assertEquals(1, convAnnex.getAttachments().size());
		assertEquals(existingConvAtts + 1, boSvc.findAll(ConveyanceAttachment.class).size());
		// D
		boSvc.delete(convAnnex);
		assertNull(boSvc.findBySinglePrimaryKey(ConveyanceAnnex.class, convAnnex.getId()));
		assertEquals(existingConvAtts, boSvc.findAll(ConveyanceAttachment.class).size());
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * test that null fields cause an error
	 */
	public void testConveyanceAnnexNullableFields() {
		ConveyanceAnnex convAnnex = new ConveyanceAnnex();
		boSvc.save(convAnnex);
	}
}
