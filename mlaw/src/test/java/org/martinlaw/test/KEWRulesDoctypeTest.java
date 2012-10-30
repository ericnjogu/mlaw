/**
 * 
 */
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
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
	public void testCaseRule() {
		Rule rule = getRuleSvc().getRuleByName("org.martinlaw.rules.case");
		assertEquals("Routing rule for case maintenance", rule.getDescription());
		assertEquals("org.martinlaw.defaultApprovalTemplate",rule.getRuleTemplate().getName());
	}
	@Test
	@Ignore
	public void testConveyanceRule() {
		Rule rule = getRuleSvc().getRuleByName("org.martinlaw.rules.conveyance");
		assertEquals("Routing rule for conveyance maintenance", rule.getDescription());
	}
	
	@Test
	@Ignore("status routes to final, there is no need for a rule")
	public void testCaseStatusRule () {
		Rule rule = getRuleSvc().getRuleByName("org.martinlaw.rules.status");
		assertEquals("Routing rule for case status maintenance", rule.getDescription());
	}
	
	@Test
	public void testConveyanceTypeRule() {
		Rule rule = getRuleSvc().getRuleByName("org.martinlaw.rules.conveyanceType");
		assertEquals("Routing rule for ConveyanceTypeDocument maintenance", rule.getDescription());
	}
	
	@Test
	public void testCaseDoctype() {
		assertNotNull(getDocTypeSvc().findByName("CaseMaintenanceDocument"));
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
	
	@Test()
	@Ignore("will maintained as part of conveyance type")
	public void testConveyanceAnnexTypeDocType() {
		assertNotNull(getDocTypeSvc().findByName("ConveyanceAnnexTypeDocument"));
	}
}
