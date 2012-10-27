/**
 * 
 */
package org.martinlaw.bo.contract;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterClient;


/**
 * associates a {@link Contract} to a kuali person
 * 
 * @author mugo
 * 
 */
@Entity(name="contract_client")
@Table(name="martinlaw_contract_client_t")
public class Client extends MatterClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3445357337237384485L;


}
