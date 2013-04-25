/**
 * 
 */
package org.martinlaw.test.conveyance;

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
import org.martinlaw.bo.MatterTransactionDoc;
import org.martinlaw.bo.conveyance.TransactionDoc;
import org.martinlaw.test.MatterTransactionDocBOTest;

/**
 * tests DD and CRUD for {@link TransactionDoc}
 * @author mugo
 *
 */
public class ConveyanceTransactionBOTest extends MatterTransactionDocBOTest {

	@Override
	public Class<? extends MatterTransactionDoc> getMatterTransactionDocumentClass() {
		return TransactionDoc.class;
	}

	@Override
	public String getDocType() {
		return MartinlawConstants.DocTypes.CONVEYANCE_TRANSACTION;
	}

	@Override
	public String getViewId() {
		return MartinlawConstants.ViewIds.CONVEYANCE_TRANSACTION;
	}

}
