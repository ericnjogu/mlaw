/**
 * 
 */
package org.martinlaw.bo.opinion;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.martinlaw.bo.Matter;

/**
 * holds information about a legal opinion
 * 
 * <p>Most of the information will be held as attachments in the maintenance doc or thru work documents by assignees</p>
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_opinion_t")
public class Opinion extends Matter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4075690470255555315L;
	/**
	 * 
	 */
	public Opinion() {
		super();
		fees = new ArrayList<OpinionFee>();
		clients = new ArrayList<OpinionClient>(); 
	}
	@Column(name="summary")
	private String summary;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="matterId")
	private List<OpinionClient> clients;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="matterId")
	private List<OpinionFee> fees;
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return the clients
	 */
	public List<OpinionClient> getClients() {
		return clients;
	}
	/**
	 * @param clients the clients to set
	 */
	public void setClients(List<OpinionClient> clients) {
		this.clients = clients;
	}
	/**
	 * @return the fees
	 */
	public List<OpinionFee> getFees() {
		return fees;
	}
	/**
	 * @param fees the fees to set
	 */
	public void setFees(List<OpinionFee> fees) {
		this.fees = fees;
	}
}
