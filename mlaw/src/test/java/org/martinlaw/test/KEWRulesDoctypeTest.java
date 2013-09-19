/**
 * 
 */
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
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.rule.Rule;

/**
 * @author mugo
 *
 */
public class KEWRulesDoctypeTest extends KewTestsBase {
	/**
	 * convenience method to retrive obj ref 
	 */
	private org.kuali.rice.kew.api.rule.RuleService getRuleSvc() {
		return KewApiServiceLocator.getRuleService();
	}
	@Test
	public void testDefaultApprovalRule() {
		Rule rule = getRuleSvc().getRuleByName("org.martinlaw.rules.default.approval");
		assertEquals("default routing rule for maintenance docs", rule.getDescription());
		assertEquals("org.martinlaw.defaultApprovalTemplate",rule.getRuleTemplate().getName());
	}
	
	@Test
	public void testInitiatorFyiRule() {
		Rule rule = getRuleSvc().getRuleByName("org.martinlaw.rules.default.initiatorFyi");
		assertEquals("FYI notification to the initiator notifying them their edoc has been processed", rule.getDescription());
	}
	
	@Test
	public void testCaseDoctype() {
		assertNotNull(getDocTypeSvc().findByName("CourtCaseMaintenanceDocument"));
	}
	
	@Test
	public void testConveyanceDocType() {
		assertNotNull(getDocTypeSvc().findByName("ConveyanceMaintenanceDocument"));
	}
	
	@Test
	public void testCaseStatusDocType() {
		assertNotNull(getDocTypeSvc().findByName("StatusMaintenanceDocument"));
	}
	
	@Test
	public void testConveyanceTypeDocType() {
		assertNotNull(getDocTypeSvc().findByName("ConveyanceTypeMaintenanceDocument"));
	}
	public String getDocTypeName() {
		return null;
	}
	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#testInitiatorFYI()
	 */
	/**
	 * does not represent a particular document type
	 */
	@Override
	public void testInitiatorFYI() {
		// DO nothing
	}
	@Override
	public Class<?> getDataObjectClass() {
		return null;
	}
	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#testCreateMaintain(java.lang.Class, java.lang.String)
	 */
	@Override
	protected void testCreateMaintain(Class<?> klass, String docType) {
		// do nothing
	}
}
