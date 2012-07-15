/**
 * 
 */
package org.martinlaw.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * holds information that keeps  track of a conveyancing matter
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_convey_t")
public class Conveyance extends Matter {
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="conveyanceId")
	private List<ConveyanceFee> fees;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="conveyanceId")
	private List<ConveyanceClient> clients;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="conveyanceId")
	private List<ConveyanceAnnex> annexes;
	@Id
	@Column(name="conveyance_id")
	private Long id;
	private Long typeId;
	/**
	 * default constructor which initializes lists
	 */
	public Conveyance() {
		fees = new ArrayList<ConveyanceFee>();
		clients = new ArrayList<ConveyanceClient>();
		annexes = new ArrayList<ConveyanceAnnex>();
	}
	
	@OneToOne
	@JoinColumn(name="convey_type_id", nullable=false, updatable=false)
	private ConveyanceType type;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7207626236630736596L;
	/**
	 * @return the fees
	 */
	public List<ConveyanceFee> getFees() {
		return fees;
	}
	/**
	 * @param fees the fees to set
	 */
	public void setFees(List<ConveyanceFee> fees) {
		this.fees = fees;
	}
	/**
	 * @return the clients
	 */
	public List<ConveyanceClient> getClients() {
		return clients;
	}
	/**
	 * @param clients the clients to set
	 */
	public void setClients(List<ConveyanceClient> clients) {
		this.clients = clients;
	}
	/**
	 * @return the annexes
	 */
	public List<ConveyanceAnnex> getAnnexes() {
		return annexes;
	}
	/**
	 * @param annexes the annexes to set
	 */
	public void setAnnexes(List<ConveyanceAnnex> annexes) {
		this.annexes = annexes;
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
	 * @return the typeId
	 */
	public Long getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Long conveyanceTypeId) {
		this.typeId = conveyanceTypeId;
	}
	/**
	 * @return the type
	 */
	public ConveyanceType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(ConveyanceType conveyanceType) {
		this.type = conveyanceType;
	}
}
