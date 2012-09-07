/**
 * 
 */
package org.martinlaw.bo.conveyance;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.martinlaw.bo.Fee;

/**
 * Fee represents a fee paid to a lawyer by a client
 * @author mugo
 */
@Entity
@Table(name="martinlaw_convey_fee_t")
// @Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ConveyanceFee extends Fee {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5139498341007335334L;
	@Id
	@Column(name="convey_fee_id")
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
	
	/**
	 * @ojb.field column= indexed="true"
	 * participates in a 1:1 relationship with conveyance, the object relationship is impl as a collection on the 
	 * conveyance side
	 */
	@Column(name="conveyance_id", nullable=false)
	private Long conveyanceId;
	
	/**
	 * @return the courtCaseId
	 */
	public Long getConveyanceId() {
		return conveyanceId;
	}
	/**
	 * @param conveyanceId the courtCaseId to set
	 */
	public void setConveyanceId(Long conveyanceId) {
		this.conveyanceId = conveyanceId;
	}
}
