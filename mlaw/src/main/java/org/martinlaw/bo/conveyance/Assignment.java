/**
 * 
 */
package org.martinlaw.bo.conveyance;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterAssignment;


/**
 * associates a Conveyance to the people/person assigned to work on it
 * 
 * @author mugo
 *
 */
@Entity(name="conv_assignment")
@Table(name="martinlaw_convey_assignment_t")
public class Assignment extends MatterAssignment<Conveyance, Assignee> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1303934008600315215L;
}
