/**
 * 
 */
package org.martinlaw.bo;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author mugo
 */
@Entity
@Table(name="martinlaw_annex_type_t")
public class AnnexType extends org.kuali.rice.krad.bo.PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4804506622892463389L;
	/**
	 * @ojb.field column= primarykey="true" autoincrement="ojb" sequence-name="martinlaw_annex_type_id_s"
	 */
	@Id
	@Column(name="annex_type_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="annex_type_gen")
	@TableGenerator(name="annex_type_gen", initialValue=1000, table="martinlaw_annex_type_id_s", valueColumnName="id")
	private Long id;
	/**
	 * @ojb.field column="text" length="50"
	 */
	@Column(name="text", length=50, nullable=false)
	private String value;
	/**
	 * @param id - the primary key
	 * @param value - the annex type e.g. '
	 */
	public AnnexType(Long id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	/**
	 * 
	 */
	public AnnexType() {
		super();
	}
	/* (non-Javadoc)
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	// @Override
	protected LinkedHashMap<String, Object> toStringMapper() {
		LinkedHashMap<String, Object> props = new LinkedHashMap<String, Object>();
		props.put("id", getId());
		props.put("value", getValue());
		return props;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
