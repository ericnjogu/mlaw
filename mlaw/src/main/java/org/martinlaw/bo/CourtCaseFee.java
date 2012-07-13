/**
 * 
 */
package org.martinlaw.bo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Fee represents a fee paid to a lawyer by a client
 * @author mugo
 */
@Entity
@Table(name="martinlaw_court_case_fee_t")
// @Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class CourtCaseFee extends Fee {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5139498341007335334L;
	@Id
	@Column(name="court_case_fee_id")
	Long id;
	
	/**
	 * get the primary key
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
