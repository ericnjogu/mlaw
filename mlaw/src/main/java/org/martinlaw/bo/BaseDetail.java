/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * a super class that holds some common fields
 * 
 * @author mugo
 */
@MappedSuperclass
public class BaseDetail extends PersistableBusinessObjectBase{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8844219522196909942L;
	@Column(name="name", length=100, nullable=false) 
	private String name;
	@Column(name = "description", length = 100)
	private String description;
	/**
	 * can be null if name is descriptive enough
	 * 
	 * @return e.g. 'The lands board minutes where the sale of the said land was approved'
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return e.g. 'Lands board approval'
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
