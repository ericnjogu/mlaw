/**
 * 
 */
package org.martinlaw.bo;

import java.util.LinkedHashMap;

/**
 * maintains the many to one relationship btn case and annex (an annex can be included in several cases)
 * @author mugo
 * @ojb.class table="court_case_annex_t"
 */
public class CaseAnnex extends CourtCaseCollectionBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6804606078781712488L;
	/**
	 * the edoc number
	 * @ojb.field length="20" column="annex_doc_number"
	 */
	private Long annexId;
	/**
	 * the annex document represented by the annexDocumentNumber above 
	 * @ojb.reference foreignkey="annexId" auto-retrieve="true" auto-update="none"	auto-delete="none"
	 */
	private Annex annex;
	
	/**
	 * @return the annex
	 */
	public Annex getAnnex() {
		return annex;
	}
	/**
	 * @param annex the annex to set
	 */
	public void setAnnex(Annex annex) {
		this.annex = annex;
	}
	/* (non-Javadoc)
	 * @see org.martinlaw.bo.CourtCaseCollectionBase#toStringMapper()
	 */
	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> props = super.toStringMapper();
		props.put("annexId", getAnnexId());
		return props;
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
}
