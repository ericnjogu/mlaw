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


import org.martinlaw.bo.BaseDetail;
import org.martinlaw.bo.Scope;
import org.martinlaw.bo.contract.ContractType;
import org.martinlaw.test.type.BaseDetailBoTestBase;

/**
 * test various BO ops for {@link ContractType}
 * 
 * @author mugo
 * 
 */
public class ContractTypeBOTest extends BaseDetailBoTestBase {

	private ContractType contractType;

	@Override
	public Class<? extends BaseDetail> getDataObjectClass() {
		return ContractType.class;
	}

	@Override
	public BaseDetail getExpectedOnRetrieve() {
		return contractType;
	}

	/**
	 * 
	 */
	public ContractTypeBOTest() {
		contractType = new ContractType();
		contractType.setId(1002l);
		contractType.setName("life assurance");
		contractType.setDescription("maisha");
	}

	@Override
	public String getDocTypeName() {
		return "ContractTypeMaintenanceDocument";
	}

	@Override
	public Class<? extends Scope> getScopeClass() {
		return null;//contract type has no scope
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.type.BaseDetailBoTestBase#testScopeAttributes()
	 */
	@Override
	public void testScopeAttributes() {
		// contract type has no scope
	}

}
