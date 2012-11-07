package org.martinlaw.test;

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


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.bo.MatterWorkRule;

/**
 * base class with BO tests for {@link MatterWork} objects
 * @author mugo
 *
 */
public abstract class WorkBOTestBase extends MartinlawTestsBase {

	private MatterTxDocBase work;
	private Class<? extends MatterWork> workClass;
	private String docType;
	private String viewId;

	public WorkBOTestBase() {
		super();
	}

	/**
	 * test that a {@link MatterWork} object created via sql can be retrieved
	 * @param klass
	 */
	@Test
	public void testWorkRetrieve() {
		MatterTxDocBase workTemp = getBoSvc().findBySinglePrimaryKey(getWorkClass(), 1001l);
		assertNotNull("result should not be null", workTemp);
	}

	/**
	 * test that {@link MatterWork} is loaded into the data dictionary
	 */
	@Test
	public void testWorkDD() {
		testTxDocDD(getDocType(), getWorkClass(), getViewId());
	}

	private String getViewId() {
		return viewId;
	}

	/**
	 * tests {@link org.martinlaw.bo.MatterWork#isMatterIdValid()}
	 */
	@Test
	public void testMatterIdValidity() {
		assertFalse("un-initialized matter id should be invalid", getWork().isMatterIdValid());
		getWork().setMatterId(-1l);
		assertFalse("matter id should be invalid", getWork().isMatterIdValid());
		getWork().setMatterId(1001l);
		assertTrue("matter id should be valid", getWork().isMatterIdValid());
	}
	
	@Test
	/**
	 * tests {@link org.martinlaw.bo.MatterWorkRule#isPrincipalNameInAssigneeList(MatterWork, String)}
	 */
	public void testIsPrincipalNameInAssigneeList () {
		work.setMatterId(3000l);
		MatterWorkRule rule = new MatterWorkRule();
		String principalNameExists = "pn";
		String principalNameDummy = "en";
		assertFalse("matter id does not exist", rule.isPrincipalNameInAssigneeList(work, principalNameExists));
		assertFalse("matter id does not exist", rule.isPrincipalNameInAssigneeList(work, principalNameDummy));
		work.setMatterId(1001l);
		assertTrue("assignee exists", rule.isPrincipalNameInAssigneeList(work, principalNameExists));
		assertFalse("assignee does not exist", rule.isPrincipalNameInAssigneeList(work, principalNameDummy));
	}

	/**
	 * @return the work
	 */
	public MatterTxDocBase getWork() {
		return work;
	}

	/**
	 * @param work the work to set
	 */
	public void setWork(MatterTxDocBase work) {
		this.work = work;
	}

	/**
	 * @return the workClass
	 */
	public Class<? extends MatterWork> getWorkClass() {
		return workClass;
	}

	/**
	 * @param workClass the workClass to set
	 */
	public void setWorkClass(Class<? extends MatterWork> workClass) {
		this.workClass = workClass;
	}

	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @param viewId the viewId to set
	 */
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
}