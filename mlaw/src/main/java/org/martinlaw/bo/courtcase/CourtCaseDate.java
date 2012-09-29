/**
 * 
 */
package org.martinlaw.bo.courtcase;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.martinlaw.bo.MatterDate;


/**
 * represents a hearing date set for a case
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_court_case_date_t")
public class CourtCaseDate extends MatterDate {
	@Column(name="court_case_id", nullable=false)
	private Long courtCaseId;
	/**
	 * default constructor
	 */
	public CourtCaseDate() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3686195124782039856L;
	/**
	 * initializes fields to the param list
	 * 
	 * @param date
	 * @param comment
	 * @param courtCaseId TODO
	 */
	public CourtCaseDate(Date date, String comment, Long courtCaseId) {
		super();
		setDate(date);
		setComment(comment);
		setCourtCaseId(courtCaseId);
	}
	
	@Id
    /*@GeneratedValue(generator="martinlaw_court_case_date_id_s")
	@GenericGenerator(name="martinlaw_court_case_date_id_s",strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",parameters={
			@Parameter(name="sequence_name",value="martinlaw_court_case_date_id_s"),
			@Parameter(name="value_column",value="id")
	})*/
	@Column(name="court_case_date_id")
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
	
	/**
	 * @return the courtCaseId
	 */
	public Long getCourtCaseId() {
		return courtCaseId;
	}
	/**
	 * @param courtCaseId the courtCaseId to set
	 */
	public void setCourtCaseId(Long courtCaseId) {
		this.courtCaseId = courtCaseId;
	}
}
