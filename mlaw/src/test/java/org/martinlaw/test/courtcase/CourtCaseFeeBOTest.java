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




import org.martinlaw.Constants;
import org.martinlaw.bo.courtcase.ClientFee;
import org.martinlaw.bo.courtcase.Fee;
import org.martinlaw.test.MatterFeeBOTest;

/**
 * tests DD and CRUD for {@link ClientFee}
 * @author mugo
 *
 */
public class CourtCaseFeeBOTest extends MatterFeeBOTest {
	
	/**
	 * default constructor
	 */
	public CourtCaseFeeBOTest() {
		setDocType(Constants.DocTypes.COURTCASE_FEE);
		setDocumentClass(ClientFee.class);
		setViewId(Constants.ViewIds.COURTCASE_FEE);
		setFeeClass(Fee.class);
	}
}
