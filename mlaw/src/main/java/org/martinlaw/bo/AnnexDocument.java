/**
 * 
 */
package org.martinlaw.bo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.rice.krad.document.SessionDocument;
import org.kuali.rice.krad.document.TransactionalDocumentBase;
import org.kuali.rice.krad.service.KRADServiceLocator;

/**
 * represents an annex transaction document
 * @author mugo
 * @ojb.class table="martinlaw_annex_document_t"
 */
public class AnnexDocument extends TransactionalDocumentBase implements SessionDocument {
	Log log = LogFactory.getLog(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = -8368407052265212058L;
	/**
	 *@ojb.reference foreignkey="annexId" auto-retrieve="true" auto-update="true" auto-delete="none" 
	 */
	private Annex annex;
	/** 
	 * @ojb.field column=annex_id
	 */
	private Long annexId;
	/**
	 * @param annex the annex to set
	 */
	public void setAnnex(Annex annex) {
		this.annex = annex;
	}

	/**
	 * @return the annex
	 */
	public Annex getAnnex() {
		return annex;
	}

	/**
	 * @param annexId the annexId to set
	 */
	public void setAnnexId(Long annexId) {
		this.annexId = annexId;
	}

	/**
	 * @return the annexId
	 */
	public Long getAnnexId() {
		return annexId;
	}
	
	/**
	 * to fulfil the xmldoclet req to have a pk in this class.
	 * We are actually using super.getDocumentNumber as the real pk
	 * @ojb.field primarykey="true" column="fake_pk" index="true" length="20"
	 */
	private String fakePk;
	
	public void setFakePk(String fakePk) {
		this.fakePk = fakePk;
	}

	public String getFakePk() {
		return fakePk;
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.kns.document.DocumentBase#prepareForSave()
	 */
	@Override
	public void prepareForSave() {
		if (getAnnex() != null && getAnnex().getTypeId()!= null) {
			getAnnex().setAnnexDocumentNumber(getDocumentNumber());
			log.info(getAnnex().toStringMapper());
			KRADServiceLocator.getBusinessObjectService().save(getAnnex());
			
		}
		super.prepareForSave();
	}
}
