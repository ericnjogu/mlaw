/**
 * 
 */
package org.martinlaw.test.opinion;

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



import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.test.WorkRoutingTestBase;

/**
 * tests routing and perms for {@link Work}
 * 
 * @author mugo
 *
 */
public class OpinionWorkRoutingTest extends WorkRoutingTestBase {
	
	@Override
	public void testDocSearch() {
		// TODO not yet impl as doc search is not activated for matter work
	}

	@Override
	public MatterTxDocBase getTxDoc() throws WorkflowException {
		return getTestUtils().populateMatterWork((MatterWork) KRADServiceLocatorWeb.getDocumentService().getNewDocument(getDocType()));
	}

	@Override
	public String getDocType() {
		// TODO Auto-generated method stub
		return MartinlawConstants.DocTypes.OPINION_WORK;
	}
}
