/**
 * 
 */
package org.martinlaw.bo.courtcase;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterAssignment;


/**
 * associates a {@link CourtCase} to the people/person assigned to work on it
 * 
 * @author mugo
 *
 */
@Entity(name="court_case_assignment")
@Table(name="martinlaw_court_case_assignment_t")
public class Assignment extends MatterAssignment<CourtCase, Assignee> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8833165378416618381L;


}
