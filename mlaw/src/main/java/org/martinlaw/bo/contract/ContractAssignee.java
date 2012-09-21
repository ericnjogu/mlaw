/**
 * 
 */
package org.martinlaw.bo.contract;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.martinlaw.bo.MartinlawPerson;

/**
 * specifies a person who has been assigned to work on a contract
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_contract_assignee_t")
public class ContractAssignee extends MartinlawPerson {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5336580846702092284L;
	@Id
	// disabled since there pks did not appear to be generated from the sequence table
    /*@GeneratedValue(generator="martinlaw_contract_assignee_s")
	@GenericGenerator(name="martinlaw_contract_assignee_s",strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",parameters={
			@Parameter(name="sequence_name",value="martinlaw_contract_assignee_s"),
			@Parameter(name="value_column",value="id")
	})*/
	@Column(name="contract_assignee_id")
	private Long id;
	
	@Column(name="contract_assignment_id")
	private Long contractAssignmentId;
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

	/**
	 * @return the contractAssignmentId
	 */
	public Long getContractAssignmentId() {
		return contractAssignmentId;
	}

	/**
	 * @param contractAssignmentId the contractAssignmentId to set
	 */
	public void setContractAssignmentId(Long contractAssignmentId) {
		this.contractAssignmentId = contractAssignmentId;
	}

}
