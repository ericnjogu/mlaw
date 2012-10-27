/**
 * 
 */
package org.martinlaw.bo.courtcase;

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
@Entity(name="court_case_client_fee")
@Table(name="martinlaw_court_case_fee_doc_t")
public class ClientFee extends MatterClientFee<Fee> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6531077887209387473L;

	/* (non-Javadoc)
	 * @see org.martinlaw.bo.MatterTxDocBase#getMatterClass()
	 */
	@Override
	public Class<? extends Matter<? extends MatterAssignee, ? extends MatterWork, ? extends MatterClientFee<? extends MatterFee>, ? extends MatterClient>> getMatterClass() {
		return CourtCase.class;
	}
}
