package org.martinlaw.bo;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.bo.Attachment;
import org.kuali.rice.krad.bo.Note;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krad.service.KRADServiceLocator;
/**
 * a super class that holds the information common to court case, conveyance and contract
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public class Matter extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3504756475357807641L;
	@Id
	@Column(name="matter_id")
	private Long id;
	/**law firms ref e.g. NN/N201/MN
	 * @ojb.field  column="local_reference"
	 */
	@Column(name = "local_reference", length = 20, nullable = false)
	private String localReference;
	//column defined using reference below - this is for the sake of ojb
	@Transient
	private Long statusId;
	/** 
	 * case e.g. Mike Vs Iron (2002) 
	 */
	@Column(name = "name", length = 100)
	private String name;
	@OneToOne
	@JoinColumn(name = "status_id", nullable = false, updatable = false)
	private Status status;
	/**cache the dynamically fetched attachments locally*/
	@Transient
	private List<Attachment> attachments = null;
	public Matter() {
		super();
	}

	/**
	 * @return the localReference
	 */
	public String getLocalReference() {
		return localReference;
	}

	/**
	 * @param localReference the localReference to set
	 */
	public void setLocalReference(String localReference) {
		this.localReference = localReference;
	}

	/**
	 * @return the statusId
	 */
	public Long getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * gets the list of attachments associated with this matter
	 * 
	 * <p>This is achieved by retrieving the notes whose remote obj id is equal to this object's obj id
	 * and retrieving the attachments if present</p>
	 * 
	 * @return the list of attachments if found, an empty list if not
	 */
	public List<Attachment> getAttachments() {
		if (attachments == null) {
			List<Attachment> atts = new ArrayList<Attachment>();
			if (!StringUtils.isEmpty(getObjectId())) {
				List<Note> notes = KRADServiceLocator.getNoteService().getByRemoteObjectId(getObjectId());
				if (notes != null) {
					for (Note note: notes) {
						// only interested in file attachments
						if (note.getAttachment() != null) {
							atts.add(note.getAttachment());
						}
					}
					attachments = atts;
				}
			}
		}
		return attachments;
	}
	
	/**
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

}