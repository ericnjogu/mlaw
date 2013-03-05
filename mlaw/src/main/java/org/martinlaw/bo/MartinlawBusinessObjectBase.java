/**
 * 
 */
package org.martinlaw.bo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * holds common operations for business objects e.g. setting the created and modified time stamps
 * @author mugo
 *
 */
@MappedSuperclass
public class MartinlawBusinessObjectBase extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7188636821913277316L;
	@Column(name="date_created", columnDefinition="timestamp not null")
	private Timestamp dateCreated;
	@Column(name="date_modified", columnDefinition="timestamp null")
	private Timestamp dateModified;

	/**
	 * @return the dateCreated
	 */
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	/**
	 * @param ts the dateCreated to set
	 */
	public void setDateCreated(Timestamp ts) {
		this.dateCreated = ts;
	}
	/**
	 * @return the dateModified
	 */
	public Timestamp getDateModified() {
		return dateModified;
	}
	/**
	 * @param ts the dateModified to set
	 */
	public void setDateModified(Timestamp ts) {
		this.dateModified = ts;
	}
	/* (non-Javadoc)
	 * @see org.kuali.rice.krad.bo.PersistableBusinessObjectBase#prePersist()
	 */
	@Override
	protected void prePersist() {
		setDateCreated(new Timestamp(System.currentTimeMillis()));
		super.prePersist();
	}
}
