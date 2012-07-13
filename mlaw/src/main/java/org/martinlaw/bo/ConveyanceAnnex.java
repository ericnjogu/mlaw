/**
 * 
 */
package org.martinlaw.bo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * associates a conveyance annex type to a list of attachments
 * 
 * A conveyance annex type e.g. 'lands board approval' could have several documents - minutes, letter, payment receipt etc
 * @see ConveyanceAnnexType
 * @see ConveyanceAttachment
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_convey_annex_t")
public class ConveyanceAnnex extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8135118531784410247L;
	@Id
	@Column(name="convey_annex_id")
	private Long id;
	//needed by ojb to associate the ConveyanceAnnexType
	//@Column(name="convey_annex_type_id")
	private Long conveyanceAnnexTypeId;
	// helps generate this column and the fk constraint
	@OneToOne
	@JoinColumn(name="convey_annex_type_id", nullable=false, updatable=false)
	private ConveyanceAnnexType type;
	@Column(name="conveyance_id", nullable=false)
	private Long conveyanceId;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="conveyanceAnnexId")
	private List<ConveyanceAttachment> attachments;
	/**
	 * the primary key
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
	/**
	 * gets the foreign key for the conveyance annex type
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
	 * gets the conveyance annex type being associated with the attachments
	 * @return the type
	 */
	public ConveyanceAnnexType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(ConveyanceAnnexType type) {
		this.type = type;
	}
	/**
	 * gets the foreign key of the conveyance that contains this conveyance annex
	 * 
	 * it is a many to one relationship (from this end) and can be made bidirectional if needed
	 * 
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
	/**
	 * gets the list of attachments associated with the conveyance annex type referred to herein
	 * @return the attachments
	 */
	public List<ConveyanceAttachment> getAttachments() {
		return attachments;
	}
	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(List<ConveyanceAttachment> attachments) {
		this.attachments = attachments;
	}
	
}
