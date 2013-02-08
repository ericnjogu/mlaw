/**
 * 
 */
package org.martinlaw.test.conveyance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.martinlaw.bo.conveyance.ConveyanceAnnexType;
import org.martinlaw.bo.conveyance.ConveyanceType;
import org.martinlaw.test.KewTestsBase;

/**
 * tests {@link ConveyanceType} routing
 * @author mugo
 *
 */
public class ConveyanceTypeRoutingTest extends KewTestsBase {
	private Logger log = Logger.getLogger(getClass());
	@Test
	/**
	 * test that ConveyanceTypeDocument is routed to lawyer on submit
	 */
	public void testConveyanceTypeRouting() {
		ConveyanceType convType = new ConveyanceType();
		String name = "auction";
		convType.setName(name);
		// set annex types
		List<ConveyanceAnnexType> annexTypes = new ArrayList<ConveyanceAnnexType>();
		ConveyanceAnnexType convAnnexType = new ConveyanceAnnexType();
		convAnnexType.setName("signed affidavit");
		annexTypes.add(convAnnexType);
		convAnnexType = new ConveyanceAnnexType();
		convAnnexType.setName("title deed");
		annexTypes.add(convAnnexType);
		convType.setAnnexTypes(annexTypes);
		// get number of annex types before adding new
		int existingAnnexTypes = getBoSvc().findAll(ConveyanceAnnexType.class).size();
		try {
			testMaintenanceRouting("ConveyanceTypeMaintenanceDocument", convType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("test failed", e);
			fail("testConveyanceTypeRouting caused an exception");
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		Collection<ConveyanceType> result = getBoSvc().findMatching(ConveyanceType.class, params);
		assertEquals(1, result.size());
		assertEquals(2 + existingAnnexTypes, getBoSvc().findAll(ConveyanceAnnexType.class).size());
	}
}
