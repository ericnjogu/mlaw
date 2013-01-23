/**
 * 
 */
package org.martinlaw.test.date;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012 Eric Njogu (kunadawa@gmail.com)
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;
import org.martinlaw.bo.MatterDate;
import org.martinlaw.bo.courtcase.MyDate;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * tests {@link MyDate}
 * 
 * @author mugo
 *
 */
public class CourtCaseDateBoTest extends MartinlawTestsBase {
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * tests non nullable fields are checked
	 */
	public void testCourtCaseDateDateNullableFields() {
		MyDate date = new MyDate();
		getBoSvc().save(date);
	}
	
	@Test
	/**
	 * tests that a CourtCase date, inserted via an sql script in {@link #loadSuiteTestData()} can be retrieved
	 */
	public void testCourtCaseDateRetrieve() {
		MatterDate date = getBoSvc().findBySinglePrimaryKey(MyDate.class, new Long(1001));
		assertNotNull("date should be present in database", date);
		getTestUtils().testRetrievedMatterDateFields(date);
		assertNotNull("date type should not be null", date.getType());
		assertEquals("date type id not does not match", new Long(1002), date.getType().getId());
	}
	
	@Test
	/**
	 * tests {@link MyDate}  CRUD ops
	 */
	public void testCourtCaseDateCRUD() {
		Date date = new Date(Calendar.getInstance().getTimeInMillis());
		MyDate myDate = new MyDate(date, "soon", 1001l);
		myDate.setTypeId(1001l);
		// C
		getBoSvc().save(myDate);
		// R
		myDate.refresh();
		assertNotNull("date type should not be null", myDate.getType());
		assertEquals("Date type name does not match", "Hearing", myDate.getType().getName());
		// U
		String comment = "later";
		myDate.setComment(comment);
		getBoSvc().save(myDate);
		myDate.refresh();
		assertEquals("comment does not match", comment, myDate.getComment());
		// D
		getBoSvc().delete(myDate);
		assertNull("BO should have been deleted", getBoSvc().findBySinglePrimaryKey(MyDate.class, myDate.getId()));
	}
	
	@Test
	/**
	 * test that the MyDate is loaded into the data dictionary
	 */
	public void testCourtCaseDateAttributes() {
		testBoAttributesPresent(MyDate.class.getCanonicalName());
		Class<MyDate> dataObjectClass = MyDate.class;
		verifyInquiryLookup(dataObjectClass);
	}
	
	/*@Override
	protected void loadSuiteTestData() throws Exception {
		super.loadSuiteTestData();
		new SQLDataLoader("classpath:org/martinlaw/scripts/default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/case-test-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/date-type-default-data.sql", ";").runSql();
		new SQLDataLoader("classpath:org/martinlaw/scripts/case-date-test-data.sql", ";").runSql();
	}*/
}
