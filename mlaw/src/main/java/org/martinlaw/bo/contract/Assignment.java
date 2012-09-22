/**
 * 
 */
package org.martinlaw.bo.contract;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterAssignment;


/**
 * associates a contract to the people/person assigned to work on it
 * 
 * @author mugo
 *
 */
@Entity
@Table(name="martinlaw_contract_assignment_t")
public class Assignment extends MatterAssignment<Contract, Assignee> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1303934008600315215L;
}
