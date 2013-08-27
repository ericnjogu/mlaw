/**
 * 
 */
package org.martinlaw.bo.courtcase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * represents a lands case
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_land_case_t")
public class LandCase extends CourtCase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7112335735355595868L;
	@Column(name="land_reference", length=200)
	private String landReference;

	/**
	 * @return the landReference
	 */
	public String getLandReference() {
		return landReference;
	}

	/**
	 * @param landReference the landReference to set
	 */
	public void setLandReference(String landReference) {
		this.landReference = landReference;
	}
}
