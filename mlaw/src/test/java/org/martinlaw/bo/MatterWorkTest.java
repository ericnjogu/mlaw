/**
 * 
 */
package org.martinlaw.bo;

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

import static org.junit.Assert.*;

import org.apache.cxf.common.util.StringUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.martinlaw.bo.contract.Work;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * tests methods of {@link org.martinlaw.bo.MatterWork}
 * @author mugo
 *
 */
public class MatterWorkTest {

	private Work work;
	private WorkflowDocument wfd;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//using contract work as concrete class
		work = mock(Work.class);
		when(work.getDateCreated()).thenCallRealMethod();
		when(work.getDocumentHeader()).thenCallRealMethod();
		when(work.getPeriodToLastModification()).thenCallRealMethod();
	}

	/**
	 * Test method for {@link org.martinlaw.bo.MatterWork#getDateCreated()}.
	 */
	@Test
	public void testGetDateCreated() {
		assertNull("should be null since no header has been set", work.getDateCreated());
		setupHeader();
		assertFalse("should not be null", work.getDateCreated() == null);
	}

	/**
	 * add mock header and workflow doc to the work document
	 */
	public void setupHeader() {
		wfd = mock(WorkflowDocument.class);
		when(wfd.getDateCreated()).thenReturn(DateTime.now());
		DocumentHeader docHdr = mock(DocumentHeader.class);
		when(docHdr.getWorkflowDocument()).thenReturn(wfd);
		when(work.getDocumentHeader()).thenReturn(docHdr);
	}

	/**
	 * Test method for {@link org.martinlaw.bo.MatterWork#getPeriodToLastModification()}.
	 */
	@Test
	public void testGetPeriodToLastModification() {
		assertTrue("should be empty since header is not set", StringUtils.isEmpty(work.getPeriodToLastModification()));
		setupHeader();
		when(wfd.getDateLastModified()).thenReturn(DateTime.now().plusDays(7));
		String period = work.getPeriodToLastModification();
		assertNotNull(period);
	}
}
