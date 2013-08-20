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
import org.martinlaw.bo.opinion.Client;
import org.martinlaw.bo.opinion.Consideration;
import org.martinlaw.bo.opinion.Opinion;
import org.martinlaw.bo.opinion.TransactionDoc;
import org.martinlaw.web.MatterTxForm;

/**
 * @author mugo
 *
 */
public class MatterClientNamesKeyValuesTest {

	private TransactionDoc doc;
	private BusinessObjectService boSvc;
	private Opinion opinion;
	private Consideration consideration;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//prepare the transaction doc
		doc = mock(TransactionDoc.class);
		final long matterId = 1001l;
		when(doc.getMatterId()).thenReturn(matterId);
		//Class<? extends Matter> klass = Opinion.class;
		when(doc.getMatterClass()).thenCallRealMethod();
		when(doc.isMatterIdValid()).thenReturn(true);
		
		//prepare the matter and clients
		opinion = new Opinion();
		Client c1 = mock(Client.class);
		when(c1.getPrincipalName()).thenReturn("signum");
		Person cp1 = mock(Person.class);
		when(cp1.getName()).thenReturn("Simon Gitahi");
		when(c1.getPerson()).thenReturn(cp1);
		opinion.getClients().add(c1);
		Client c2 = mock(Client.class);
		when(c2.getPrincipalName()).thenReturn("mlaw");
		Person cp2 = mock(Person.class);
		when(cp2.getName()).thenReturn("Martin Mungai");
		when(c2.getPerson()).thenReturn(cp2);
		opinion.getClients().add(c2);
		boSvc = mock(BusinessObjectService.class);
		when(boSvc.findBySinglePrimaryKey(Opinion.class, matterId)).thenReturn(opinion);
		
		// prepare consideration
		consideration = new Consideration();
		consideration.setMatter(opinion);
	}

	/**
	 * Test method for {@link org.martinlaw.keyvalues.MatterClientNamesKeyValues#getKeyValues(org.kuali.rice.krad.uif.view.ViewModel)}.
	 */
	@Test
	public void testGetKeyValuesViewModel() {
		MatterClientNamesKeyValues mckv = new MatterClientNamesKeyValues();
		mckv.setBusinessObjectService(boSvc);
		
		InquiryForm inqForm = new InquiryForm();
		inqForm.setDataObject(opinion);
		
		InquiryForm inqForm2 = new InquiryForm();
		inqForm2.setDataObject(consideration);
		
		MatterTxForm txForm = mock(MatterTxForm.class);
		when(txForm.getDocument()).thenReturn(doc);
		
		UifFormBase[] forms = {inqForm, txForm, inqForm2};
		
		for (UifFormBase form: forms) {
			List<KeyValue> kvs = mckv.getKeyValues(form);
			assertFalse("should not be empty", kvs.isEmpty());
			assertEquals("size differs", 2, kvs.size());
		}
		
	}

}
