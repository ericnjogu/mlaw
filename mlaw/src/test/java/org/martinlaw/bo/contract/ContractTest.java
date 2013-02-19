/**
 * 
 */
package org.martinlaw.bo.contract;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * tests {@link Contract}
 * @author mugo
 *
 */
public class ContractTest {

	@Test
	/**
	 * verifies that the collections are not null
	 */
	public void testCollections() {
		Contract contract = new Contract();
		assertNotNull("clients should not be null", contract.getClients());
		assertNotNull("parties should not be null", contract.getParties());
		assertNotNull("signatories should not be null", contract.getSignatories());
	}
}
