/**
 * 
 */
package org.martinlaw.test;

import org.martinlaw.bo.Conveyance;

/**
 * holds various methods used across test cases
 * 
 * @author mugo
 *
 */
public class TestUtils {
	/**
	 * get a test conveyance object
	 * @return
	 */
	public static Conveyance getTestConveyance() {
		Conveyance conv = new Conveyance();
		String name = "sale of KAZ 457T";
		conv.setName(name);
		conv.setLocalReference("EN/C001");
		conv.setTypeId(1002l);
		conv.setStatusId(1001l);
		return conv;
	}
}
