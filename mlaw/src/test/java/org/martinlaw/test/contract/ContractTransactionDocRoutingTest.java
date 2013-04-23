/**
 * 
 */
package org.martinlaw.test.contract;

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


import java.math.BigDecimal;

import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.MatterTransactionDoc;
import org.martinlaw.bo.contract.TransactionDoc;
import org.martinlaw.bo.contract.Transaction;

/**
 * tests routing for {@link TransactionDoc}
 * @author mugo
 *
 */
public class ContractTransactionDocRoutingTest extends ContractTxRoutingTestBase {
	/* (non-Javadoc)
	 * @see org.martinlaw.test.MartinlawTestsBase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		setDocType(MartinlawConstants.DocTypes.CONTRACT_TRANSACTION);
		TransactionDoc doc = (TransactionDoc) KRADServiceLocatorWeb.getDocumentService().getNewDocument(getDocType());
		setWorkDoc(getTestUtils().populateTransactionDocForRouting(doc, new Transaction()));
	}
	
	/**
	 * test doc search
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws WorkflowException 
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testContractTransactionDocSearch() throws InstantiationException, IllegalAccessException, WorkflowException {
		// route some test documents then search
		MatterTransactionDoc txDoc1 = getTestUtils().populateTransactionDocForRouting(
				TransactionDoc.class.newInstance(), Transaction.class.newInstance());
		testTransactionalRoutingAndDocumentCRUD(MartinlawConstants.DocTypes.CONTRACT_TRANSACTION , txDoc1);
		
		MatterTransactionDoc txDoc2 = getTestUtils().populateTransactionDocForRouting(
				TransactionDoc.class.newInstance(), Transaction.class.newInstance());
		txDoc2.getTransaction().setAmount(new BigDecimal(50001));
		txDoc2.getTransaction().setClientPrincipalName("kyaloda");
		testTransactionalRoutingAndDocumentCRUD(MartinlawConstants.DocTypes.CONTRACT_TRANSACTION , txDoc2);
		
		MatterTransactionDoc txDoc3 = getTestUtils().populateTransactionDocForRouting(
				TransactionDoc.class.newInstance(), Transaction.class.newInstance());
		txDoc3.getTransaction().setAmount(new BigDecimal(45000));
		txDoc3.getTransaction().setClientPrincipalName("sirarthur");
		txDoc3.getTransaction().setTransactionTypeId(1003);
		
		testTransactionalRoutingAndDocumentCRUD(MartinlawConstants.DocTypes.CONTRACT_TRANSACTION , txDoc3);
		
	}

}
