/**
 * 
 */
package org.martinlaw.bo.conveyance;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterAssignee;


/**
 * specifies a person who has been assigned to work on a conveyance
 * 
 * @author mugo
 *
 */
@Entity(name="conveyance_assignee")
@Table(name="martinlaw_convey_assignee_t")
public class Assignee extends MatterAssignee {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4754744085287455243L;

}
