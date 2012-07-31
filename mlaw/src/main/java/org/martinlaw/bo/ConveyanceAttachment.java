/**
 * 
 */
package org.martinlaw.bo;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.rice.krad.bo.Attachment;
import org.kuali.rice.krad.bo.Note;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krad.service.KRADServiceLocator;

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
	@Transient
	Log log = LogFactory.getLog(getClass());
	private static final long serialVersionUID = 661753612067654890L;
	@Id
	@Column(name="convey_att_id")
	private Long id;
	// to help ojb associate attachment
	@Column(name="note_timestamp", nullable=false)
	private Timestamp noteTimestamp;
	@Column(name="convey_annex_id", nullable=false)
	private Long conveyanceAnnexId;
	@Transient
	private Attachment attachment;
	@Transient
	private String filename;
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
	 * gets the {@link Note#getNotePostedTimestamp()}} of the note which contains the attachment associated here
	 * 
	 * @return the noteTimestamp
	 */
	public Timestamp getNoteTimestamp() {
		return noteTimestamp;
	}
	/**
	 * @param noteTimestamp the noteTimestamp to set
	 */
	public void setNoteTimestamp(Timestamp attachmentId) {
		this.noteTimestamp = attachmentId;
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
	 * the rice attachment referred to by {@link #noteTimestamp}
	 * 
	 * only works properly if the conveyance, conveyance annex and this object have been saved - so is meant to be used in the inquiry page
	 * 
	 * @return the attachment, null if not found
	 */
	public Attachment getAttachment() {
		if (attachment == null && getConveyanceAnnexId() != null) {
			// get the conveyance annex
			ConveyanceAnnex convAnnex = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
					ConveyanceAnnex.class, getConveyanceAnnexId());
			if (convAnnex == null) {
				log.error("conveyance annex with id '" + getConveyanceAnnexId() + "' does not exist");
			} else {
				Conveyance conv = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(
						Conveyance.class, convAnnex.getConveyanceId());
				// retrieve the note that was saved for the conveyance
				Map<String, Object> criteria = new HashMap<String, Object>(2);
				criteria.put("notePostedTimestamp", getNoteTimestamp());
				criteria.put("remoteObjectIdentifier", conv.getObjectId());
				
				Collection<Note> notes = KRADServiceLocator.getBusinessObjectService().findMatching(
						Note.class, criteria);
				// there should just be one note matching
				if (notes.size() == 1) {
					for (Note n: notes) {
						attachment = n.getAttachment();
					}
				} else {
					log.error("multiple notes matching remote(conveyance) object id '" +
							conv.getObjectId() + "' and timestamp " + getNoteTimestamp());
				}
			}
		}
		return attachment;
	}
	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
}
