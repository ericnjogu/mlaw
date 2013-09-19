/**
 * 
 */
package org.martinlaw.test.conveyance;

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



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.bo.conveyance.Work;
import org.martinlaw.test.MatterWorkBOTestBase;

/**
 * tests CRUD and data dictionary of {@link Work}
 * 
 * @author mugo
 *
 */
public class ConveyanceWorkBOTest extends MatterWorkBOTestBase {
	
	/* (non-Javadoc)
	 * @see org.martinlaw.test.MatterWorkBOTestBase#getWorkClass()
	 */
	@Override
	public Class<? extends MatterWork> getWorkClass() {
		return Work.class;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.MatterWorkBOTestBase#getDocTypeName()
	 */
	@Override
	public String getDocTypeName() {
		return MartinlawConstants.DocTypes.CONVEYANCE_WORK;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.MatterWorkBOTestBase#getViewId()
	 */
	@Override
	public String getViewId() {
		return MartinlawConstants.ViewIds.CONVEYANCE_WORK;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.MatterWorkBOTestBase#getMatterWorkDocNumber()
	 */
	@Override
	public String getMatterWorkDocNumber() {
		return "1005";
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.MatterWorkBOTestBase#testWorkRetrieve()
	 */
	@Override
	@Test
	public void testWorkRetrieve() {
		super.testWorkRetrieve();
		
		Work work = (Work) getBoSvc().findBySinglePrimaryKey(getWorkClass(), getMatterWorkDocNumber());
		assertNotNull("conveyance annex type should not be null", work.getConveyanceAnnexType());
		assertEquals("annex type name differs", "land board approval", work.getConveyanceAnnexType().getName());
		
	}
	
}
