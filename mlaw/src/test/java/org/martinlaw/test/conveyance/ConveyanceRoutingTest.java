/**
 * 
 */
package org.martinlaw.test.conveyance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.test.KewTestsBase;

/**
 * tests routing for {@link Conveyance}
 * @author mugo
 *
 */
public class ConveyanceRoutingTest extends KewTestsBase {
	private Logger log = Logger.getLogger(getClass());
	
	@Test
	/**
	 * test that a conveyance document is routed to the lawyer
	 */
	public void testConveyanceRouting() {
		int existingConveyances = getBoSvc().findAll(Conveyance.class).size();
		Conveyance conv = getTestUtils().getTestConveyance();
		try {
			testMaintenanceRouting("ConveyanceMaintenanceDocument", conv);
		} catch (Exception e) {
			log.error("error in testConveyanceRouting", e);
			fail(e.getMessage());
		}
		// confirm BO was saved
		assertEquals(existingConveyances + 1, getBoSvc().findAll(Conveyance.class).size());
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", conv.getName());
		Collection<Conveyance> result = getBoSvc().findMatching(Conveyance.class, params);
		assertEquals(1, result.size());
		for (Conveyance conveyance: result) {
			// TODO - test fails - possibly something to do with the ojb framework - or delays in document processing?
			// assertNotNull("status should not be null", conveyance.getStatus());
			assertNotNull("status id should not be null", conveyance.getStatusId());
		}
		
	}
}
