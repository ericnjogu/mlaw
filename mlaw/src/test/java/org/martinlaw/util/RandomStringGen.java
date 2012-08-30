/**
 * 
 */
package org.martinlaw.util;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * generate some random strings
 * 
 * @author mugo
 *
 */
public class RandomStringGen {
	static Log log = LogFactory.getLog(RandomStringGen.class);
	
	public static void main(String[] args) {
		for (int i=0; i<10; i++) {
			log.info(RandomStringUtils.random(36, true, true));
		}
	}
}
