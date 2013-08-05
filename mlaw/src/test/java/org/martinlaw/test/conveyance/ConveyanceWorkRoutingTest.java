/**
 * 
 */
package org.martinlaw.test.conveyance;

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
import org.martinlaw.bo.conveyance.Work;
import org.martinlaw.test.WorkRoutingTestBase;

/**
 * tests routing and perms for {@link Work}
 * 
 * @author mugo
 *
 */
public class ConveyanceWorkRoutingTest extends WorkRoutingTestBase {

	@Override
	public MatterTxDocBase getTxDoc() throws WorkflowException {
		Work newDocument = (Work) KRADServiceLocatorWeb.getDocumentService().getNewDocument(getDocTypeName());
		newDocument.setConveyanceAnnexTypeId(1001l);
		return getTestUtils().populateMatterWork(newDocument);
	}

	@Override
	public String getDocTypeName() {
		return MartinlawConstants.DocTypes.CONVEYANCE_WORK;
	}

	@Override
	public void testDocSearch() {
		// TODO not yet impl as doc search is not activated for matter work
	}
}
