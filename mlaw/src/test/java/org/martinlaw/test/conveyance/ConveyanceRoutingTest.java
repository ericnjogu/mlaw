/**
 * 
 */
package org.martinlaw.test.conveyance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.test.KewTestsBase;
import org.martinlaw.util.SearchTestCriteria;

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
			testMaintenanceRoutingInitToFinal("ConveyanceMaintenanceDocument", conv);
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
			assertNotNull("status should not be null", conveyance.getStatus());
			assertNotNull("status id should not be null", conveyance.getStatusId());
		}
	}
	
	@Test
	/**
	 * test conveyance document searching
	 */
	public void testConveyanceDocSearch() throws WorkflowException, InstantiationException, IllegalAccessException {
		Conveyance conv = getTestUtils().getTestConveyance();
		//conv.getConsiderations().add(new Consideration(new BigDecimal(1000), "EBS", null));
		final String docType = "ConveyanceMaintenanceDocument";
		testMaintenanceRoutingInitToFinal(docType, conv);
		
		Conveyance conv2 = getTestUtils().getTestConveyance();
		conv2.setLocalReference("my/firm/conv/15");
		conv2.setName("sale of plot number 123");
		//conv.getConsiderations().add(new Consideration(new BigDecimal(1001), "TFX", null));
		testMaintenanceRoutingInitToFinal(docType, conv2);
	
		// no document criteria given, so both documents should be found
		SearchTestCriteria crit1 = new SearchTestCriteria();
		crit1.setExpectedDocuments(2);
		// search for local reference
		SearchTestCriteria crit2 = new SearchTestCriteria();
		crit2.setExpectedDocuments(1);
		crit2.getFieldNamesToSearchValues().put("localReference", conv.getLocalReference());
		// search for name
		SearchTestCriteria crit3 = new SearchTestCriteria();
		crit3.setExpectedDocuments(1);
		crit3.getFieldNamesToSearchValues().put("name", "*plot*");
		
		List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
		crits.add(crit1);
		crits.add(crit2);
		crits.add(crit3);
		getTestUtils().runDocumentSearch(crits, docType);
	}
}
