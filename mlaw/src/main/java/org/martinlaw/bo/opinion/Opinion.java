/**
 * 
 */
package org.martinlaw.bo.opinion;

import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Opinion extends Matter<Assignee, Work, ClientFee, Client> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4075690470255555315L;
	/**
	 * 
	 */
	public Opinion() {
		super();
		setClients(new ArrayList<Client>()); 
	}
	@Column(name="summary")
	private String summary;
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

	@Override
	public Class<Work> getWorkClass() {
		return Work.class;
	}
	@Override
	public Class<ClientFee> getFeeClass() {
		return ClientFee.class;
	}
}
