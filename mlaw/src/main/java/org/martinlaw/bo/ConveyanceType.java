/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * represents a type of conveyance e.g. lease agreement or sale of land
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_convey_type_t")
public class ConveyanceType extends BaseDetail {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7220248295960647672L;
	@Id
	@Column(name="convey_type_id")
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
