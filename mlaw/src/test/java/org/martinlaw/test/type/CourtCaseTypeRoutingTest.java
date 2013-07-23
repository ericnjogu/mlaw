/**
 * 
 */
package org.martinlaw.test.type;

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




import org.martinlaw.bo.BaseDetail;
import org.martinlaw.bo.TransactionType;
import org.martinlaw.bo.courtcase.CourtCaseType;

/**
 * tests routing for {@link TransactionType}
 * @author mugo
 *
 */
public class CourtCaseTypeRoutingTest extends BaseDetailRoutingTestBase {

	@Override
	public Class<? extends BaseDetail> getDataObjectClass() {
		return CourtCaseType.class;
	}

	@Override
	public String getDocTypeName() {
		return "CourtCaseTypeMaintenanceDocument";
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.BaseDetailRoutingTestBase#getDataObject()
	 */
	@Override
	protected BaseDetail getDataObject() throws InstantiationException,
			IllegalAccessException {
		CourtCaseType type =  new CourtCaseType();
		type.setName("tribunal");
		type.setDescription("tribunal");
		
		return type;
	}
}
