package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class MatterClient extends MartinlawPerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1622883405457087014L;
	@Id
	@Column(name = "client_id")
	private Long id;
	@Column(name = "matter_id")
	private Long matterId;

	public MatterClient() {
		super();
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