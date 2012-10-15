/**
 * 
 */
package org.martinlaw.test.contract;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.rules.DocumentRuleBase;
import org.kuali.rice.krad.rules.TransactionalDocumentRuleBase;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.Constants;
import org.martinlaw.bo.MatterWorkRule;
import org.martinlaw.bo.contract.Work;
import org.martinlaw.test.KewTestsBase;

/**
 * tests routing and perms for {@link Work}
 * 
 * @author mugo
 *
 */
public class ContractWorkRoutingTest extends KewTestsBase {
	private Work contractWorkDoc;

	/**
	 * tests transactional routing
	 * @throws WorkflowException 
	 */
	@Test
	public void testContractWorkRouting() throws WorkflowException {
		super.testTransactionalRouting(Constants.DocTypes.CONTRACT_WORK);
	}
	
	@Test
	/**
	 * tests attribute validation
	 */
	public void testAttributeValidation() throws WorkflowException {
		contractWorkDoc.setMatterId(1001l);
		// something in the framework is initializing this field, so try to replicate that behaviour here
		// contractWorkDoc.setMatter(new Contract());
		contractWorkDoc.getDocumentHeader().setDocumentDescription("testing");
		DocumentRuleBase ruleBase = new TransactionalDocumentRuleBase();
		if (!ruleBase.isDocumentAttributesValid(contractWorkDoc, true)) {
			contractWorkDoc.logErrors();
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
		contractWorkDoc.setMatterId(2001l);
		assertFalse("rule should return false", rule.processCustomSaveDocumentBusinessRules(contractWorkDoc));
		assertTrue("there should be errors", GlobalVariables.getMessageMap().hasErrors());
		contractWorkDoc.logErrors();
		GlobalVariables.getMessageMap().clearErrorMessages();
		
		contractWorkDoc.setMatterId(1001l);
		assertTrue("rule should return true", rule.processCustomSaveDocumentBusinessRules(contractWorkDoc));
		assertTrue("there should be no errors", GlobalVariables.getMessageMap().hasNoErrors());
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.MartinlawTestsBase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		GlobalVariables.setUserSession(new UserSession("clerk1"));
		contractWorkDoc = (Work) KRADServiceLocatorWeb.getDocumentService().getNewDocument(Constants.DocTypes.CONTRACT_WORK);
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-type-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/contract-test-data.sql", ";").runSql();
	}
}
