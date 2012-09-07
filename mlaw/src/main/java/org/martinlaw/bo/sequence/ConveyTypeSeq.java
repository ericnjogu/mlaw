package org.martinlaw.bo.sequence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * used to get hibernate to create a sequence file for {@link org.martinlaw.bo.conveyance.ConveyanceType}
 * 
 * @author mugo
 *
 */
 
@Entity
@Table(name="martinlaw_convey_type_id_s")
public class ConveyTypeSeq {
	@Id
	@Column(columnDefinition="bigint auto_increment")
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
