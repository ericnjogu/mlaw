/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.rice.krad.bo.Attachment;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * associates a conveyance annex to an attachment (also referred to as a note by rice)
 * 
 * @author mugo
 * @see ConveyanceAnnex
 * @see Attachment
 *
 */
@Entity
@Table(name="martinlaw_convey_att_t")
public class ConveyanceAttachment extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 661753612067654890L;
	@Id
	@Column(name="convey_att_id")
	private Long id;
	// to help ojb associate attachment
	@Column(name="att_id", nullable=false)
	private Long attachmentId;
	@Column(name="convey_annex_id", nullable=false)
	private Long conveyanceAnnexId;
	// annotation fails with: attachment references an unknown entity: org.kuali.rice.krad.bo.Attachment
	/*@OneToOne
	@JoinColumn(name="att_id", nullable=false, updatable=false)*/
	private Attachment attachment;
	/**
	 * gets the primary key
	 * 
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
	 * gets the foreign key of the attachment
	 * 
	 * it happens that the note id is used as the primary key of the attachment object
	 * 
	 * @return the attachmentId
	 */
	public Long getAttachmentId() {
		return attachmentId;
	}
	/**
	 * @param attachmentId the attachmentId to set
	 */
	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}
	/**
	 * gets the foreign key of the conveyance annex in which this attachment is listed in
	 * 
	 * @return the conveyanceAnnexId
	 */
	public Long getConveyanceAnnexId() {
		return conveyanceAnnexId;
	}
	/**
	 * @param conveyanceAnnexId the conveyanceAnnexId to set
	 */
	public void setConveyanceAnnexId(Long conveyanceAnnexId) {
		this.conveyanceAnnexId = conveyanceAnnexId;
	}
	/**
	 * the rice attachment referred to by {@link #attachmentId}
	 * 
	 * @return the attachment
	 */
	public Attachment getAttachment() {
		return attachment;
	}
	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
}
