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
import org.martinlaw.bo.MatterRule;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.bo.MatterWorkRule;

/**
 * base class for testing transactional documents routing
 * 
 * @author mugo
 *
 */
public abstract class TxRoutingTestBase extends KewTestsBase {

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
		testTransactionalRoutingAndDocumentCRUD(docType);
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
	 * @see org.martinlaw.test.KewTestsBase#testTransactionalRouting(java.lang.String)
	 */
	/**
	 * tests routing in a real user-world manner since the postprocessor is {@link org.martinlaw.service.PostProcessorServiceImpl}
	 * 
	 * <p>The business object persistence is also tested</p>
	 */
	public void testTransactionalRoutingAndDocumentCRUD(String docType)
			throws WorkflowException {
		// changing doc type at runtime did not work
		/*DocumentType docTypeObj = KEWServiceLocator.getDocumentTypeService().findByName(getDocType());
		//prepare to save new version
		docTypeObj.setVersionNumber(null);
		docTypeObj.setDocumentTypeId(null);
		docTypeObj.setPostProcessorName("org.kuali.rice.kew.postprocessor.DefaultPostProcessor");
		KEWServiceLocator.getDocumentTypeService().versionAndSave(docTypeObj);*/
		
		Document doc = KRADServiceLocatorWeb.getDocumentService().saveDocument(getWorkDoc());
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