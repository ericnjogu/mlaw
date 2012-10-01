/**
 * 
 */
package org.martinlaw.bo.opinion;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterFee;


/**
 * Fee represents a fee paid to a lawyer by a client
 * @author mugo
 */
@Entity
@Table(name="martinlaw_opinion_fee_t")
public class OpinionFee extends MatterFee {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5139498341007335334L;
}
