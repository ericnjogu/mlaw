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


import static org.junit.Assert.fail;

import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.bo.courtcase.TransactionDoc;
import org.martinlaw.test.TxRoutingTestBase;

/**
 * tests {@link org.martinlaw.bo.courtcase.TransactionDoc} routing
 * @author mugo
 *
 */
public class CourtCaseTransactionDocRoutingTest extends TxRoutingTestBase {

	@Override
	public MatterTxDocBase getTxDoc() throws WorkflowException {
		return getTestUtils().populateTransactionDocForRouting(TransactionDoc.class);
	}

	@Override
	public String getDocTypeName() {
		return MartinlawConstants.DocTypes.COURTCASE_TRANSACTION;
	}

	@Override
	@Test
	public void testDocSearch() {
		try {
			getTestUtils().testMatterTransactionDocSearch(TransactionDoc.class, getDocTypeName());
		} catch (Exception e) {
			log.error(e);
			fail("error occured");
		}
	}
}
