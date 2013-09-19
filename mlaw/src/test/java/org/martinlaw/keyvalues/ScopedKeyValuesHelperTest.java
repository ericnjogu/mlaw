/**
 * 
 */
package org.martinlaw.keyvalues;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.martinlaw.bo.Status;
import org.martinlaw.bo.StatusScope;

/**
 * Tests {@link org.martinlaw.keyvalues.ScopedKeyValuesHelper}
 * @author mugo
 *
 */
public class ScopedKeyValuesHelperTest {

	/**
	 * Test method for {@link org.martinlaw.keyvalues.ScopedKeyValuesHelper#getKeyValues(java.lang.String, java.lang.Class)}.
	 */
	@Test
	public void testGetKeyValues() {
		ScopedKeyValuesHelper keyValuesHelper = new ScopedKeyValuesHelper();
		BusinessObjectService boSvc = mock(BusinessObjectService.class);
		keyValuesHelper.setBusinessObjectService(boSvc);
		
		String marriageClassName = "org.Marriage";
		String weddingClassName = "org.Wedding";
		String foodClassName = "org.Food";
		Class<? extends BusinessObject> scopedClass = Status.class;
		
		List<Status> statusList = new ArrayList<Status>(3);
		
		final String statusChristian = "Christian";
		Status status1 = new Status(1l, statusChristian);
		StatusScope[] scope1 = {new StatusScope(marriageClassName), new StatusScope(weddingClassName)};
		status1.setScope(Arrays.asList(scope1));
		
		final String statusFulfld = "Fulfilled";
		Status status2 = new Status(1l, statusFulfld);
		StatusScope[] scope2 = {new StatusScope(marriageClassName)};
		status2.setScope(Arrays.asList(scope2));
		
		// status 'good applies to all classes, so leave scope blank
		final String statusGd = "Good";
		Status status3 = new Status(1l, statusGd);
		statusList.add(status1);
		statusList.add(status2);
		statusList.add(status3);
		when(boSvc.findAll(same(Status.class))).thenReturn(statusList);
		
		List<KeyValue> keyValuesResult = keyValuesHelper.getKeyValues(marriageClassName, scopedClass);
		String [] expectedStatusArray = {statusChristian, statusFulfld, statusGd};
		testKeyValuesResult(expectedStatusArray, keyValuesResult);
		
		keyValuesResult = keyValuesHelper.getKeyValues(weddingClassName, scopedClass);
		String [] expectedStatusArray2 = {statusChristian, statusGd};
		testKeyValuesResult(expectedStatusArray2, keyValuesResult);
		
		keyValuesResult = keyValuesHelper.getKeyValues(foodClassName, scopedClass);
		String [] expectedStatusArray3 = {statusGd};
		testKeyValuesResult(expectedStatusArray3, keyValuesResult);
		
	}

	/**
	 * convenience method to test expected key values
	 * @param expectedValuesArray - a list of expected values
	 * @param keyValuesResult - the key values to check values
	 */
	protected void testKeyValuesResult(String[] expectedValuesArray, final List<KeyValue> keyValuesResult) {
		assertNotNull("key values should not be null", keyValuesResult);
		assertFalse("key values should not be empty", keyValuesResult.isEmpty());
		assertEquals("key values size differs", expectedValuesArray.length, keyValuesResult.size());
		int count = 0;
		for (String status: expectedValuesArray) {
			assertEquals("status value differs", status, keyValuesResult.get(count).getValue());
			count += 1;
		}
	}
}
