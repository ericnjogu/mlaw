package org.martinlaw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.martinlaw.bo.BaseDetail;
import org.martinlaw.bo.contract.ContractType;
import org.martinlaw.util.SearchTestCriteria;

/**
 * holds the common routing tests for children of {@link BaseDetail}
 * @author mugo
 *
 */
public abstract class BaseDetailRoutingTestBase extends KewTestsBase implements TestBoInfo {

	private Log log = LogFactory.getLog(getClass());

	public BaseDetailRoutingTestBase() {
		super();
	}

	@Test
	public void testBaseDetailRouting() throws InstantiationException, IllegalAccessException {
		BaseDetail type = getDataObjectClass().newInstance();
		String name = "resale agreement";
		type.setName(name);
		try {
			testMaintenanceRoutingInitToFinal(getDocTypeName(), type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log .error("test failed", e);
			fail("test routing " + getDocTypeName() + " caused an exception");
		}
		// confirm that BO was saved to DB
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		Collection<? extends BaseDetail> result = getBoSvc().findMatching(getDataObjectClass(), params);
		assertEquals(1, result.size());
	}

	@Test
	public void testContractTypeMaintDocPerms() {
		testCreateMaintain(getDataObjectClass(), getDocTypeName());
	}

	/**
	 * test that ContractTypeDocument doc search works
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws WorkflowException 
	 */
	@Test
	public void testBaseDetailRoutingDocSearch() throws WorkflowException,
			InstantiationException, IllegalAccessException {
				BaseDetail type = new ContractType();
				type.setName("permanent for testing purposes");
				final String docType = getDocTypeName();
				testMaintenanceRoutingInitToFinal(docType, type);
				
				BaseDetail type2 = new ContractType();
				type2.setName("supply of rain and shine");
				testMaintenanceRoutingInitToFinal(docType, type2);
				
				// no document criteria given, so both documents should be found
				SearchTestCriteria crit1 = new SearchTestCriteria();
				crit1.setExpectedDocuments(2);
				// search for name
				SearchTestCriteria crit2 = new SearchTestCriteria();
				crit2.setExpectedDocuments(1);
				crit2.getFieldNamesToSearchValues().put("name", "permanent*");
				// search for non-existent name
				SearchTestCriteria crit3 = new SearchTestCriteria();
				crit3.setExpectedDocuments(0);
				crit3.getFieldNamesToSearchValues().put("name", "*temp*");
				
				List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
				crits.add(crit1);
				crits.add(crit2);
				crits.add(crit3);
				runDocumentSearch(crits, docType);
			}

}