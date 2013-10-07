/**
 * 
 */
package org.martinlaw.test.courtcase;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kew.api.exception.WorkflowException;
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.bo.courtcase.LandCase;
import org.martinlaw.util.SearchTestCriteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
 * tests routing for {@link LandCase}
 * @author mugo
 *
 */
public class LandCaseRoutingTest extends CourtCaseRoutingTestBase {
	private static final String LAND_REF = "LR ACTS 10/38";

	@Override
	public String getDocTypeName() {
		return "LandCourtCaseMaintenanceDocument";
	}
	
	@Override
	public Class<? extends CourtCase> getDataObjectClass() {
		return LandCase.class;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.CourtCaseRoutingTestBase#getTestCourtCase()
	 */
	@Override
	protected Matter getTestMatter() throws InstantiationException,
			IllegalAccessException {
		LandCase kase = (LandCase) super.getTestMatter();
		kase.setLandReference(LAND_REF);
		
		return kase;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.CourtCaseRoutingTestBase#testCreatedCase(org.martinlaw.bo.courtcase.CourtCase)
	 */
	@Override
	protected void testCreatedMatter(Matter kase) {
		super.testCreatedMatter(kase);
		assertEquals("land ref differs", LAND_REF, ((LandCase)kase).getLandReference());
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.courtcase.CourtCaseRoutingTestBase#testCourtCase_doc_search()
	 */
	@Override
	public void testMatter_doc_search() throws WorkflowException, InstantiationException, IllegalAccessException {
		super.testMatter_doc_search();
		
		try {
			LandCase landCase = (LandCase)getTestMatter();
			final String lrValue = "LR GEN 1/1";
			landCase.setLandReference(lrValue);
			testMaintenanceRoutingInitToFinal(getDocTypeName(), landCase);
			
			// search for land reference
			SearchTestCriteria crit2 = new SearchTestCriteria();
			crit2.setExpectedDocuments(1);
			crit2.getFieldNamesToSearchValues().put("landReference", lrValue);
			List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
			crits.add(crit2);
			getTestUtils().runDocumentSearch(crits, getDocTypeName());
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}
}
