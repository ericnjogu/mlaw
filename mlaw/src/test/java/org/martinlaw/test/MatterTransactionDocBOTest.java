package org.martinlaw.test;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.martinlaw.bo.MatterTransactionDoc;
import org.martinlaw.bo.contract.Contract;
import org.martinlaw.keyvalues.MatterClientNamesKeyValues;
import org.martinlaw.keyvalues.MatterConsiderationKeyValues;
import org.martinlaw.web.MatterTxForm;

/**
 * base class for testing the children of {@link MatterTransactionDoc} transactional
 * document
 * 
 * @author mugo
 * 
 */
public abstract class MatterTransactionDocBOTest extends MartinlawTestsBase {
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	public MatterTransactionDocBOTest() {
		super();
	}

	/**
	 * @return the transaction document class
	 */
	public abstract Class<? extends MatterTransactionDoc> getMatterTransactionDocumentClass();

	/**
	 * @return the docType
	 */
	public abstract String getDocType();

	public abstract String getViewId();
	
	/**
	 * test DD entries
	 */
	@Test
	public void testDD() {
		testTxDocDD(getDocType(), getMatterTransactionDocumentClass(), getViewId());
	}
	
	/**
	 * retrieves object that was inserted via sql and compares it with expected info
	 * @throws ParseException 
	 */
	@Test
	public void testTransactionDocRetrieve() throws ParseException {
		MatterTransactionDoc transactionDoc = getBoSvc().findBySinglePrimaryKey(
				getMatterTransactionDocumentClass(), "1001");
		testTransactionDocFields(transactionDoc, "mawanja", new Date(sdf.parse("22-Oct-2012").getTime()), new BigDecimal(2501));
		//getTestUtils().testRetrievedConsiderationFields(transactionDoc.getConsideration());
	}

	/**
	 * convenience method to test a transaction doc object
	 * 
	 * @param transactionDoc
	 *            - the test object
	 * @param expectedDate - the expected date
	 * @param expectedAmount - the expected amount
	 */
	protected void testTransactionDocFields(MatterTransactionDoc transactionDoc,
			String principalName, Date expectedDate, BigDecimal expectedAmount) {
		assertNotNull("result should not be null", transactionDoc);
		assertEquals("principal name differs", principalName, transactionDoc.getClientPrincipalName());
		transactionDoc.refreshNonUpdateableReferences();
		assertNotNull("transaction type should not be null", transactionDoc.getTransactionType());
		assertEquals("date differs", sdf.format(expectedDate), sdf.format(transactionDoc.getDate()));
		assertEquals("amount differs", 0, expectedAmount.compareTo(transactionDoc.getAmount()));
	}

	/**
	 * test CRUD ops
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testTransactionDocCRUD() throws InstantiationException,
			IllegalAccessException {
		// C
		String documentNumber = "2001";
		MatterTransactionDoc transactionDoc = getTestUtils().getTestTransactionDocForCRUD(
				getMatterTransactionDocumentClass(), documentNumber);
		getBoSvc().save(transactionDoc);

		// R
		String clientPrincipalName = "pkk";
		transactionDoc = (MatterTransactionDoc) getBoSvc()
				.findBySinglePrimaryKey(getMatterTransactionDocumentClass(), documentNumber);
		testTransactionDocFields(transactionDoc, clientPrincipalName, new Date(System.currentTimeMillis()), 
				new BigDecimal(2000));
		//getTestUtils().testConsiderationFields(transactionDoc.getConsideration());
		// U
		String clientPrincipalName2 = "hwn";
		transactionDoc.setClientPrincipalName(clientPrincipalName2);
		transactionDoc.refresh();
		assertEquals("principal name differs", clientPrincipalName2, transactionDoc.getClientPrincipalName());

		// D
		getBoSvc().delete(transactionDoc);
		assertNull("object should not exist", getBoSvc()
				.findBySinglePrimaryKey(getMatterTransactionDocumentClass(), documentNumber));
	}
	

	/**
	 * tests
	 * {@link org.martinlaw.keyvalues.MatterClientNamesKeyValues#getKeyValues(ViewModel)}
	 * and also for {@link Contract#getClients()}
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@Test
	public void testClientNamesKeyValues() throws InstantiationException,
			IllegalAccessException {
		MatterClientNamesKeyValues keyValues = new MatterClientNamesKeyValues();
		
		List<KeyValue> result = keyValues.getKeyValues(getMockForm());

		assertFalse("key values list should not be empty", result.isEmpty());
		assertEquals("key values list size differs", 2, result.size());
		assertEquals("client name differs", "client1", result.get(0).getKey());
	}
	
	/**
	 * tests
	 * {@link org.martinlaw.keyvalues.MatterConsiderationKeyValues#getKeyValues(ViewModel model)}
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@Test
	public void testMatterConsiderationKeyValues() throws InstantiationException,
			IllegalAccessException {
		MatterConsiderationKeyValues keyValues = new MatterConsiderationKeyValues(); 
		List<KeyValue> result = keyValues.getKeyValues(getMockForm());

		assertFalse("key values list should not be empty", result.isEmpty());
		assertEquals("key values list size differs", 1, result.size());
		assertEquals("value differs", "Legal fee - TZS - 41000.00", result.get(0).getValue());
	}

	/**
	 * @return a mocked {@link MatterTxForm}
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public MatterTxForm getMockForm() throws InstantiationException,
			IllegalAccessException {
		MatterTxForm txForm = mock(MatterTxForm.class);

		MatterTransactionDoc transactionDoc = getMatterTransactionDocumentClass().newInstance();
		transactionDoc.setMatterId(1001l);
		when(txForm.getDocument()).thenReturn(transactionDoc);
		return txForm;
	}
}