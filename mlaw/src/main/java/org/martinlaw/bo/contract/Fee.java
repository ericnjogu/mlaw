/**
 * 
 */
package org.martinlaw.bo.contract;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterFee;


/**
 * Fee represents a fee paid to a lawyer by a client
 * @author mugo
 */
@Entity(name="contract_fee")
@Table(name="martinlaw_contract_fee_t")
public class Fee extends MatterFee {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8546782407151235045L;

}
