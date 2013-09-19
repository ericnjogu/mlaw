/**
 * 
 */
package org.martinlaw.keyvalues;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.web.form.InquiryForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.MatterClient;
import org.martinlaw.bo.MatterConsideration;
import org.martinlaw.bo.MatterTransactionDoc;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.web.MatterTxForm;

/**
 * @author mugo
 *
 */
public class MatterClientNamesKeyValuesTest {

	private MatterTransactionDoc doc;
	private BusinessObjectService boSvc;
	private Matter kase;
	private MatterConsideration consideration;
	private long matterId;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//prepare the transaction doc
		doc = mock(MatterTransactionDoc.class);
		matterId = 1001l;
		when(doc.getMatterId()).thenReturn(matterId);
		//Class<? extends Matter> klass = Opinion.class;
		// when(doc.getMatterClass()).thenCallRealMethod();
		when(doc.isMatterIdValid()).thenReturn(true);
		
		//prepare the matter and clients
		kase = new CourtCase();
		MatterClient c1 = mock(MatterClient.class);
		when(c1.getPrincipalName()).thenReturn("signum");
		Person cp1 = mock(Person.class);
		when(cp1.getName()).thenReturn("Simon Gitahi");
		when(c1.getPerson()).thenReturn(cp1);
		kase.getClients().add(c1);
		MatterClient c2 = mock(MatterClient.class);
		when(c2.getPrincipalName()).thenReturn("mlaw");
		Person cp2 = mock(Person.class);
		when(cp2.getName()).thenReturn("Martin Mungai");
		when(c2.getPerson()).thenReturn(cp2);
		kase.getClients().add(c2);
		
		// prepare consideration
		consideration = new MatterConsideration();
		consideration.setMatter(kase);
	}

	/**
	 * Test method for {@link org.martinlaw.keyvalues.MatterClientNamesKeyValues#getKeyValues(org.kuali.rice.krad.uif.view.ViewModel)}.
	 */
	@Test
	public void testGetKeyValuesViewModel() {
		MatterClientNamesKeyValues mckv = new MatterClientNamesKeyValues();
		boSvc = mock(BusinessObjectService.class);
		mckv.setBusinessObjectService(boSvc);
		
		InquiryForm inqForm = new InquiryForm();
		inqForm.setDataObject(kase);
		
		InquiryForm inqForm2 = new InquiryForm();
		inqForm2.setDataObject(consideration);
		
		MatterTxForm txForm = mock(MatterTxForm.class);
		when(txForm.getDocument()).thenReturn(doc);
		when(boSvc.findBySinglePrimaryKey(Matter.class, matterId)).thenReturn(kase);
		
		UifFormBase[] forms = {inqForm, txForm, inqForm2};
		int count = 0;
		for (UifFormBase form: forms) {
			List<KeyValue> kvs = mckv.getKeyValues(form);
			assertFalse("should not be empty at count " + count, kvs.isEmpty());
			assertEquals("size differs", 2, kvs.size());
			count += 1;
		}
		
	}

}
