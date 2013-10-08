package org.martinlaw.test;

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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.bo.MatterWorkRule;

/**
 * base class with BO tests for {@link MatterWork} objects
 * @author mugo
 *
 */
public class MatterWorkBOTest extends MartinlawTestsBase {

	private MatterTxDocBase work;

	public MatterWorkBOTest() {
		super();
	}

	/**
	 * test that a {@link MatterWork} object created via sql can be retrieved
	 * @param klass
	 * @return 
	 */
	@Test
	public void testWorkRetrieve() {
		MatterWork work = getBoSvc().findBySinglePrimaryKey(getWorkClass(), getMatterWorkDocNumber());
		assertNotNull("result should not be null", work);
		assertNotNull("matter should not be null", work.getMatter());
		assertNotNull("work type should not be null", work.getAnnexType());
		assertEquals("default annex type differs", MartinlawConstants.DEFAULT_ANNEX_TYPE_ID, work.getAnnexTypeId());
	}

	/**
	 * @return the document number of the test object to retrieve from the database
	 */
	public String getMatterWorkDocNumber() {
		return "1001";
	}

	/**
	 * test that {@link MatterWork} is loaded into the data dictionary
	 */
	@Test
	public void testWorkDD() {
		testTxDocDD(getDocTypeName(), getWorkClass(), getViewId());
	}

	/**
	 * tests {@link org.martinlaw.bo.MatterWork#isMatterIdValid()}
	 */
	@Test
	public void testMatterIdValidity() {
		final MatterTxDocBase tmpWork = getWork();
		assertFalse("un-initialized matter id should be invalid", tmpWork.isMatterIdValid());
		tmpWork.setMatterId(-1l);
		assertFalse("matter id should be invalid", tmpWork.isMatterIdValid());
		tmpWork.setMatterId(1001l);
		assertTrue("matter id should be valid", tmpWork.isMatterIdValid());
	}
	
	@Test
	/**
	 * tests {@link org.martinlaw.bo.MatterWorkRule#isPrincipalNameInAssigneeList(MatterWork, String)}
	 */
	public void testIsPrincipalNameInAssigneeList () {
		work = getWork();
		work.setMatterId(3000l);
		MatterWorkRule rule = new MatterWorkRule();
		String principalNameExists = "pauline_njogu";
		String principalNameDummy = "en";
		assertFalse("matter id does not exist", rule.isPrincipalNameInAssigneeList(work, principalNameExists));
		assertFalse("matter id does not exist", rule.isPrincipalNameInAssigneeList(work, principalNameDummy));
		work = getWork();
		work.setMatterId(1001l);
		assertTrue("assignee exists", rule.isPrincipalNameInAssigneeList(work, principalNameExists));
		assertFalse("assignee does not exist", rule.isPrincipalNameInAssigneeList(work, principalNameDummy));
	}

	/**
	 * @return the work
	 */
	public MatterTxDocBase getWork() {
		return new MatterWork();
	}

	/**
	 * @return the workClass
	 */
	public Class<? extends MatterWork> getWorkClass() {
		return MatterWork.class;
	}

	/**
	 * @return the docType
	 */
	public String getDocTypeName() {
		return MartinlawConstants.DocTypes.MATTER_WORK;
	}

	/**
	 * @param viewId the viewId to set
	 */
	public String getViewId() {
		return MartinlawConstants.ViewIds.MATTER_WORK;
	}
}