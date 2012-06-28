/**
 * 
 */
package org.martinlaw.bo;

import java.util.LinkedHashMap;

/**
 * represents an annex transaction document
 * @author mugo
 * @ojb.class table="martinlaw_annex_t"
 */
public class Annex extends org.kuali.rice.krad.bo.PersistableBusinessObjectBase {
	/**
	 * the primary key
	 * @ojb.field primarykey="true"
	 */
	private Long id;
	/**
	 * @ojb.reference foreignkey="annexDocumentNumber" auto-retrieve="true" auto-update="true" auto-delete="none"
	 */
	private AnnexDocument annexDocument;
	/**
	 * @ojb.field column="annex_doc_num" indexed="true" length="20"
	 */
	private String annexDocumentNumber;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8869868286366908789L;
	/** the annex type id 
	 * @ojb.field column="annex_type_id" indexed="true"
	 * */
	private Long typeId;
	/**the annex type whose pk is the typeId above
	 *@ojb.reference foreignkey="typeId" auto-retrieve="true" auto-update="true" auto-delete="none" 
	 */
	private AnnexType annexType;
	

	
	/**
	 * @return the typeId
	 */
	public Long getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	/**
	 * @return the annexType
	 */
	public AnnexType getAnnexType() {
		return annexType;
	}
	/**
	 * @param annexType the annexType to set
	 */
	public void setAnnexType(AnnexType annexType) {
		this.annexType = annexType;
	}

	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> props = new LinkedHashMap<String, Object>();
		props.put("typeId", getTypeId());
		props.put("id",	getId());
		props.put("annexDocumentNumber", getAnnexDocumentNumber());
		return props;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the annexDocument
	 */
	public AnnexDocument getAnnexDocument() {
		return annexDocument;
	}
	/**
	 * @param annexDocument the annexDocument to set
	 */
	public void setAnnexDocument(AnnexDocument annexDocument) {
		this.annexDocument = annexDocument;
	}
	/**
	 * @return the annexDocumentNumber
	 */
	public String getAnnexDocumentNumber() {
		return annexDocumentNumber;
	}
	/**
	 * @param annexDocumentNumber the annexDocumentNumber to set
	 */
	public void setAnnexDocumentNumber(String annexDocumentNumber) {
		this.annexDocumentNumber = annexDocumentNumber;
	}
}
