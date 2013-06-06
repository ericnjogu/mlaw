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


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.datadictionary.DocumentEntry;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.DocumentRuleBase;
import org.kuali.rice.krad.rules.TransactionalDocumentRuleBase;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.martinlaw.bo.MatterTxBusinessRulesBase;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.bo.MatterWorkRule;

/**
 * base class for testing transactional documents routing
 * 
 * @author mugo
 *
 */
public abstract class TxRoutingTestBase extends KewTestsBase {

	private MatterTxBusinessRulesBase rule;

	public TxRoutingTestBase() {
		super();
	}

	/**
	 * tests transactional routing
	 * @throws WorkflowException 
	 */
	@Test
	public void testRouting() throws WorkflowException {
		GlobalVariables.setUserSession(new UserSession("lawyer1"));
		getTestUtils().testTransactionalRoutingInitToFinal(getTxDoc());
	}

	@Test
	public void testAttributeValidation() throws WorkflowException {
		getTxDoc().setMatterId(1001l);
		getTxDoc().getDocumentHeader().setDocumentDescription("testing");
		DocumentRuleBase ruleBase = new TransactionalDocumentRuleBase();
		if (!ruleBase.isDocumentAttributesValid(getTxDoc(), true)) {
			getTxDoc().logErrors();
			fail("attributes should be valid");
		}
	}

	/**
	 * tests {@link org.martinlaw.bo.MatterWorkRule#processCustomSaveDocumentBusinessRules(Document)}
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws WorkflowException 
	 */
	@Test
	public void testProcessCustomSaveDocumentBusinessRules() throws InstantiationException, IllegalAccessException, WorkflowException {
		// not setting a value will definitely result in an error - so use a non-existent value
		GlobalVariables.setUserSession(new UserSession("clerk1"));
		MatterTxDocBase txDoc = getTxDoc();
		txDoc.setMatterId(2001l);
		assertFalse("rule should return false", getRule().processCustomRouteDocumentBusinessRules(txDoc));
		assertTrue("there should be errors", GlobalVariables.getMessageMap().hasErrors());
		txDoc.logErrors();
		GlobalVariables.getMessageMap().clearErrorMessages();
		
		txDoc.setMatterId(1001l);
		assertTrue("rule should return true", getRule().processCustomRouteDocumentBusinessRules(txDoc));
		txDoc.logErrors();
		assertTrue("there should be no errors", GlobalVariables.getMessageMap().hasNoErrors());
	}
	
	/**
	 * provide children classes with a way of specifying the rule - which is {@link MatterWorkRule} by default
	 * 
	 * @return the business rule class
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private MatterTxBusinessRulesBase getRule() throws InstantiationException, IllegalAccessException {
		if (rule == null) {
			DocumentEntry entry = KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getDocumentEntry(getDocType());
			if (entry != null) {
				rule = (MatterTxBusinessRulesBase) entry.getBusinessRulesClass().newInstance();
			}
		}
		return rule;
	}
	
	/* (non-Javadoc)
	 * @see org.martinlaw.test.KewTestsBase#testTransactionalRouting(java.lang.String)
	 */
	/**
	 * tests routing in a real user-world manner since the postprocessor is {@link org.martinlaw.service.PostProcessorServiceImpl}
	 * 
	 * <p>The business object persistence is also tested</p>
	 * @param txDoc - the populated transactional document
	 * @param docType - the document type
	 */
	@Deprecated//does not work anymore when document search is activated, causes 'user not authorized' errors
	public void testTransactionalRoutingAndDocumentCRUD(String docType, MatterTxDocBase txDoc)
			throws WorkflowException {
		GlobalVariables.setUserSession(new UserSession("clerk1"));
		Document doc = KRADServiceLocatorWeb.getDocumentService().saveDocument(txDoc);
		assertTrue("document should have been saved", doc.getDocumentHeader().getWorkflowDocument().isSaved());
		KRADServiceLocatorWeb.getDocumentService().routeDocument(doc, "submitted", null);
		
		// approve as lawyer1
		GlobalVariables.setUserSession(new UserSession("lawyer1"));
		doc = KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(doc.getDocumentNumber());
		assertTrue("document should be enroute", doc.getDocumentHeader().getWorkflowDocument().isEnroute());
		KRADServiceLocatorWeb.getDocumentService().approveDocument(doc, "approved", null);
		
		//retrieve again to confirm status
		doc = KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(doc.getDocumentNumber());
		assertTrue("document should have been approved", doc.getDocumentHeader().getWorkflowDocument().isApproved());
		assertTrue("document should be final", doc.getDocumentHeader().getWorkflowDocument().isFinal());
	}
	
	/**
	 * @return the transaction document object
	 * @throws WorkflowException 
	 */
	public abstract MatterTxDocBase getTxDoc() throws WorkflowException;
	
	/**
	 * @return the docType
	 */
	public abstract String getDocType();

	/**
	 * @param rule the rule to set
	 */
	public void setRule(MatterTxBusinessRulesBase rule) {
		this.rule = rule;
	}
	
	/**
	 * expect descendants to implement doc search
	 */
	public abstract void testDocSearch();

}