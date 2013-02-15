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
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.martinlaw.bo.conveyance.ConveyanceAnnexType;
import org.martinlaw.bo.conveyance.ConveyanceType;
import org.martinlaw.test.KewTestsBase;
import org.martinlaw.util.SearchTestCriteria;

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
		ConveyanceType convType = getTestUtils().getTestConveyanceType();
		// get number of annex types before adding new
		int existingAnnexTypes = getBoSvc().findAll(ConveyanceAnnexType.class).size();
		try {
			testMaintenanceRoutingInitToFinal("ConveyanceTypeMaintenanceDocument", convType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("test failed", e);
			fail("testConveyanceTypeRouting caused an exception - " + e);
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", convType.getName());
		Collection<ConveyanceType> result = getBoSvc().findMatching(ConveyanceType.class, params);
		assertEquals(1, result.size());
		assertEquals(2 + existingAnnexTypes, getBoSvc().findAll(ConveyanceAnnexType.class).size());
	}
	
	/**
	 * test that ConveyanceTypeDocument doc search works
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws WorkflowException 
	 */
	@Test
	public void testConveyanceTypeRoutingDocSearch() throws WorkflowException, InstantiationException, IllegalAccessException {
		ConveyanceType convType = getTestUtils().getTestConveyanceType();
		convType.setName("company incorporation");
		final String docType = "ConveyanceTypeMaintenanceDocument";
		testMaintenanceRoutingInitToFinal(docType, convType);
		
		ConveyanceType convType2 = getTestUtils().getTestConveyanceType();
		convType2.setName("sale of land");
		testMaintenanceRoutingInitToFinal(docType, convType2);
		
		// no document criteria given, so both documents should be found
		SearchTestCriteria crit1 = new SearchTestCriteria();
		crit1.setExpectedDocuments(2);
		// search for name
		SearchTestCriteria crit2 = new SearchTestCriteria();
		crit2.setExpectedDocuments(1);
		crit2.getFieldNamesToSearchValues().put("name", "sale*");
		// search for non-existent name
		SearchTestCriteria crit3 = new SearchTestCriteria();
		crit3.setExpectedDocuments(0);
		crit3.getFieldNamesToSearchValues().put("name", "*lease*");
		
		List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
		crits.add(crit1);
		crits.add(crit2);
		crits.add(crit3);
		runDocumentSearch(crits, docType);
	}
}
