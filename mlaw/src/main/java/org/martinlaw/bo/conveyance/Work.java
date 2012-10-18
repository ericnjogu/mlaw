/**
 * 
 */
package org.martinlaw.bo.conveyance;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.martinlaw.bo.MatterWork;

/**
 * a transactional document whose notes/attachments have work done about a {@link Conveyance}
 * 
 * @author mugo
 *
 */
@Entity(name="convey_work")
@Table(name="martinlaw_convey_work_doc_t")
public class Work extends MatterWork {
	// declared using join column below @Column(name="convey_annex_type_id", nullable=false)
	@Transient
	private Long conveyanceAnnexTypeId;
	@OneToOne
	@JoinColumn(name = "convey_annex_type_id", nullable = false, updatable = false)
	private ConveyanceAnnexType conveyanceAnnexType;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3180199728494497136L;

	/**
	 * default constructor to initialize matter class
	 * 
	 * adapted from {@link http://stackoverflow.com/questions/182636/how-to-determine-the-class-of-a-generic-type}
	 */
	public Work() {
		super();
		setMatterClass(Conveyance.class);
	}

	/**
	 * gets the annex type e.g. search document or copy of certificate, as is determined by the conveyance type
	 * 
	 * @return the conveyanceAnnexTypeId
	 */
	public Long getConveyanceAnnexTypeId() {
		return conveyanceAnnexTypeId;
	}

	/**
	 * @param conveyanceAnnexTypeId the conveyanceAnnexTypeId to set
	 */
	public void setConveyanceAnnexTypeId(Long conveyanceAnnexTypeId) {
		this.conveyanceAnnexTypeId = conveyanceAnnexTypeId;
	}

	/**
	 * gets the annex type - would only work in jpa as the reference definition is not present in ojb
	 * 
	 * <p>The association helps to validate that the conveyance annex type id given exists</p>
	 * 
	 * @return the conveyanceAnnexType
	 */
	public ConveyanceAnnexType getConveyanceAnnexType() {
		return conveyanceAnnexType;
	}

	/**
	 * @param conveyanceAnnexType the conveyanceAnnexType to set
	 */
	public void setConveyanceAnnexType(ConveyanceAnnexType conveyanceAnnexType) {
		this.conveyanceAnnexType = conveyanceAnnexType;
	}

}
