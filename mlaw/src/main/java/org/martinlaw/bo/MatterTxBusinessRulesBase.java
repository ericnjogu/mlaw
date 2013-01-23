package org.martinlaw.bo;

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


import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.TransactionalDocumentRuleBase;
import org.martinlaw.MatterBusinessRulesHelper;

/**
 * adds some validation checks for {@link MatterTxDocBase} documents
 * 
 * @author mugo
 *
 */
public class MatterTxBusinessRulesBase extends TransactionalDocumentRuleBase {

	private MatterBusinessRulesHelper rulesHelper;



	public MatterTxBusinessRulesBase() {
		super();
		setRulesHelper(new MatterBusinessRulesHelper());
	}

	

	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.rules.DocumentRuleBase#processCustomSaveDocumentBusinessRules(org.kuali.rice.krad.document.Document)
	 */
	@Override
	public boolean processCustomSaveDocumentBusinessRules(Document document) {
		MatterTxDocBase matterWork = (MatterTxDocBase) document;
		if (matterWork.isMatterIdValid()) {
				return true;
		} else {
			@SuppressWarnings("rawtypes")
			Class<? extends Matter> matterClass = matterWork.getMatterClass();
			getRulesHelper().addMatterIdError(getRulesHelper().createMatterNotExistingError(matterClass));
			return false;
		}
	}



	/**
	 * @return the rulesHelper
	 */
	public MatterBusinessRulesHelper getRulesHelper() {
		return rulesHelper;
	}



	/**
	 * @param rulesHelper the rulesHelper to set
	 */
	public void setRulesHelper(MatterBusinessRulesHelper rulesHelper) {
		this.rulesHelper = rulesHelper;
	}

}