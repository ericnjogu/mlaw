/**
 * 
 */
package org.martinlaw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.rule.Rule;
import org.kuali.rice.kew.doctype.service.DocumentTypeService;
import org.kuali.rice.kew.service.KEWServiceLocator;

/**
 * @author mugo
 *
 */
public class KEWRulesDoctypeTest extends KewTestsBase {
	/*private RuleService ruleSvc;
	private DocumentTypeService docTypeService;*/
	/**
	 * convenience method to retrive obj ref 
	 */
	private org.kuali.rice.kew.api.rule.RuleService getRuleSvc() {
		return KewApiServiceLocator.getRuleService();
	}
	private DocumentTypeService getDocTypeSvc() {
		return KEWServiceLocator.getDocumentTypeService();
	}
	@Test
	public void testCaseRule() {
		//KualiRuleService ruleSvc = KNSServiceLocator.getKualiRuleService();
		//getRuleSvc().
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
	public void testCaseStatusRule () {
		Rule rule = getRuleSvc().getRuleByName("org.martinlaw.rules.caseStatus");
		assertEquals("Routing rule for case status maintenance", rule.getDescription());
	}
	
	@Test
	@Ignore
	public void testConveyanceAnnexRule() {
		Rule rule = getRuleSvc().getRuleByName("org.martinlaw.rules.conveyanceAnnex");
		assertEquals("Routing rule for conveyance annex transaction document", rule.getDescription());
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.rice.test.RiceTestCase#getBaseDir()
	 */
	//this is set in eclipse junit run configuration for working directory
	/*@Override
	protected String getBaseDir() {
		return "/home/mugo/apps/rice-1.0.3.1-src/impl";
	}*/
	@Test
	public void testCaseDoctype() {
		assertNotNull(getDocTypeSvc().findByName("CaseMaintenanceDocument"));
	}
	@Test
	@Ignore
	public void testConveyanceDocType() {
		assertNotNull(getDocTypeSvc().findByName("ConveyanceMaintenanceDocument"));
	}
	@Test
	public void testCaseStatusDocType() {
		assertNotNull(getDocTypeSvc().findByName("CaseStatusMaintenanceDocument"));
	}
	
	@Test
	@Ignore
	public void testConveyanceAnnexDocType() {
		assertNotNull(getDocTypeSvc().findByName("ConveyanceAnnexDocument"));
	}
}
