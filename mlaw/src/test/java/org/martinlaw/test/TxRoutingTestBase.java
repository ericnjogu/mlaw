package org.martinlaw.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.datadictionary.DocumentEntry;
import org.kuali.rice.krad.rules.DocumentRuleBase;
import org.kuali.rice.krad.rules.TransactionalDocumentRuleBase;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.test.SQLDataLoader;
import org.martinlaw.bo.MatterRule;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.bo.MatterWorkRule;

/**
 * base class for testing transactional documents routing
 * 
 * @author mugo
 *
 */
public class TxRoutingTestBase extends KewTestsBase {

	private MatterTxDocBase workDoc;
	private String docType;
	private MatterRule rule;

	public TxRoutingTestBase() {
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
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testprocessCustomSaveDocumentBusinessRules() throws InstantiationException, IllegalAccessException {
		// not setting a value will definitely result in an error - so use a non-existent value
		workDoc.setMatterId(2001l);
		assertFalse("rule should return false", getRule().processCustomSaveDocumentBusinessRules(workDoc));
		assertTrue("there should be errors", GlobalVariables.getMessageMap().hasErrors());
		workDoc.logErrors();
		GlobalVariables.getMessageMap().clearErrorMessages();
		
		workDoc.setMatterId(1001l);
		assertTrue("rule should return true", getRule().processCustomSaveDocumentBusinessRules(workDoc));
		assertTrue("there should be no errors", GlobalVariables.getMessageMap().hasNoErrors());
	}
	
	/**
	 * provide children classes with a way of specifying the rule - which is {@link MatterWorkRule} by default
	 * 
	 * @return the business rule class
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private MatterRule getRule() throws InstantiationException, IllegalAccessException {
		if (rule == null) {
			DocumentEntry entry = KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getDocumentEntry(getDocType());
			if (entry != null) {
				rule = (MatterRule) entry.getBusinessRulesClass().newInstance();
			}
		}
		return rule;
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#loadSuiteTestData()
	 */
	@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
	}

	/**
	 * @return the workDoc
	 */
	public MatterTxDocBase getWorkDoc() {
		return workDoc;
	}

	/**
	 * @param workDoc the workDoc to set
	 */
	public void setWorkDoc(MatterTxDocBase workDoc) {
		this.workDoc = workDoc;
	}

	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @param rule the rule to set
	 */
	public void setRule(MatterRule rule) {
		this.rule = rule;
	}

}