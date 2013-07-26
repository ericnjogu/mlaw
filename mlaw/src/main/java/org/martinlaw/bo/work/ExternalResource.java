/**
 * 
 */
package org.martinlaw.bo.work;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

/**
 * a way of representing files, folders etc that are kept outside the system
 * 
 * <p>These resources will be introduced via a work document, whose foreign key is {@link #workDocumentNumber}.
 * This can be an additional security/confidentiality measure, help in accessing shared resources, save bandwidth and storage space for
 * readily available resources</p>
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_external_resource_t")
public class ExternalResource extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4429287849765929218L;
	@Id
	@Column(name = "ext_res_id")
	private Long id;
	@Column(name = "DOC_HDR_ID")
	private String workDocumentNumber;
	@Column(length=1000)
	private String url;
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
	 * @return the workDocumentNumber
	 */
	public String getWorkDocumentNumber() {
		return workDocumentNumber;
	}
	/**
	 * @param workDocumentNumber the workDocumentNumber to set
	 */
	public void setWorkDocumentNumber(String workDocumentNumber) {
		this.workDocumentNumber = workDocumentNumber;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
