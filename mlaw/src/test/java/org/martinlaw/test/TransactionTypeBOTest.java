/**
 * 
 */
package org.martinlaw.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.martinlaw.bo.BaseDetail;
import org.martinlaw.bo.TransactionType;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2013 Eric Njogu (kunadawa@gmail.com)
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

/**
 * test various BO ops for {@link TransactionType}
 * 
 * @author mugo
 * 
 */
public class TransactionTypeBOTest extends BaseDetailBoTestBase {
	private TransactionType transactionType;
	@Override
	public Class<? extends BaseDetail> getDataObjectClass() {
		return TransactionType.class;
	}

	/**
	 * 
	 */
	public TransactionTypeBOTest() {
		transactionType = new TransactionType();
		transactionType.setId(1003l);
		transactionType.setName("interest");
		transactionType.setDescription("interest on penalty");
		transactionType.setEffectOnConsideration(TransactionType.TRANSACTION_EFFECT_ON_CONSIDERATION.INCREASE.toString());
	}

	@Override
	public BaseDetail getExpectedOnRetrieve() {
		return transactionType;
	}

	@Override
	public String getDocTypeName() {
		return "TransactionTypeMaintenanceDocument";
	}
	
	@Test
	public void testRetrievedEffectOnConsideration () {
		TransactionType type = getBoSvc().findBySinglePrimaryKey(TransactionType.class, getExpectedOnRetrieve().getId());
		assertEquals("effect on consideration differs", transactionType.getEffectOnConsideration(), type.getEffectOnConsideration());
	}
}
