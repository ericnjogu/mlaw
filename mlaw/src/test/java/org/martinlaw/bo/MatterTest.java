/**
 * 
 */
package org.martinlaw.bo;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.martinlaw.MartinlawConstants;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.util.TestUtils;

/**
 * Tests {@link org.martinlaw.bo.Matter}.
 * @author mugo
 *
 */
public class MatterTest {

	private TestUtils utils;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		utils = new TestUtils();
	}

	/**
	 * Test method for {@link org.martinlaw.bo.Matter#getEventsHtml()}.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testGetEventsHtml() throws InstantiationException, IllegalAccessException {
		List<MatterEvent> events = new ArrayList<MatterEvent>(3);
		
		MatterEvent event = utils.getTestMatterEventUnt(MatterEvent.class);
		for (int i=0; i<3; i++) {
			events.add(event);
		}
		String expected = "<div class=\"" + MartinlawConstants.Styles.EVEN + "\">" + event.toHtml() +
				"</div><div class=\"" + MartinlawConstants.Styles.ODD + "\">" + event.toHtml() +
				"</div><div class=\"" + MartinlawConstants.Styles.EVEN + "\">" + event.toHtml() +
				"</div>";
		Conveyance matter = new Conveyance();
		matter.setEvents(events);
		assertEquals("html markup differs", expected, matter.getEventsHtml());
	}
	
	/**
	 * tests {@link org.martinlaw.bo.Matter#getConsiderationsHtml()}
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Test
	public void testGetConsiderationsHtml() throws InstantiationException, IllegalAccessException {
		Conveyance conv = new Conveyance();
		assertEquals("considerations html differs", "", conv.getConsiderationsHtml());
		
		MatterConsideration csd1 = (MatterConsideration) getTestConsiderationWithType("reserve");
		
		MatterConsideration csd2 = (MatterConsideration) getTestConsiderationWithType("penalty");
		csd2.getTransactions().add((MatterTransactionDoc) utils.getMockTransaction(new BigDecimal(42)));
		csd2.getTransactions().add((MatterTransactionDoc) utils.getMockTransaction(new BigDecimal(38)));
		
		MatterConsideration csd3 = (MatterConsideration) getTestConsiderationWithType("donation");
		csd3.setAmount(new BigDecimal(0));
		
		MatterConsideration csd4 = (MatterConsideration) getTestConsiderationWithType("bail");
		csd4.setAmount(new BigDecimal(1200));
		
		
		conv.getConsiderations().add(csd1);
		conv.getConsiderations().add(csd2);
		conv.getConsiderations().add(csd3);
		conv.getConsiderations().add(csd4);
		
		String expected = "<div class=\"mlaw_even\"><b>reserve</b>:&nbsp;KES 1,000.00</div>" +
				"<div class=\"mlaw_odd\"><b>penalty</b>:&nbsp;KES 1,080.00</div>" +
				"<div class=\"mlaw_even\"><b>bail</b>:&nbsp;KES 1,200.00</div>";
		assertEquals("considerations html differs", expected, conv.getConsiderationsHtml());
	}

	/**
	 * get a test consideration with a type of the name given
	 * @param typeName - the consideration type name
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public MatterConsideration getTestConsiderationWithType(String typeName) throws InstantiationException,
			IllegalAccessException {
		MatterConsideration csd1 = (MatterConsideration) utils.getTestConsideration();
		ConsiderationType csdType1 = new ConsiderationType();
		csdType1.setName(typeName);
		csd1.setConsiderationType(csdType1);
		
		return csd1;
	}

}
