/**
 * 
 */
package org.martinlaw.bo.contract;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterAssignee;


/**
 * specifies a person who has been assigned to work on a contract
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_contract_assignee_t")
public class Assignee extends MatterAssignee {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5336580846702092284L;

}
