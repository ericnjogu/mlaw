/**
 * 
 */
package org.martinlaw.bo.contract;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.Matter;
import org.martinlaw.bo.MatterAssignee;
import org.martinlaw.bo.MatterClient;
import org.martinlaw.bo.MatterClientFee;
import org.martinlaw.bo.MatterFee;
import org.martinlaw.bo.MatterWork;

/**
 * tx document that holds information of a payment made by a client for contract
 *  
 * @author mugo
 *
 */
@Entity(name="contract_client_fee")
@Table(name="martinlaw_contract_fee_doc_t")
public class ClientFee extends MatterClientFee<Fee> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8397901980615462593L;

	/* (non-Javadoc)
	 * @see org.martinlaw.bo.MatterTxDocBase#getMatterClass()
	 */
	@Override
	public Class<? extends Matter<? extends MatterAssignee, ? extends MatterWork, ? extends MatterClientFee<? extends MatterFee>, ? extends MatterClient>> getMatterClass() {
		return Contract.class;
	}

}
