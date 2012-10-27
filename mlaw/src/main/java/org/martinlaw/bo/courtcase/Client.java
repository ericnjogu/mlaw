/**
 * 
 */
package org.martinlaw.bo.courtcase;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterClient;

/**
 * links a {@link CourtCase} to a kuali person
 * 
 * @author mugo
 * 
 */
@Entity(name="court_case_client")
@Table(name="martinlaw_court_case_client_t")
public class Client extends MatterClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4030857472082772464L;
	
}
