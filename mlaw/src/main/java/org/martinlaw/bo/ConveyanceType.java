/**
 * 
 */
package org.martinlaw.bo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * represents a type of conveyance e.g. lease agreement or sale of land
 * 
 * @author mugo
 */
@Entity
@Table(name="martinlaw_convey_type_t")
public class ConveyanceType extends BaseDetail {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7220248295960647672L;
	@Id
	@Column(name="convey_type_id")
	Long id;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},  mappedBy="conveyanceTypeId")
	private List<ConveyanceAnnexType> annexTypes;
	
	/**
	 * get the primary key
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
	 * get the annex types associated with this conveyance type
	 * 
	 * @return the annexTypes
	 */
	public List<ConveyanceAnnexType> getAnnexTypes() {
		return annexTypes;
	}
	/**
	 * @param annexTypes the annexTypes to set
	 */
	public void setAnnexTypes(List<ConveyanceAnnexType> annexTypes) {
		this.annexTypes = annexTypes;
	}
}
