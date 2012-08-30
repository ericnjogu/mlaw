/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * represents a contract type
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_contract_type_t")
public class ContractType extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4801035275306699373L;
	@Id
	@Column(name="contract_type_id")
	private Long id;
	@Column(name="name", length = 50, nullable = false)
	private String name;
	@Column(name = "description", length = 100)
	private String description;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
