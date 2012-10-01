/**
 * 
 */
package org.martinlaw.bo.opinion;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.martinlaw.bo.MatterClient;

/**
 * associates an {@link Opinion} to a kuali person
 * 
 * @author mugo
 * 
 */
@Entity
@Table(name="martinlaw_opinion_client_t")
public class OpinionClient extends MatterClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5102497856920913168L;
	

}
