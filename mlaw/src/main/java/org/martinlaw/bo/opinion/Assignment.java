/**
 * 
 */
package org.martinlaw.bo.opinion;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterAssignment;


/**
 * associates a {@link Opinion} to the people/person assigned to work on it
 * 
 * @author mugo
 *
 */
@Entity(name="opinion_assignment")
@Table(name="martinlaw_opinion_assignment_t")
public class Assignment extends MatterAssignment<Opinion, Assignee> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6650416004040252654L;

}
