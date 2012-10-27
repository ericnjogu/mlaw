package org.martinlaw.bo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.bo.Attachment;
import org.kuali.rice.krad.bo.Note;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.Constants;
/**
 * a super class that holds the information common to court case, conveyance, contract etc
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class Matter<A extends MatterAssignee, W extends MatterTxDocBase, F extends MatterClientFee<? extends MatterFee>, C extends MatterClient> extends PersistableBusinessObjectBase {

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
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "matterId")
	private List<A> assignees;
	// in Work the pk is the document id, not matterId, so we cannot configure a many-one mapping
	@Transient
	private List<W> work;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="matterId")
	private List<F> fees;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="matterId")
	private List<C> clients;
	
	/**
	 * default constructor
	 */
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

	/**
	 * @return the assignees
	 */
	public List<A> getAssignees() {
		return assignees;
	}

	/**
	 * @param assignees the assignees to set
	 */
	public void setAssignees(List<A> assignees) {
		this.assignees = assignees;
	}

	/**
	 * fetch a list of work documents whose matter id matches this objects matter id
	 * 
	 * @return the work
	 */
	public List<W> getWork() {
		// TODO using the caching mechanism for this so that we can handle the creation of new work items
		if (work == null) {
			Map<String, Object> criteria = new HashMap<String, Object>(1);
			criteria.put(Constants.PropertyNames.MATTER_ID, getId());
			work = (List<W>) KRADServiceLocator.getBusinessObjectService().findMatching(getWorkClass(), criteria);
			return work;
		}
		return work;
	}

	/**
	 * retrieves the class type of the parametrized work, which is used in populating {@link #getWork()}
	 * 
	 * @return the work class type
	 */
	public abstract  Class<W> getWorkClass();
	
	/**
	 * retrieves the class type of the parametrized work, which is used in populating {@link #getFees()}
	 * 
	 * @return the work class type
	 */
	public abstract  Class<F> getFeeClass();


	/**
	 * @param work the work to set
	 */
	public void setWork(List<W> work) {
		this.work = work;
	}

	/**
	 * @return the fees
	 */
	public List<F> getFees() {
		// TODO using the caching mechanism for this so that we can handle the creation of new work items
		if (fees == null) {
			Map<String, Object> criteria = new HashMap<String, Object>(1);
			criteria.put(Constants.PropertyNames.MATTER_ID, getId());
			fees = (List<F>) KRADServiceLocator.getBusinessObjectService().findMatching(getFeeClass(), criteria);
		}
		return fees;
	}

	/**
	 * @param fees the fees to set
	 */
	public void setFees(List<F> fees) {
		this.fees = fees;
	}

	/**
	 * @return the clients
	 */
	public List<C> getClients() {
		return clients;
	}

	/**
	 * @param clients the clients to set
	 */
	public void setClients(List<C> clients) {
		this.clients = clients;
	}
}