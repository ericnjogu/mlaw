/**
 * 
 */
package org.martinlaw.test.conveyance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.MatterClient;
import org.martinlaw.bo.conveyance.Client;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.conveyance.ConveyanceAnnex;
import org.martinlaw.bo.conveyance.ConveyanceAttachment;
import org.martinlaw.bo.conveyance.Fee;
import org.martinlaw.keyvalues.ConveyanceAnnexTypeKeyValuesBase;
import org.martinlaw.keyvalues.ConveyanceAnnexTypeKeyValuesMaint;
import org.martinlaw.keyvalues.ConveyanceStatusKeyValues;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * various tests for {@link Conveyance}
 * @author mugo
 *
 */
public class ConveyanceBOTest extends ConveyanceBOTestBase {
	@Test
	/**
	 * test CRUD ops on {@link Conveyance}
	 */
	public void testConveyanceCRUD() {
		// C
		Conveyance conv = getTestUtils().getTestConveyance();
		// add client
		Client client = new Client();
		String principalName = "clientX";
		client.setPrincipalName(principalName);
		conv.getClients().add(client);
		// add fee
		/*Fee fee = new Fee();
		fee.setAmount(new BigDecimal(5000));
		fee.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
		conv.getFees().add(fee);*/
		// add annex
		ConveyanceAnnex annex = new ConveyanceAnnex();
		annex.setConveyanceAnnexTypeId(1003l);
		ConveyanceAttachment convAtt = new ConveyanceAttachment();
		Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
		convAtt.setNoteTimestamp(timestamp.toString());
		annex.getAttachments().add(convAtt);
		//need to set annexes externally since getAnnexes() will generate a default listing for th etype
		List<ConveyanceAnnex> annexes = new ArrayList<ConveyanceAnnex>();
		annexes.add(annex);
		conv.setAnnexes(annexes);
		
		getBoSvc().save(conv);
		
		// R
		conv.refresh();

		conv.getAnnexes().get(0).refreshNonUpdateableReferences();
		assertEquals(getTestUtils().getTestConveyance().getName(), conv.getName());
		assertEquals(1, conv.getClients().size());
		assertEquals(principalName, conv.getClients().get(0).getPrincipalName());
		/*assertEquals(1, conv.getFees().size());*/
		assertEquals(1, conv.getAnnexes().size());
		assertEquals(timestamp.toString(), conv.getAnnexes().get(0).getAttachments().get(0).getNoteTimestamp());
		
		// U
		String name2 = "EN/C010";
		conv.setName(name2);
		getBoSvc().delete(conv.getAnnexes().get(0));

		conv.refresh();
		assertEquals(name2, conv.getName());
		// should create new annexes to fit into the existing annex types (1)
		assertEquals(1, conv.getAnnexes().size());
		assertNull(conv.getAnnexes().get(0).getId());
		
		// D
		getBoSvc().delete(conv);
		assertNull("conveyance client should have been deleted", 
				getBoSvc().findBySinglePrimaryKey(Client.class, conv.getClients().get(0).getId()));
		/*assertNull("conveyance fee should have been deleted", 
				getBoSvc().findBySinglePrimaryKey(Fee.class, conv.getFees().get(0).getId()));*/
	}
	
	@Test
	/**
	 * test retrieving the {@link Conveyance} populated from sql
	 */
	public void testConveyanceRetrieve() {
		Conveyance conv = getBoSvc().findBySinglePrimaryKey(Conveyance.class, 1001l);
		assertNotNull(conv);
		assertEquals("Sale of LR4589", conv.getName());
		assertEquals("c1", conv.getLocalReference());
		assertEquals("Sale of Urban Land", conv.getType().getName());
		assertEquals("pending", conv.getStatus().getStatus());
		// clients
		assertEquals(2, conv.getClients().size());
		assertEquals("client1", conv.getClients().get(0).getPrincipalName());
		// conveyance annexes
		assertEquals(1, conv.getAnnexes().size());
		assertEquals(2, conv.getAnnexes().get(0).getAttachments().size());
		assertEquals("2012-07-19 00:00:00", conv.getAnnexes().get(0).getAttachments().get(0).getNoteTimestamp());
		getTestUtils().testAssignees(conv.getAssignees());
		
		getTestUtils().testClientFeeList(conv.getFees());
	}
	
	@Test
	/**
	 * test that {@link Conveyance} is loaded into the data dictionary
	 */
	public void testConveyanceAttributes() {
		testBoAttributesPresent(Conveyance.class.getCanonicalName());
		
		Class<Conveyance> dataObjectClass = Conveyance.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}
	
	@Test
	/**
	 * test that {@link ConveyanceAttachment} is loaded into the data dictionary
	 */
	public void testConveyanceAttachmentAttributes() {
		testBoAttributesPresent(ConveyanceAttachment.class.getCanonicalName());
	}
	
	@Test
	/**
	 * test that {@link ConveyanceAnnex} is loaded into the data dictionary
	 */
	public void testConveyanceAnnexAttributes() {
		testBoAttributesPresent(ConveyanceAnnex.class.getCanonicalName());
	}
	
