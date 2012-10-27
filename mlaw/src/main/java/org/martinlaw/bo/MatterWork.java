/**
 * 
 */
package org.martinlaw.bo;

import javax.persistence.MappedSuperclass;


/**
 * provides a way for an assignee to submit work for a matter
 * 
 * @author mugo
 *
 */
@MappedSuperclass
public abstract class MatterWork extends MatterTxDocBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3637053012196079706L;
}
