/**
 * 
 */
package org.martinlaw.test.courtcase;

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




import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.MatterTransaction;
import org.martinlaw.bo.MatterTransactionDoc;
import org.martinlaw.bo.courtcase.TransactionDoc;
import org.martinlaw.bo.courtcase.Transaction;
import org.martinlaw.test.MatterTransactionBOTest;

/**
 * tests DD and CRUD for {@link TransactionDoc}
 * @author mugo
 *
 */
public class CourtCaseTransactionBOTest extends MatterTransactionBOTest {

	@Override
	public Class<? extends MatterTransactionDoc<? extends MatterTransaction>> getMatterTransactionDocumentClass() {
		return TransactionDoc.class;
	}

	@Override
	public String getDocType() {
		return MartinlawConstants.DocTypes.COURTCASE_TRANSACTION;
	}

	@Override
	public String getViewId() {
		return MartinlawConstants.ViewIds.COURTCASE_FEE;
	}

	@Override
	public Class<? extends MatterTransaction> getTransactionClass() {
		return Transaction.class;
	}
}
