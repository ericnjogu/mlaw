package org.martinlaw.test.courtcase;

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
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.MatterClient;
import org.martinlaw.bo.MatterConsideration;
import org.martinlaw.bo.MatterEvent;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.bo.Status;
import org.martinlaw.test.MartinlawTestsBase;

public class MatterBoTest extends MartinlawTestsBase {

	private Log log = LogFactory.getLog(getClass());
	private String matterTags = "peace, harmony";

	public MatterBoTest() {
		super();
	}

	/**
	 * test retrieving a matter BO
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMatterRetrieve() throws Exception {
	    Matter matter = getBoSvc().findBySinglePrimaryKey(getDataObjectClass(), getDataObjectPrimaryKey());
	    assertNotNull("matter should not be null", matter);
	    testRetrievedMatterFields(matter);
	}

	/**
	 * test shared matter fields
	 * <p>most of the test data is the same as court case to make it easier to test</p>
	 * @param matter
	 */
	public void testRetrievedMatterFields(Matter matter) {
		assertEquals("parties differ", "Barca vs Man U (2011)", matter.getName());
	    assertEquals("local ref differs", "l1", matter.getLocalReference());
	    assertEquals("tags differ", "testing, sql, sample", matter.getTags());
	    // status
	    assertNotNull("status should not be null", matter.getStatus());
	    assertEquals("status differs", "hearing", matter.getStatus().getStatus());
	    // case client
	    List<MatterClient> clients = matter.getClients();
	    assertEquals("number of clients differs", 2, clients.size());
	    MatterClient client = clients.get(0);
	    assertEquals("client principal name differs", "client1", client.getPrincipalName());
	    assertEquals("client first name differs", "Client", client.getPerson().getFirstName());
	    // event
	    List<MatterEvent> dates = matter.getEvents();
	    assertEquals("number of dates differs", 1, dates.size());
	    getTestUtils().testRetrievedMatterEventFields(matter.getEvents().get(0));
	    // attachments
	    assertEquals("number of attachments not the expected quantity", 2, matter.getAttachments().size());
	    assertEquals("first attachment name differs", "submission.pdf", matter.getAttachments().get(0).getAttachmentFileName());
	    assertEquals("second attachment name differs", "pleading.odt", matter.getAttachments().get(1).getAttachmentFileName());
	    // assignees
	    assertNotNull("assignees should not be null",matter.getAssignees());
	    assertEquals("number of assignees differs", 1, matter.getAssignees().size());
	    assertEquals("assignee principal name differs", "pauline_njogu", matter.getAssignees().get(0).getPrincipalName());
	    // work
	    List<MatterWork> work = matter.getWork();
	    getTestUtils().testWorkList(work);
	    // consideration
	    assertEquals("number of considerations differs", 1, matter.getConsiderations().size());
	    getTestUtils().testRetrievedConsiderationFields(matter.getConsiderations().get(0));
	    // client
	    getTestUtils().testMatterClient(matter, "Client");
	}

	@Test
	public void testMatterCRUD() throws Exception {
		final String localReference = "local1";
		String statusText = "filed";
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
		Matter matter = getTestMatter(localReference, statusText, name);
		// C
		getBoSvc().save(matter);
		// R
		matter = getBoSvc().findBySinglePrimaryKey(getDataObjectClass(), matter.getId());
		matter.refreshNonUpdateableReferences(); //without this, case status (object) is null
		assertEquals("case name differs", name, matter.getName());
		assertEquals("local reference differs", localReference, matter.getLocalReference());
		assertNotNull("clients should not be null", matter.getClients());
		assertEquals("number of clients expected differs", 2, matter.getClients().size());
		
		assertNotNull("status id should not be null", matter.getStatusId());
		assertNotNull("status should not be null", matter.getStatus());
		
		assertEquals("status differs", statusText, matter.getStatus().getStatus());
		assertNotNull("considerations should not be null", matter.getConsiderations());
		assertEquals("default number of considerations differs", 2, matter.getConsiderations().size());
		log.debug("Created case with id " + matter.getId());
		assertNotNull("case id should not be null", matter.getId());
		
		assertEquals("ojb concrete class differs", getDataObjectClass().getCanonicalName(), matter.getConcreteClass());
		assertEquals("tags differ", matterTags, matter.getTags());
		additionalTestsForCreatedMatter(matter);
		// U
		matter.getConsiderations().add((MatterConsideration) getTestUtils().getTestConsideration());
		
		getBoSvc().save(matter);
		
		matter = getBoSvc().findBySinglePrimaryKey(matter.getClass(), matter.getId());
		
		getTestUtils().testConsiderationFields(matter.getConsiderations().get(0));
		getTestUtils().testMatterClient(matter, getTestUtils().getTestClientFirstName());
		
		// D
		getBoSvc().delete(matter);
		assertNull("matter should have been deleted", getBoSvc().findBySinglePrimaryKey(getDataObjectClass(), matter.getId()));
		for (MatterClient client: matter.getClients()) {
			assertNull("client should have been deleted", getBoSvc().findBySinglePrimaryKey(MatterClient.class, client.getId()));
		}
		assertNull("consideration should have been deleted", getBoSvc().findBySinglePrimaryKey(MatterConsideration.class, 
				matter.getConsiderations().get(0).getId()));
		additionalTestsForDeletedMatter(matter);
	}

	/**
	 * create a test court case or descendant
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	protected Matter getTestMatter(String localReference, String statusText, String matterName) throws InstantiationException,
			IllegalAccessException {
		Matter matter = getDataObjectClass().newInstance();
		matter.setLocalReference(localReference);
		matter.setName(matterName);
		matter.setTags(matterTags );
	
		Status status = new Status();
		status.setStatus(statusText);
		// save status since it is not updated from the court case - ojb config to prevent object modified errors when the status is changed
		getBoSvc().save(status);
		status.refresh();
		matter.setStatusId(status.getId());
		matter.setClientPrincipalName(getTestUtils().getTestClientPrincipalName());
		getTestUtils().addClients(matter);
		
		return matter;
	}

	@Test
	public void testMatterLookup() {
		//adapted from  org.kuali.rice.kns.service.LookupServiceTest
		LookupService lookupService = KRADServiceLocatorWeb.getLookupService();
	    @SuppressWarnings("rawtypes")
		Map formProps = new HashMap();
	    @SuppressWarnings("unchecked")
	    // includes any land cases
		Collection<Matter> matter = lookupService.findCollectionBySearchHelper(getDataObjectClass(), formProps, false);
	    assertEquals("number of matters differs", getExpectedLookupCount(), matter.size());
	}

	@Test
	public void testMatterAttributes() {
		testBoAttributesPresent(getDataObjectClass().getCanonicalName());
		verifyMaintDocDataDictEntries(getDataObjectClass());
	}

	/**
	 * 
	 * @return the data object (BO) class
	 */
	public Class<? extends Matter> getDataObjectClass() {
		return Matter.class;
	}

	/**
	 * 
	 * @return the primary key to retrive the data object from the db
	 */
	public Long getDataObjectPrimaryKey() {
		return 1011l;
	}

	/**
	 * 
	 * @return the expected number of results on lookup
	 */
	public int getExpectedLookupCount() {
		return 11;
	}

	/**
	 * for the court case created during CRUD, additional tests/verification
	 */
	public void additionalTestsForCreatedMatter(Matter matter) {
		// default does nothing;
	}
	
	/**
	 * for the court case deleted during CRUD, additional tests/verification
	 */
	public void additionalTestsForDeletedMatter(Matter matter) {
		// default does nothing;
	}
}