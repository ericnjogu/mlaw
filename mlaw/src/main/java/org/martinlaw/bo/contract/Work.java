/**
 * 
 */
package org.martinlaw.bo.contract;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterWork;

/**
 * a transactional document whose notes/attachments have work done about a {@link Contract}
 * 
 * @author mugo
 *
 */
@Entity(name="contract_work")
@Table(name="martinlaw_contract_work_doc_t")
public class Work extends MatterWork {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3316067275669086689L;

	/**
	 * default constructor to initialize matter class
	 * 
	 * adapted from {@link http://stackoverflow.com/questions/182636/how-to-determine-the-class-of-a-generic-type}
	 */
	public Work() {
		super();
		setMatterClass(Contract.class);
	}

}
