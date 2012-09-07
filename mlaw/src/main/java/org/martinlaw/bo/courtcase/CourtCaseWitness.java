/**
 * 
 */
package org.martinlaw.bo.courtcase;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * links a court case to a kuali person who is a witness
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_court_case_witness_t")
public class CourtCaseWitness extends CourtCasePerson {
	/**
	 * 
	 */
	private static final long serialVersionUID = 562941243075608345L;
	@Id
	@Column(name="court_case_witness_id")
	private Long id;

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

	/* (non-Javadoc)
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	@Override
	protected Map<String, Object> toStringMapper() {
		Map<String, Object> propMap = super.toStringMapper();
		propMap.put("id", getId());
		return propMap;
	}

}
