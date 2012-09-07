/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * represents a signatory to a contract
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_contract_signatory_t")
public class ContractSignatory extends ContractPerson {

	/**
	 * initializes principal name
	 */
	public ContractSignatory(String principalName) {
		setPrincipalName(principalName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2139426065091120287L;
	/**
	 * default constructor
	 */
	public ContractSignatory() {
		super();
	}

	@Id
	@Column(name="contract_signatory_id")
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
