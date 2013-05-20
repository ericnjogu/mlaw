/**
 * 
 */
package org.martinlaw.test;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

/**
 * @author mugo
 *
 */
public class PhoneNumberPatternRegexTest {

	private Pattern regexPattern;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		String pattern = "([0-9]{4}[\\- ][0-9]{3}[\\- ][0-9]{3})|([0-9]{4}[\\- ][0-9]{2}[\\- ][0-9]{2}[\\- ][0-9]{2})|([0-9]{4}[\\- ][0-9]{6})";
		regexPattern = Pattern.compile(pattern);
	}

	@Test
	public void test_mobile_spaces_groupedBy2() {
		assertTrue("phone number does not match pattern", regexPattern.matcher("0722 12 34 56").matches());
	}
	
	@Test
	public void test_mobile_spaces_groupedBy3() {
		assertTrue("phone number does not match pattern", regexPattern.matcher("0722 123 456").matches());
	}
	
	@Test
	public void test_mobile_hyphen_groupedBy3() {
		assertTrue("phone number does not match pattern", regexPattern.matcher("0722-123-456").matches());
	}
	
	@Test
	public void test_mobile_hyphen_groupedBy2() {
		assertTrue("phone number does not match pattern", regexPattern.matcher("0722-12-34-56").matches());
	}
	
	@Test
	public void test_mobile_hyphen_after_prefix() {
		assertTrue("phone number does not match pattern", regexPattern.matcher("0722-123456").matches());
	}
	
	@Test
	public void test_mobile_space_after_prefix() {
		assertTrue("phone number does not match pattern", regexPattern.matcher("0722 123456").matches());
	}

}
