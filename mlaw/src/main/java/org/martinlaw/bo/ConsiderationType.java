/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * specifies consideration type e.g. legal fee, contract value, deposit etc
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_consideration_type_t")
public class ConsiderationType extends BaseDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4184910355599564922L;
	@Id
	@Column(name="consideration_type_id")
	private Long id;
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

}
