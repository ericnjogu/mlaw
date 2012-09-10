package org.martinlaw.bo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

@MappedSuperclass
public class MatterDate extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8395850579903958430L;
	@Column(name = "matter_date", nullable = false)
	private Date date;
	@Column(name = "date_comment", length = 150)
	private String comment;

	public MatterDate() {
		super();
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