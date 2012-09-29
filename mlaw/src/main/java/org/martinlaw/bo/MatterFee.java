package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * associates a fee with a matter
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MatterFee extends Fee {

	/**
	 * 
	 */
	private static final long serialVersionUID = -818929880498017758L;
	@Id
	@Column(name = "fee_id")
	Long id;
	/**
	 * participates in a 1:1 relationship with matter, the object relationship is impl as a collection on the 
	 * matter side
	 */
	@Column(name = "matter_id", nullable = false)
	private Long matterId;

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

	public MatterFee() {
		super();
	}

	/**
	 * @return the matterId
	 */
	public Long getMatterId() {
		return matterId;
	}

	/**
	 * @param matterId the matterId to set
	 */
	public void setMatterId(Long matterId) {
		this.matterId = matterId;
	}

}