package org.martinlaw.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.rules.DocumentRuleBase;
import org.kuali.rice.krad.rules.TransactionalDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.martinlaw.bo.MatterWork;
import org.martinlaw.bo.MatterWorkRule;

public class WorkRoutingTestBase extends KewTestsBase {

	protected MatterWork workDoc;
	protected String docType;

	public WorkRoutingTestBase() {
		super();
	}

	/**
	 * tests transactional routing
	 * @throws WorkflowException 
	 */
	@Test
	public void testWorkRouting() throws WorkflowException {
		this.testTransactionalRouting(docType);
	}

	@Test
	public void testAttributeValidation() throws WorkflowException {
		workDoc.setMatterId(1001l);
		workDoc.getDocumentHeader().setDocumentDescription("testing");
		DocumentRuleBase ruleBase = new TransactionalDocumentRuleBase();
		if (!ruleBase.isDocumentAttributesValid(workDoc, true)) {
			workDoc.logErrors();
			fail("attributes should be valid");
		}
	}

	/**
	 * tests {@link org.martinlaw.bo.MatterWorkRule#processCustomSaveDocumentBusinessRules(Document)}
	 */
	@Test
	public void testprocessCustomSaveDocumentBusinessRules() {
		MatterWorkRule rule = new MatterWorkRule();
		// not setting a value will definitely result in an error - so use a non-existent value
		workDoc.setMatterId(2001l);
		assertFalse("rule should return false", rule.processCustomSaveDocumentBusinessRules(workDoc));
		assertTrue("there should be errors", GlobalVariables.getMessageMap().hasErrors());
		workDoc.logErrors();
		GlobalVariables.getMessageMap().clearErrorMessages();
		
		workDoc.setMatterId(1001l);
		assertTrue("rule should return true", rule.processCustomSaveDocumentBusinessRules(workDoc));
		assertTrue("there should be no errors", GlobalVariables.getMessageMap().hasNoErrors());
	}

}