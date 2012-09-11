/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * defines a date type for use in court case or other matters
 * 
 * <p>e.g. Hearing Date, Mention Date, Bring up Date</p>
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_date_type_t")
public class DateType extends BaseDetail {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7245197877574711265L;
	@Id
	@GeneratedValue(generator="martinlaw_date_type_id_s")
	@GenericGenerator(name="martinlaw_date_type_id_s",strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",parameters={
			@Parameter(name="sequence_name",value="martinlaw_date_type_id_s"),
			@Parameter(name="value_column",value="id")
	})
	@Column(name="date_type_id")
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
