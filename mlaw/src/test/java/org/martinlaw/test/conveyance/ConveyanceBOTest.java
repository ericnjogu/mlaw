/**
 * 
 */
package org.martinlaw.test.conveyance;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012,2013 Eric Njogu (kunadawa@gmail.com)
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.martinlaw.bo.MatterClient;
import org.martinlaw.bo.conveyance.Client;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.conveyance.ConveyanceAnnex;
import org.martinlaw.bo.conveyance.ConveyanceAttachment;
import org.martinlaw.keyvalues.ConveyanceAnnexTypeKeyValuesBase;
import org.martinlaw.keyvalues.ConveyanceAnnexTypeKeyValuesMaint;
import org.martinlaw.keyvalues.ConveyanceStatusKeyValues;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * various tests for {@link Conveyance}
 * @author mugo
 *
 */
public class ConveyanceBOTest extends MartinlawTestsBase {
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(getClass());
	@Test
	/**
	 * test CRUD ops on {@link Conveyance}
	 */
	public void testConveyanceCRUD() {
		// C
		Conveyance conv = getTestUtils().getTestConveyance();
		/*try {
			conv.getConsiderations().add((Consideration) getTestUtils().getTestConsideration(Consideration.class));
		} catch (Exception e) {
			fail("could not add consideration");
			log.error(e);
		}*/
		// add client
		Client client = new Client();
		String principalName = "clientX";
		client.setPrincipalName(principalName);
		conv.getClients().add(client);
		// add fee
		/*Transaction fee = new Transaction();
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
		assertEquals("conveyance name differs", getTestUtils().getTestConveyance().getName(), conv.getName());
		assertEquals("number of clients differs", 1, conv.getClients().size());
		assertEquals("principal name differs", principalName, conv.getClients().get(0).getPrincipalName());
		/*assertEquals(1, conv.getFees().size());*/
		assertEquals("number of annexes differs", 1, conv.getAnnexes().size());
		assertEquals("timestamp differs", timestamp.toString(), conv.getAnnexes().get(0).getAttachments().get(0).getNoteTimestamp());
		getTestUtils().testConsiderationFields(conv.getConsiderations().get(0));
		assertNotNull("considerations should not be null", conv.getConsiderations());
		assertEquals("default number of considerations differs", 2, conv.getConsiderations().size());
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
				getBoSvc().findBySinglePrimaryKey(Transaction.class, conv.getFees().get(0).getId()));*/
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
		
		getTestUtils().testWorkList(conv.getWork());
		
		getTestUtils().testRetrievedConsiderationFields(conv.getConsiderations().get(0));
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
	
	@Test()
	/**
	 * test that {@link ConveyanceAnnexTypeKeyValuesMaint} works as expected
	 */
	public void testConveyanceAnnexTypeKeyValues() {
		ConveyanceAnnexTypeKeyValuesBase keyValues = new ConveyanceAnnexTypeKeyValuesMaint();
		
		MaintenanceDocumentForm maintForm = mock(MaintenanceDocumentForm.class);
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
	
	/*@Test(expected=DataIntegrityViolationException.class)
	*//**
	 * tests that annex type generates errors when non-nullable fields are blank
	 *//*
	public void testConveyanceFeeNullableFields() {
		Transaction transaction = new Transaction();
		//fee.setId(25l);
		getBoSvc().save(transaction);
	}*/
	
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
	
	@Test
	/**
	 * test that the {@link Conveyance} is loaded into the data dictionary
	 */
	public void testConsiderationAttributes() {
		testBoAttributesPresent(Conveyance.class.getCanonicalName());
	}
}
