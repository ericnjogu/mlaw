package org.martinlaw.bo;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012, 2013 Eric Njogu (kunadawa@gmail.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.bo.Attachment;
import org.kuali.rice.krad.bo.Note;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.utils.PersonUtils;
/**
 * a super class that holds the information common to court case, conveyance, contract etc
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_matter_t")
@Inheritance(strategy=InheritanceType.JOINED)
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
	@Column(name = "local_reference", length = 50, nullable = false)
	private String localReference;
	@Column(name = "status_id")
	private Long statusId;
	/** 
	 * case e.g. Mike Vs Iron (2002) 
	 */
	@Column(name = "name", length = 1000)
	private String name;
	@OneToOne
	@JoinColumn(name = "status_id", nullable = false, updatable = false, insertable=false)
	private Type status;
	@Column(name = "client_principal_name", length = 100, nullable = false)
	private String clientPrincipalName;
	/** the client**/
	@Transient
	private Person client;
	/**cache the dynamically fetched attachments locally*/
	@Transient
	private List<Attachment> attachments = null;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "matterId")
	private List<MatterAssignee> assignees;
	// in Work the pk is the document id, not matterId, so we cannot configure a many-one mapping - not
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="matterId")
	private List<MatterWork> work;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="matterId")
	private List<MatterClient> clients;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="matterId")
	private List<MatterEvent> events;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="matterId")
	private List<MatterConsideration> considerations;
	@Transient
	private PersonUtils personUtils;
	@Transient
	private transient String eventsHtml = "";
	@Transient
	private transient String considerationsHtml = "";
	@Column(name = "class_name", length = 150)
	private String concreteClass;
	@Column(name = "tags", length = 1000)
	private String tags;
	@Column(name = "type_id", nullable=false)
	private Long typeId;
	@OneToOne
	@JoinColumn(name = "type_id", nullable = false, updatable = false, insertable=false)
	private MatterType type;
	
	/**
	 * default constructor
	 */
	public Matter() {
		super();
		setEvents(new ArrayList<MatterEvent>());
		setClients(new ArrayList<MatterClient>());
		try {
			setConsiderations(createDefaultConsiderations(MatterConsideration.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * create default considerations - legal fees and disbursement
	 * @return the considerations
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ArrayList<MatterConsideration> createDefaultConsiderations(Class<MatterConsideration> k) throws InstantiationException, IllegalAccessException {
		ArrayList<MatterConsideration> defaultConsiderations = new ArrayList<MatterConsideration>(2);
		MatterConsideration legalFee = k.newInstance();
		legalFee.setConsiderationTypeId(MartinlawConstants.DefaultConsideration.LEGAL_FEE_TYPE_ID);
		legalFee.setDescription(MartinlawConstants.DefaultConsideration.LEGAL_FEE_DESCRIPTION);
		legalFee.setAmount(new BigDecimal(0));
		legalFee.setCurrency(MartinlawConstants.DefaultConsideration.CURRENCY);
		defaultConsiderations.add(legalFee);
		
		MatterConsideration disbursement = k.newInstance();
		disbursement.setConsiderationTypeId(MartinlawConstants.DefaultConsideration.DISBURSEMENT_TYPE_ID);
		disbursement.setDescription(MartinlawConstants.DefaultConsideration.DISBURSEMENT_DESCRIPTION);
		disbursement.setAmount(new BigDecimal(0));
		disbursement.setCurrency(MartinlawConstants.DefaultConsideration.CURRENCY);
		defaultConsiderations.add(disbursement);
		return defaultConsiderations;
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
	public Type getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Type status) {
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
	public List<MatterAssignee> getAssignees() {
		return assignees;
	}

	/**
	 * @param assignees the assignees to set
	 */
	public void setAssignees(List<MatterAssignee> assignees) {
		this.assignees = assignees;
	}

	/**
	 * fetch a list of work documents whose matter id matches this objects matter id
	 * 
	 * @return the work
	 */
	public List<MatterWork> getWork() {
		return work;
	}

	/**
	 * retrieves the class type of the parametrized work, which is used in populating {@link #getWork()}
	 * 
	 * @return the work class type
	 *//*
	public abstract  Class<W> getWorkClass();*/
	
	/**
	 * retrieves the class type of the parametrized work, which is used in populating {@link #getFees()}
	 * 
	 * @return the work class type
	 *//*
	public abstract  Class<F> getFeeClass();*/


	/**
	 * @param work the work to set
	 */
	public void setWork(List<MatterWork> work) {
		this.work = work;
	}

	/**
	 * @return the clients
	 */
	public List<MatterClient> getClients() {
		return clients;
	}

	/**
	 * @param clients the clients to set
	 */
	public void setClients(List<MatterClient> clients) {
		this.clients = clients;
	}
	
	/**
	 * @param events the events to set
	 */
	public void setEvents(List<MatterEvent> events) {
		this.events = events;
	}
	/**
	 * @return the events
	 */
	public List<MatterEvent> getEvents() {
		return events;
	}

	/**
	 * @return the considerations
	 */
	public List<MatterConsideration> getConsiderations() {
		return considerations;
	}

	/**
	 * @param considerations the considerations to set
	 */
	public void setConsiderations(List<MatterConsideration> considerations) {
		this.considerations = considerations;
	}

	/**
	 * @return the clientPrincipalName
	 */
	public String getClientPrincipalName() {
		return clientPrincipalName;
	}

	/**
	 * @param clientPrincipalName the clientPrincipalName to set
	 */
	public void setClientPrincipalName(String clientPrincipalName) {
		this.clientPrincipalName = clientPrincipalName;
	}

	/**
	 * @return the client
	 */
	public Person getClient() {
		if (personUtils == null) {
			personUtils = new PersonUtils();
		}
		client = personUtils.getPerson(client, getClientPrincipalName());
		
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(Person client) {
		this.client = client;
	}
	
	/**
	 * events as html for display in the lookup results as a message field
	 * @return the eventsHtml
	 */
	public String getEventsHtml() {
		if (StringUtils.isEmpty(eventsHtml) && !getEvents().isEmpty()) {
			StringBuilder sb = new StringBuilder();
			int counter = 0;
			for (MatterEvent event: getEvents()) {
				appendHtmlDivStart(sb, counter);
				sb.append(event.toHtml());
				sb.append("</div>");
				/*if (counter + 1 != getEvents().size()) {
					sb.append("<br/>");
				}*/
				counter +=1;
			}
			eventsHtml = sb.toString();
		}
		
		return eventsHtml;
	}

	/**
	 * @param sb - a string builder to append a div start tag
	 * @param counter - counts the number of div elements to determine the background color style
	 */
	protected void appendHtmlDivStart(StringBuilder sb, int counter) {
		sb.append("<div class=\"");
		if (counter % 2 == 0) {
			sb.append(MartinlawConstants.Styles.EVEN + "\">");
		} else {
			sb.append(MartinlawConstants.Styles.ODD + "\">");
		}
	}

	/**
	 * @param eventsHtml the eventsHtml to set
	 */
	public void setEventsHtml(String eventsHtml) {
		this.eventsHtml = eventsHtml;
	}

	/**
	 * @return the considerationsHtml
	 */
	public String getConsiderationsHtml() {
		if (StringUtils.isEmpty(considerationsHtml) && !getConsiderations().isEmpty()) {
			StringBuilder sb = new StringBuilder();
			int counter = 0;
			for (MatterConsideration csdn: getConsiderations()) {
				String html = csdn.toHtml();
				if (!StringUtils.isEmpty(html)) {
					appendHtmlDivStart(sb, counter);
					sb.append(html);
					sb.append("</div>");
					counter+=1;
				}
			}
			considerationsHtml = sb.toString();
		}
		return considerationsHtml;
	}

	/**
	 * @param considerationsHtml the considerationsHtml to set
	 */
	public void setConsiderationsHtml(String considerationsHtml) {
		this.considerationsHtml = considerationsHtml;
	}

	/**
	 * @return the concreteClass
	 */
	public String getConcreteClass() {
		return concreteClass;
	}

	/**
	 * @param concreteClass the concreteClass to set
	 */
	public void setConcreteClass(String concreteClass) {
		this.concreteClass = concreteClass;
	}

	@Override
	protected void prePersist() {
		setConcreteClass(this.getClass().getCanonicalName());
		super.prePersist();
	}

	/**
	 * any number of phrases, words, acronyms, abbreviations, category etc that cannot fit into other existing fields
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the typeId
	 */
	public Long getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the courtCaseType
	 */
	public MatterType getType() {
		return type;
	}

	/**
	 * @param matterType the courtCaseType to set
	 */
	public void setType(MatterType matterType) {
		this.type = matterType;
	}
}