/**
 * 
 */
package org.martinlaw.bo;

import java.sql.Date;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.martinlaw.bo.courtcase.CourtCaseCollectionBase;

/**
 * represents a hearing date set for a case
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_court_case_hearing_date_t")
public class HearingDate extends CourtCaseCollectionBase {
	/**
	 * default constructor
	 */
	public HearingDate() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3686195124782039856L;
	@Column(name="hearing_date", nullable=false)
	private Date date;
	
	/**
	 * initializes fields to the param list
	 * 
	 * @param date
	 * @param comment
	 * @param courtCaseId TODO
	 */
	public HearingDate(Date date, String comment, Long courtCaseId) {
		super();
		this.date = date;
		this.comment = comment;
		super.setCourtCaseId(courtCaseId);
	}
	
	@Column(name="date_comment", length=150)
	private String comment;
	@Id
	@Column(name="court_case_hearing_date_id")
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
	/* (non-Javadoc)
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	@Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> propMap = super.toStringMapper();
		propMap.put("comment", getComment());
		propMap.put("date", getDate());
		return propMap;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * any relevant comment - optional
	 * 
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

}
