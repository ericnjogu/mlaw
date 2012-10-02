/**
 * 
 */
package org.martinlaw.bo.opinion;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterAssignee;


/**
 * specifies a person who has been assigned to work on a {@link Opinion}}
 * 
 * @author mugo
 *
 */
@Entity(name="opinion_assignee")
@Table(name="martinlaw_opinion_assignee_t")
public class Assignee extends MatterAssignee {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2628395293188615922L;
}
