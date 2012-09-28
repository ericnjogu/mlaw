/**
 * 
 */
package org.martinlaw.bo.courtcase;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterAssignee;


/**
 * specifies a person who has been assigned to work on a {@link CourtCase}}
 * 
 * @author mugo
 *
 */
@Entity(name="court_case_assignee")
@Table(name="martinlaw_court_case_assignee_t")
public class Assignee extends MatterAssignee {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5135134879201039451L;

}
