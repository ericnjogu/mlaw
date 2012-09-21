/**
 * 
 */
package org.martinlaw.bo.contract;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * associates a contract to the people/person assigned to work on it
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_contract_assignment_t")
public class Assignment extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	public Assignment() {
		super();
		assignees = new ArrayList<Assignee>();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1303934008600315215L;
	@Id
    /*@GeneratedValue(generator="martinlaw_contract_assignment_s")
	@GenericGenerator(name="martinlaw_contract_assignment_s",strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",parameters={
			@Parameter(name="sequence_name",value="martinlaw_contract_assignment_s"),
			@Parameter(name="value_column",value="id")
	})*/
	@Column(name="contract_assignment_id")
	private Long id;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="contractAssignmentId")
	private List<Assignee> assignees;
	@Transient
	private Long contractId;
	@OneToOne
	@JoinColumn(name = "contract_id", nullable = false, updatable = false)
	private Contract contract;

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

	/**
	 * @return the id
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
	 * @return the assignees
	 */
	public List<Assignee> getAssignees() {
		return assignees;
	}

	/**
	 * @param assignees the assignees to set
	 */
	public void setAssignees(List<Assignee> assignees) {
		this.assignees = assignees;
	}

	/**
	 * @return the contract
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract the contract to set
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}
}
