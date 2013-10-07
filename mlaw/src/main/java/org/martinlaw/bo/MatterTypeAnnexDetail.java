/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * track the many to many relationship between {@link MatterType} and{@link MatterAnnexType}
 * <p>i.e. a matter type can have many annex types defined, 
 * while an annex type can be included in many matter types e.g. 'copy of national id'</p>
 * <p>It could in the future be used to hold more information 
 * e.g. approval requirement could be conditioned on which matter type an annex is included in</p>
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_matter_type_annex_detail")
public class MatterTypeAnnexDetail extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4450665146695950494L;
	@Column(name="matter_type_id")
	private Long matterTypeId;
	@Id
	@ManyToOne 
	@JoinColumn(name = "matter_type_id")
	private MatterType matterType;
	
	@Column(name="annex_type_id")
	private Long annexTypeId;
	@Id
	@ManyToOne 
	@JoinColumn(name = "annex_type_id")
	private MatterAnnexType annexType;
	// jpa will automatically create a column of this name 
	private Long sequence = 1l;
	/**
	 * @return the sequence
	 */
	public Long getSequence() {
		return sequence;
	}
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}
	/**
	 * @return the matterTypeId
	 */
	public Long getMatterTypeId() {
		return matterTypeId;
	}
	/**
	 * @param matterTypeId the matterTypeId to set
	 */
	public void setMatterTypeId(Long matterTypeId) {
		this.matterTypeId = matterTypeId;
	}
	/**
	 * @return the matterType
	 */
	public MatterType getMatterType() {
		return matterType;
	}
	/**
	 * @param matterType the matterType to set
	 */
	public void setMatterType(MatterType matterType) {
		this.matterType = matterType;
	}
	/**
	 * @return the annexTypeId
	 */
	public Long getAnnexTypeId() {
		return annexTypeId;
	}
	/**
	 * @param annexTypeId the annexTypeId to set
	 */
	public void setAnnexTypeId(Long annexTypeId) {
		this.annexTypeId = annexTypeId;
	}
	/**
	 * @return the annexType
	 */
	public MatterAnnexType getAnnexType() {
		return annexType;
	}
	/**
	 * @param annexType the annexType to set
	 */
	public void setAnnexType(MatterAnnexType annexType) {
		this.annexType = annexType;
	}
}
