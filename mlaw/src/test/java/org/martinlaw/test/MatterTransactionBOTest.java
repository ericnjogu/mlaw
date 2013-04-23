package org.martinlaw.test;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012 Eric Njogu (kunadawa@gmail.com)
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.martinlaw.bo.MatterTransaction;
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
public abstract class MatterTransactionBOTest extends MartinlawTestsBase {

	public MatterTransactionBOTest() {
		super();
	}

	/**
	 * @return the transaction document class
	 */
	@SuppressWarnings("rawtypes")
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
	 */
	@Test
	public void testTransactionDocRetrieve() {
		MatterTransactionDoc<?> transactionDoc = getBoSvc().findBySinglePrimaryKey(
				getMatterTransactionDocumentClass(), "1001");
		testTransactionDocFields(transactionDoc, "mawanja", "22-Oct-2012", new BigDecimal(2500.58));
		getTestUtils().testRetrievedConsiderationFields(transactionDoc.getTransaction().getConsideration());
	}

	/**
	 * convenience method to test a transaction doc object
	 * 
	 * @param transactionDoc
	 *            - the test object
	 * @param expectedDate TODO
	 * @param expectedAmount TODO
	 */
	protected void testTransactionDocFields(MatterTransactionDoc<?> transactionDoc,
			String principalName, String expectedDate, BigDecimal expectedAmount) {
		assertNotNull("result should not be null", transactionDoc);
		assertNotNull("transaction should not be null", transactionDoc.getTransaction());
		assertEquals("principal name differs", principalName, transactionDoc.getTransaction().getClientPrincipalName());
		assertNotNull("transaction type should not be null", transactionDoc.getTransaction().getTransactionType());
		assertEquals("date differs", expectedDate, transactionDoc.getTransaction().getDate());
		assertEquals("amount differs", expectedAmount, transactionDoc.getTransaction().getAmount());
		assertNotNull("transaction type should not be null", transactionDoc.getTransaction().getTransactionType());
	}

	/**
	 * test CRUD ops
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings({"unchecked" })
	@Test
	public void testTransactionDocCRUD() throws InstantiationException,
			IllegalAccessException {
		// C
		String documentNumber = "2001";
		MatterTransactionDoc<MatterTransaction> transactionDoc = getTestUtils().getTestTransactionDocForCRUD(
				getMatterTransactionDocumentClass(), getTransactionClass(), documentNumber);
		getBoSvc().save(transactionDoc);

		// R
		String clientPrincipalName = "pkk";
		transactionDoc = (MatterTransactionDoc<MatterTransaction>) getBoSvc()
				.findBySinglePrimaryKey(getMatterTransactionDocumentClass(), documentNumber);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		testTransactionDocFields(transactionDoc, clientPrincipalName, sdf.format(Calendar.getInstance().getTime()), 
				new BigDecimal(1000));
		getTestUtils().testConsiderationFields(transactionDoc.getTransaction().getConsideration());
		// U
		String clientPrincipalName2 = "hwn";
		transactionDoc.getTransaction().setClientPrincipalName(clientPrincipalName2);
		transactionDoc.refresh();
		assertEquals("principal name differs", clientPrincipalName2, transactionDoc
				.getTransaction().getClientPrincipalName());

		// D
		getBoSvc().delete(transactionDoc);
		assertNull("object should not exist", getBoSvc()
				.findBySinglePrimaryKey(getMatterTransactionDocumentClass(), documentNumber));
		assertNull("object should not exist", getBoSvc()
				.findBySinglePrimaryKey(getTransactionClass(), transactionDoc.getTransaction().getId()));
	}
	
	/**
	 * @return the transaction class being tested
	 */
	public abstract Class<? extends MatterTransaction> getTransactionClass();


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
		assertEquals("value differs", "legal fee - TZS - 41000", result.get(0).getKey());
	}

	/**
	 * @return a mocked {@link MatterTxForm}
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public MatterTxForm getMockForm() throws InstantiationException,
			IllegalAccessException {
		MatterTxForm txForm = mock(MatterTxForm.class);

		@SuppressWarnings("unchecked")
		MatterTransactionDoc<? extends MatterTransaction> transactionDoc = getMatterTransactionDocumentClass().newInstance();
		transactionDoc.setMatterId(1001l);
		when(txForm.getDocument()).thenReturn(transactionDoc);
		return txForm;
	}
}