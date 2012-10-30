/**
 * 
 */
package org.martinlaw.test.contract;

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




import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.Constants;
import org.martinlaw.bo.contract.ClientFee;
import org.martinlaw.bo.contract.Fee;
import org.martinlaw.test.MatterFeeBOTest;

/**
 * tests DD and CRUD for {@link ClientFee}
 * @author mugo
 *
 */
public class ContractFeeBOTest extends MatterFeeBOTest {
	
	/**
	 * default constructor
	 */
	public ContractFeeBOTest() {
		setDocType(Constants.DocTypes.CONTRACT_FEE);
		setDocumentClass(ClientFee.class);
		setViewId(Constants.ViewIds.CONTRACT_FEE);
		setFeeClass(Fee.class);
	}
	/* (non-Javadoc)
	 * @see org.kuali.test.KRADTestCase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-type-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-fee-test-data.sql", ";").runSql();
	}
	
	
}