	@Test
	/**
	 * test that the conveyance fee is loaded into the data dictionary
	 */
	public void testConveyanceFeeAttributes() {
		testBoAttributesPresent(Fee.class.getCanonicalName());
	}
	
	
	@Test()
	/**
	 * test that {@link ConveyanceAnnexTypeKeyValuesMaint} works as expected
	 */
	public void testConveyanceAnnexTypeKeyValues() {
		ConveyanceAnnexTypeKeyValuesBase keyValues = new ConveyanceAnnexTypeKeyValuesMaint();
		
		MaintenanceForm maintForm = mock(MaintenanceForm.class);
		Conveyance conv = new Conveyance();
		conv.setTypeId(1001l);
		
		MaintenanceDocumentBase doc = mock(MaintenanceDocumentBase.class);
		when(maintForm.getDocument()).thenReturn(doc);
		MaintainableImpl maintainable = mock(MaintainableImpl.class);
		when(doc.getNewMaintainableObject()).thenReturn(maintainable);
		when(maintainable.getDataObject()).thenReturn(conv);
		
		List<KeyValue> result = keyValues.getKeyValues(maintForm);
		// expect two non blank key values
		getTestUtils().testAnnexTypeKeyValues(result);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * test that null fields cause an error
	 */
	public void testConveyanceAnnexNullableFields() {
		ConveyanceAnnex convAnnex = new ConveyanceAnnex();
		getBoSvc().save(convAnnex);
	}
	
	@Test
	/**
	 * test CRUD operations on {@link ConveyanceAnnex}
	 */
	public void testConveyanceAnnexCRUD() {
		// check number of existing conveyance atts
		int existingConvAtts = getBoSvc().findAll(ConveyanceAttachment.class).size();
		// retrieve object inserted via sql
		ConveyanceAnnex convAnnex = getBoSvc().findBySinglePrimaryKey(ConveyanceAnnex.class, 1001l);
		assertNotNull("should not be null", convAnnex);
		assertEquals("should have 2 attachments", 2, convAnnex.getAttachments().size());
		assertNotNull(" first conv att's attachment should not be null", convAnnex.getAttachments().get(0).getAttachment());
		assertEquals(" first conv att's attachment filename does not match", "filename.ext", convAnnex.getAttachments().get(0).getAttachment().getAttachmentFileName());
		assertNull(" second conv att's attachment should be null", convAnnex.getAttachments().get(1).getAttachment());
		// C
		convAnnex = new ConveyanceAnnex();
		convAnnex.setConveyanceAnnexTypeId(1001l);
		convAnnex.setConveyanceId(1001l);
		// add attachments
		List<ConveyanceAttachment> atts = new ArrayList<ConveyanceAttachment>();
		ConveyanceAttachment convAtt = new ConveyanceAttachment();
		convAtt.setNoteTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()).toString());
		atts.add(convAtt);
		convAtt = new ConveyanceAttachment();
		convAtt.setNoteTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()).toString());
		atts.add(convAtt);
		convAnnex.setAttachments(atts);
		getBoSvc().save(convAnnex);
		// R
		convAnnex.refresh();
		assertEquals(2, convAnnex.getAttachments().size());
		assertEquals(existingConvAtts + 2, getBoSvc().findAll(ConveyanceAttachment.class).size());
		assertEquals("land board approval", convAnnex.getType().getName());
		assertEquals(new Long(1001l), convAnnex.getType().getConveyanceTypeId());
		// fail("why?");
		// U
		getBoSvc().delete(convAnnex.getAttachments().get(0));
		convAnnex.refresh();
		assertEquals(1, convAnnex.getAttachments().size());
		assertEquals(existingConvAtts + 1, getBoSvc().findAll(ConveyanceAttachment.class).size());
		// D
		getBoSvc().delete(convAnnex);
		assertNull(getBoSvc().findBySinglePrimaryKey(ConveyanceAnnex.class, convAnnex.getId()));
		assertEquals(existingConvAtts, getBoSvc().findAll(ConveyanceAttachment.class).size());
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
		ConveyanceAttachment convAtt = getBoSvc().findBySinglePrimaryKey(ConveyanceAttachment.class, 1001l);
		assertNotNull(convAtt);
		// test the retrieving of the attachment
		assertNotNull(convAtt.getAttachment());
		assertEquals("filename.ext", convAtt.getAttachment().getAttachmentFileName());
		assertNull(convAtt.getFilename());
		// C
		convAtt = new ConveyanceAttachment();
		assertNull(convAtt.getAttachment());
		convAtt.setNoteTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()).toString());
		convAtt.setConveyanceAnnexId(1001l);
		String filename = "info.odt";
		convAtt.setFilename(filename);
		getBoSvc().save(convAtt);
		// R
		convAtt.refresh();
		assertEquals(filename, convAtt.getFilename());
		// U
		convAtt.setConveyanceAnnexId(1002l);
		convAtt.refresh();
		// D
		getBoSvc().delete(convAtt);
		assertNull(getBoSvc().findBySinglePrimaryKey(ConveyanceAttachment.class, convAtt.getId()));	
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * test that null fields cause an error
	 */
	public void testConveyanceAttachmentNullableFields() {
		ConveyanceAttachment convAtt = new ConveyanceAttachment();
		getBoSvc().save(convAtt);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * tests that annex type generates errors when non-nullable fields are blank
	 */
	public void testConveyanceFeeNullableFields() {
		Fee fee = new Fee();
		//fee.setId(25l);
		getBoSvc().save(fee);
	}
	
	@Test
	/**
	 * test that the witness is loaded into the data dictionary
	 */
	public void testConveyanceClientAttributes() {
		testBoAttributesPresent(Client.class.getCanonicalName());
		Class<Client> dataObjectClass = Client.class;
		verifyInquiryLookup(dataObjectClass);
	}
	
	@Test
	/**
	 * tests retrieving a conveyance client present in the db, then CRUD ops
	 */
	public void testConveyanceClient() {
		MatterClient person = new Client();
		person.setMatterId(1001l);
		testMartinlawPersonCRUD(new Client(), "client1", person);
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.conveyance.ConveyanceBOTestBase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/note-atts-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-assignment-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/conveyance-fee-test-data.sql", ";").runSql();
	}
}
