/**
 * 
 */
package org.martinlaw.bo.conveyance;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.martinlaw.bo.MartinlawPerson;

/**
 * associates a conveyance to a kuali person
 * 
 * @author mugo
 * 
 */
@Entity
@Table(name="martinlaw_convey_client_t")
public class ConveyanceClient extends MartinlawPerson {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6185040053095072666L;
	@Id
	@Column(name="convey_client_id")
	private Long id;
	@Column(name="conveyance_id")
	private Long conveyanceId;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	@Override
	protected Map<String, Object> toStringMapper() {
		Map<String, Object> propMap = super.toStringMapper();
		propMap.put("id", getId());
		propMap.put("conveyanceId", getConveyanceId());
		return propMap;
	}

	/**
	 * @return the conveyanceId
	 */
	public Long getConveyanceId() {
		return conveyanceId;
	}

	/**
	 * @param conveyanceId the conveyanceId to set
	 */
	public void setConveyanceId(Long conveyanceId) {
		this.conveyanceId = conveyanceId;
	}

}
