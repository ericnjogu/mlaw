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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.martinlaw.bo.Type;
import org.martinlaw.bo.MatterType;
import org.martinlaw.bo.MatterTypeAnnexDetail;

/**
 * test various BO ops for {@link MatterType}
 * 
 * @author mugo
 * 
 */
public class MatterTypeBOTest extends TypeBoTestBase {

	private MatterType matterType;
	final String annexTypeName = "interview";

	@Override
	public Class<? extends Type> getDataObjectClass() {
		return MatterType.class;
	}

	@Override
	public Type getExpectedOnRetrieve() {
		return matterType;
	}

	/**
	 * 
	 */
	public MatterTypeBOTest() {
		matterType = new MatterType();
		matterType.setId(10003l);
		matterType.setName("commercial");
		matterType.setDescription("any biz mambo");
	}

	@Override
	public String getDocTypeName() {
		return "MatterTypeMaintenanceDocument";
	}

	@Override
	protected void populateAdditionalFieldsForCrud(Type type) {
		MatterType matterType = (MatterType)type;
		MatterTypeAnnexDetail annexDetail = new MatterTypeAnnexDetail();
		assertEquals("default value differs", Long.valueOf(1), annexDetail.getSequence());
		annexDetail.setAnnexTypeId(10019l);
		matterType.getAnnexDetails().add(annexDetail);
	}

	@Override
	protected void testCrudCreated(Type type) {
		MatterType matterType = (MatterType)type;
		final List<MatterTypeAnnexDetail> annexDetails = matterType.getAnnexDetails();
		testAnnexDetails(annexDetails, 1, annexTypeName, Long.valueOf(1));
	}

	@Override
	protected void testCrudDeleted(Type type) {
		MatterType matterType = (MatterType)type;
		assertNull("annex detail should have been deleted", getBoSvc().findBySinglePrimaryKey(
				MatterTypeAnnexDetail.class, matterType.getAnnexDetails().get(0).getAnnexType().getName()));
		
	}

	@Override
	protected void additionalTestsForRetrievedObject(Type type) {
		MatterType matterType = (MatterType)type;
		final List<MatterTypeAnnexDetail> annexDetails = matterType.getAnnexDetails();
		testAnnexDetails(annexDetails, 1, "demand letter", Long.valueOf(3));
	}

	/**
	 * common method to test CRUD or retrieved annex details
	 * @param annexDetails - the object to test
	 * @param listSize - the expected number of annex details
	 * @param annexTypeName - the expected name of the annex type in the first annex detail
	 * @param sequenceValue - the expected sequence value in the first annex detail
	 */
	protected void testAnnexDetails(
			final List<MatterTypeAnnexDetail> annexDetails, int listSize, String annexTypeName, Long sequenceValue) {
		assertEquals("there should be an annex detail", listSize, annexDetails.size());
		final MatterTypeAnnexDetail matterTypeAnnexDetail = annexDetails.get(0);
		matterTypeAnnexDetail.refresh();
		assertEquals("annex type name differs", annexTypeName, matterTypeAnnexDetail.getAnnexType().getName());
		assertEquals("sequence value differs", sequenceValue, matterTypeAnnexDetail.getSequence());
	}
	
	@Test
	/**
	 * test that matter type key values returns the correct number
	 */
	public void testMatterTypeKeyValues() {
		final String dataObjectName = "matter type(s)";
		final int expectedCourtCaseScopeCount = 5;
		final int expectedContractScopeCount = 3;
		final int expectedConveyanceScopeCount = 3;
		final int expectedEmptyScopeCount = 1;
		final int expectedMatterScopeCount = 0;
		final int expectedLandCaseScopeCount = 0;
		
		getTestUtils().testScopeKeyValues(dataObjectName, expectedCourtCaseScopeCount,
				expectedContractScopeCount, expectedConveyanceScopeCount,
				expectedEmptyScopeCount, expectedMatterScopeCount,
				expectedLandCaseScopeCount, getDataObjectClass());
	}
}

