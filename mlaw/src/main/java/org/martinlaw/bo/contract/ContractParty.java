/**
 * 
 */
package org.martinlaw.bo.contract;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * represents a party to a contract
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_contract_party_t")
public class ContractParty extends ContractPerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2139426065091120287L;
	/**
	 * default constructor
	 */
	public ContractParty() {
		super();
	}
	
	/**
	 * initializes principal name
	 */
	public ContractParty(String principalName) {
		setPrincipalName(principalName);
	}

	@Id
	@Column(name="contract_party_id")
	private Long id;
	/* (non-Javadoc)
	 * @see org.martinlaw.bo.MartinlawPerson#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;

	}

	/* (non-Javadoc)
	 * @see org.martinlaw.bo.MartinlawPerson#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

}
