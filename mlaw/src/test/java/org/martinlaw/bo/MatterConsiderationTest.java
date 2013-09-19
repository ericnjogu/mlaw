package org.martinlaw.bo;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.martinlaw.util.TestUtils;

public class MatterConsiderationTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testToHtml() throws InstantiationException, IllegalAccessException {
		//use court case consideration
		TestUtils utils = new TestUtils();
		MatterConsideration csd = (MatterConsideration) utils.getTestConsideration();
		ConsiderationType type =  new ConsiderationType();
		final String considerationTypeName = "reserve";
		type.setName(considerationTypeName);
		csd.setConsiderationType(type);
		BigDecimal[] amounts = {new BigDecimal(450), new BigDecimal(640)};
		for (BigDecimal amount: amounts) {
			final MatterTransactionDoc testTransaction = (MatterTransactionDoc) utils.getMockTransaction(amount);
			csd.getTransactions().add(testTransaction);
		}
		
		String expected = "<b>" + considerationTypeName + "</b>:&nbsp;KES 2,090.00";
		assertEquals("consideration html differs", expected, csd.toHtml());
		
		csd = (MatterConsideration) utils.getTestConsideration();
		csd.setAmount(new BigDecimal(0));
		assertEquals("consideration html differs", "", csd.toHtml());
		
		csd = (MatterConsideration) utils.getTestConsideration();
		expected = "<b>" + considerationTypeName + "</b>:&nbsp;KES 1,000.00";
		csd.setConsiderationType(type);
		assertEquals("consideration html differs", expected, csd.toHtml());
	}
}
