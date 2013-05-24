/**
 * 
 */
package org.martinlaw.auth;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * holds details of an openID activation
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_openid_activation_t")
public class OpenidActivation extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7824894783669768316L;
	@Id
	@Column(length=40)
	private String id;
	@Column(columnDefinition="timestamp default current_timestamp", updatable=false, insertable=false)
	private Timestamp created;
	@Column(length=100, nullable=false)
	private String destination;
	@Column(length=200, nullable=false)
	private String openid;
	@Column(length=200, nullable=false)
	private String entityId;
	@Column(columnDefinition="timestamp null")
	private Timestamp activated;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the created
	 */
	public Timestamp getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * @return the entityId
	 */
	public String getEntityId() {
		return entityId;
	}
	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	/**
	 * @return the activated
	 */
	public Timestamp getActivated() {
		return activated;
	}
	/**
	 * @param activated the activated to set
	 */
	public void setActivated(Timestamp activated) {
		this.activated = activated;
	}
}
