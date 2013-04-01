package org.martinlaw.test;

import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.exception.ValidationException;
import org.martinlaw.bo.MatterEvent;
import org.martinlaw.util.SearchTestCriteria;

public abstract class MatterEventRoutingTest extends KewTestsBase {

	protected Log log = LogFactory.getLog(getClass());

	public MatterEventRoutingTest() {
		super();
	}

	/**
	 * test that a CourtCase Date maint doc can be created and edited by the authorized users only
	 * 
	 * @see /mlaw/src/main/resources/org/martinlaw/scripts/perms-roles.sql
	 */
	@Test
	public void testMatterEventMaintDocPerms() {
		testCreateMaintain(getDataObjectClass(), getDocType());
	}

	/**
	 * tests CourtCase Date maintenance doc routing
	 * 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws WorkflowException 
	 * 
	 */
	@Test
	public void testMatterEventRouting() throws InstantiationException,
			IllegalAccessException, WorkflowException {
		MatterEvent testDate = getTestUtils().getTestMatterEvent(getDataObjectClass());
		this.testMaintenanceRoutingInitToFinal(getDocType(), testDate);
	}

	/**
	 * verifies that the business rules create an error for a non existent court case id on save
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws WorkflowException
	 */
	@Test(expected = ValidationException.class)
	public void testMatterEventRouting_InvalidMatterId()
			throws InstantiationException, IllegalAccessException,
			WorkflowException {
		MatterEvent testDate = getTestUtils().getTestMatterEvent(getDataObjectClass());
		testDate.setMatterId(3000l);
		
		/*//initiate as the clerk
		Document doc = getPopulatedMaintenanceDocument(getDocType(), testDate, "clerk1");
		KRADServiceLocatorWeb.getDocumentService().saveDocument(doc);*/
		testMaintenanceRoutingInitToFinal(getDocType(), testDate);
	}

	/**
	 * verifies that the required fields are validated on route not save
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws WorkflowException
	 */
	@Test
	public void testMatterEventRouting_required_validated_onroute()
			throws InstantiationException, WorkflowException,
			IllegalAccessException {
		MatterEvent testDate = getTestUtils().getTestMatterEvent(getDataObjectClass());
		// required on route
		testDate.setStartDate(null);
		testDate.setTypeId(null);
		
		//initiate as the clerk
		Document doc = getPopulatedMaintenanceDocument(getDocType(), testDate, "clerk1");
		testRouting_required_validated_onroute(doc);
	}

	@Test
	public void testMatterEvent_doc_search() {
		try {
			// route 2 docs first
			MatterEvent testEvent1 = getTestUtils().getTestMatterEvent(getDataObjectClass());
			final String docType = getDocType();
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm");
			Date date = sdf.parse("04 mar 2013 16:54");
			testEvent1.setStartDate(new Timestamp(date.getTime()));
			testMaintenanceRoutingInitToFinal(docType, testEvent1);
			
			MatterEvent testEvent2 = getTestUtils().getTestMatterEvent(getDataObjectClass());
			String location = "nakuru";
			testEvent2.setLocation(location);
			testEvent2.setComment("optional for ict department");
			testEvent2.setStartDate(new Timestamp(System.currentTimeMillis()));
			testMaintenanceRoutingInitToFinal(docType, testEvent2);
			
			// blank document search
			SearchTestCriteria crit1 = new SearchTestCriteria();
			crit1.setExpectedDocuments(2);
			// search location
			SearchTestCriteria crit2 = new SearchTestCriteria();
			crit2.setExpectedDocuments(1);
			crit2.getFieldNamesToSearchValues().put("location", location);
			// search for comment wildcard
			SearchTestCriteria crit3 = new SearchTestCriteria();
			crit3.setExpectedDocuments(1);
			crit3.getFieldNamesToSearchValues().put("comment", "*ict*");
			// search for start date range
			SearchTestCriteria crit4 = new SearchTestCriteria();
			crit4.setExpectedDocuments(1);
			crit4.getFieldNamesToSearchValues().put("startDate", "01 mar 2013 .. 05 mar 2013");
			
			List<SearchTestCriteria> crits = new ArrayList<SearchTestCriteria>(); 
			crits.add(crit1);
			crits.add(crit2);
			crits.add(crit3);
			crits.add(crit4);
			runDocumentSearch(crits, docType);
		} catch (Exception e) {
			log.error("test failed", e);
			fail("exception occurred");
		}
	}
	
	/**
	 * 
	 * @return the doc type being tested
	 */
	public abstract String getDocType();
	
	/**
	 * 
	 * @return the data object (BO) class
	 */
	public abstract Class<? extends MatterEvent> getDataObjectClass();

}