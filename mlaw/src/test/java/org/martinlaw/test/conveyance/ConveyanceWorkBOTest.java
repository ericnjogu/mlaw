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



import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.kuali.rice.core.api.util.KeyValue;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.conveyance.Work;
import org.martinlaw.keyvalues.ConveyanceAnnexTypeKeyValuesBase;
import org.martinlaw.keyvalues.ConveyanceAnnexTypeKeyValuesTx;
import org.martinlaw.test.WorkBOTestBase;
import org.martinlaw.web.MatterTxForm;

/**
 * tests CRUD and data dictionary of {@link Work}
 * 
 * @author mugo
 *
 */
public class ConveyanceWorkBOTest extends WorkBOTestBase {
	
	/* (non-Javadoc)
	 * @see org.martinlaw.test.MartinlawTestsBase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		setWork(new Work());
		setWorkClass(Work.class);
		setDocType(MartinlawConstants.DocTypes.CONVEYANCE_WORK);
		setViewId(MartinlawConstants.ViewIds.CONVEYANCE_WORK);
	}
	
	@Test()
	/**
	 * test that {@link ConveyanceAnnexTypeKeyValuesTx} works as expected
	 */
	public void testConveyanceAnnexTypeKeyValues() {
		ConveyanceAnnexTypeKeyValuesBase keyValues = new ConveyanceAnnexTypeKeyValuesTx();
		MatterTxForm txForm = mock(MatterTxForm.class);
		
		Work doc = new Work();
		doc.setMatterId(1001l);
		when(txForm.getDocument()).thenReturn(doc);
		List<KeyValue> result = keyValues.getKeyValues(txForm);
		
		getTestUtils().testAnnexTypeKeyValues(result);
	}
	
	/**
	 * test that the associated conveyance annex type is fetched
	 */
	@Test
	public void testConveyanceAnnexTypeRetrieve() {
		Work workTemp = (Work) getBoSvc().findBySinglePrimaryKey(getWorkClass(), 1001l);
		assertNotNull("result should not be null", workTemp);
		assertNotNull("contract should not be null", workTemp.getConveyanceAnnexType());
	}
	
}
