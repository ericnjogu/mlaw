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
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.TransactionForm;
import org.martinlaw.bo.MatterClientFee;
import org.martinlaw.bo.MatterFee;
import org.martinlaw.bo.contract.ClientFee;
import org.martinlaw.bo.contract.Contract;
import org.martinlaw.bo.contract.Fee;
import org.martinlaw.keyvalues.MatterClientNamesKeyValues;

/**
 * base class for testing the children of {@link MatterClientFee} transactional
 * document
 * 
 * @author mugo
 * 
 */
public abstract class MatterFeeBOTest extends MartinlawTestsBase {

	private Class<? extends MatterClientFee<?>> documentClass;
	private String docType;
	private String viewId;
	private Class<? extends MatterFee> feeClass;

	public MatterFeeBOTest() {
		super();
	}

	/**
	 * @return the workClass
	 */
	public Class<? extends MatterClientFee<? extends MatterFee>> getDocumentClass() {
		return documentClass;
	}

	/**
	 * @param workClass
	 *            the workClass to set
	 */
	public void setDocumentClass(
			Class<? extends MatterClientFee<?>> documentClass) {
		this.documentClass = documentClass;
	}

	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType
	 *            the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @param viewId
	 *            the viewId to set
	 */
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public String getViewId() {
		return viewId;
	}

	/**
	 * test DD entries
	 */
	@Test
	public void testDD() {
		testTxDocDD(getDocType(), getDocumentClass(), getViewId());
	}

	@Test
	public void testFeeDocRetrieve() {
		MatterClientFee<?> clientFee = getBoSvc().findBySinglePrimaryKey(
				getDocumentClass(), "1001");
		testClientFeeFields(clientFee, "mawanja");
	}

	/**
	 * convenience method to test a client fee object
	 * 
	 * @param clientFee
	 *            - the test object
	 */
	protected void testClientFeeFields(MatterClientFee<?> clientFee,
			String principalName) {
		assertNotNull("result should not be null", clientFee);
		assertNotNull("fee should not be null", clientFee.getFee());
		assertEquals("principal name differs", principalName, clientFee
				.getFee().getClientPrincipalName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFeeDocCRUD() throws InstantiationException,
			IllegalAccessException {
		// C
		MatterClientFee<MatterFee> clientFee = (MatterClientFee<MatterFee>) getDocumentClass()
				.newInstance();
		String documentNumber = "2001";
		clientFee.setDocumentNumber(documentNumber);
		clientFee.getDocumentHeader().setDocumentNumber(documentNumber);
		clientFee.getDocumentHeader().setDocumentDescription("cash");
		clientFee.setMatterId(1001l);

		MatterFee fee = getFeeClass().newInstance();
		fee.setAmount(new BigDecimal(2000l));
		String clientPrincipalName = "pkk";
		fee.setClientPrincipalName(clientPrincipalName);
		fee.setDate(new Date(Calendar.getInstance().getTimeInMillis()));

		clientFee.setFee(fee);
		getBoSvc().save(clientFee);

		// R
		clientFee = (MatterClientFee<MatterFee>) getBoSvc()
				.findBySinglePrimaryKey(getDocumentClass(), documentNumber);
		testClientFeeFields(clientFee, clientPrincipalName);

		// U
		String clientPrincipalName2 = "hwn";
		clientFee.getFee().setClientPrincipalName(clientPrincipalName2);
		clientFee.refresh();
		assertEquals("principal name differs", clientPrincipalName2, clientFee
				.getFee().getClientPrincipalName());

		// D
		getBoSvc().delete(clientFee);
		assertNull("object should not exist", getBoSvc()
				.findBySinglePrimaryKey(ClientFee.class, documentNumber));
		assertNull("object should not exist", getBoSvc()
				.findBySinglePrimaryKey(Fee.class, clientFee.getFee().getId()));
	}

	private Class<? extends MatterFee> getFeeClass() {
		return feeClass;
	}

	/**
	 * @param feeClass
	 *            the feeClass to set
	 */
	public void setFeeClass(Class<? extends MatterFee> feeClass) {
		this.feeClass = feeClass;
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
		TransactionForm txForm = mock(TransactionForm.class);

		MatterClientFee<? extends MatterFee> clientFee = getDocumentClass()
				.newInstance();
		clientFee.setMatterId(1001l);
		when(txForm.getDocument()).thenReturn(clientFee);
		List<KeyValue> result = keyValues.getKeyValues(txForm);

		assertFalse("key values list should not be empty", result.isEmpty());
		assertEquals("key values list size differs", 2, result.size());
		assertEquals("client name differs", "client1", result.get(0).getKey());
	}
}