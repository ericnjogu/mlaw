package org.martinlaw.test;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2013 Eric Njogu (kunadawa@gmail.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.ken.api.KenApiConstants;
import org.kuali.rice.ken.api.notification.NotificationResponse;
import org.kuali.rice.ken.api.service.SendNotificationService;
import org.kuali.rice.ken.util.NotificationConstants;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.exception.ValidationException;
import org.martinlaw.bo.MatterEvent;
import org.martinlaw.util.SearchTestCriteria;
import org.martinlaw.util.TestUtils;

public class MatterEventRoutingTest extends KewTestsBase {

	protected Log log = LogFactory.getLog(getClass());

	public MatterEventRoutingTest() {
		super();
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
		MatterEvent testDate = getTestUtils().getTestMatterEventIT(getDataObjectClass());
		this.testMaintenanceRoutingInitToFinal(getDocTypeName(), testDate);
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
		MatterEvent testDate = getTestUtils().getTestMatterEventIT(getDataObjectClass());
		testDate.setMatterId(3000l);
		
		/*//initiate as the clerk
		Document doc = getPopulatedMaintenanceDocument(getDocType(), testDate, "clerk1");
		KRADServiceLocatorWeb.getDocumentService().saveDocument(doc);*/
		testMaintenanceRoutingInitToFinal(getDocTypeName(), testDate);
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
		MatterEvent testDate = getTestUtils().getTestMatterEventIT(getDataObjectClass());
		// required on route
		testDate.setStartDate(null);
		testDate.setTypeId(null);
		
		//initiate as the clerk
		Document doc = getPopulatedMaintenanceDocument(getDocTypeName(), testDate, "clerk1");
		testRouting_required_validated_onroute(doc);
	}

	@Test
	public void testMatterEvent_doc_search() {
		try {
			// route 2 docs first
			MatterEvent testEvent1 = getTestUtils().getTestMatterEventIT(getDataObjectClass());
			final String docType = getDocTypeName();
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm");
			Date date = sdf.parse("04 mar 2013 16:54");
			testEvent1.setStartDate(new Timestamp(date.getTime()));
			testMaintenanceRoutingInitToFinal(docType, testEvent1);
			
			MatterEvent testEvent2 = getTestUtils().getTestMatterEventIT(getDataObjectClass());
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
			getTestUtils().runDocumentSearch(crits, docType);
		} catch (Exception e) {
			log.error("test failed", e);
			fail("exception occurred");
		}
	}
	
	/**
	 * 
	 * @return the data object (BO) class
	 */
	public Class<? extends MatterEvent> getDataObjectClass() {
		return MatterEvent.class;
	}

	@Override
	public String getDocTypeName() {
		return "MatterEventMaintenanceDocument";
	}

	@Test
	public void testSendEventNotification()
			throws RiceIllegalArgumentException, IOException {
				try {
					TestUtils utils = new TestUtils();
					// send notification
					final SendNotificationService sendNotificationService = (SendNotificationService) GlobalResourceLoader.getService(
							new QName(KenApiConstants.Namespaces.KEN_NAMESPACE_2_0, "sendNotificationService"));
					NotificationResponse response = sendNotificationService.invoke(utils.getTestNotificationXml());
			
					assertEquals("response status is not success", NotificationConstants.RESPONSE_STATUSES.SUCCESS, response.getStatus());
				} catch (Exception e) {
					log.error("exception occured", e);
					fail("exception occured");
				}
				
			}

}