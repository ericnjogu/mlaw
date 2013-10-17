/**
 * 
 */
package org.martinlaw.test;

import org.martinlaw.bo.MatterWork;

/**
 *  created since two transactional docs cannot share the same doc type
 *  yet we wanted to test matter work without the search attribute, esp so that {@link #testWorkFlowDocument()} can work
 * @author mugo
 *
 */
public class MatterWorkDummy extends MatterWork {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3240108807309750614L;

}
