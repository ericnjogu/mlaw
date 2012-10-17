/**
 * 
 */
package org.martinlaw.bo.opinion;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterWork;

/**
 * a transactional document whose notes/attachments have work done about a {@link Opinion}
 * 
 * @author mugo
 *
 */
@Entity(name="opinion_work")
@Table(name="martinlaw_opinion_work_doc_t")
public class Work extends MatterWork {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3001410196564723211L;

	/**
	 * default constructor to initialize matter class
	 * 
	 * adapted from {@link http://stackoverflow.com/questions/182636/how-to-determine-the-class-of-a-generic-type}
	 */
	public Work() {
		super();
		setMatterClass(Opinion.class);
	}

}
