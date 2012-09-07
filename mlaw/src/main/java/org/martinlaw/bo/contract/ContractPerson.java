/**
 * 
 */
package org.martinlaw.bo.contract;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.martinlaw.bo.MartinlawPerson;

/**
 * common base class for specifying a unidirectional relationship between a person and a contract
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class ContractPerson extends MartinlawPerson {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7225621292990852704L;
	@Column(name="contract_id", nullable=false)
	private Long contractId;

	/**
	 * @return the contractId
	 */
	public Long getContractId() {
		return contractId;
	}

	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

}
