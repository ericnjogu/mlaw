/**
 * 
 */
package org.martinlaw.test.opinion;

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



import org.junit.Test;
import org.martinlaw.bo.opinion.Opinion;
import org.martinlaw.bo.opinion.Client;
import org.martinlaw.bo.opinion.Fee;
import org.martinlaw.test.MartinlawTestsBase;

/**
 * tests data dictionary of {@link Opinion}, {@link Fee} and {@link Client}
 * 
 * @author mugo
 *
 */
public class OpinionDDTest extends MartinlawTestsBase {
	@Test
	/**
	 * test that {@link Client} is loaded into the data dictionary
	 */
	public void testOpinionClientAttributes() {
		super.testBoAttributesPresent(Client.class.getCanonicalName());
	}
	
	@Test
	/**
	 * test that {@link Client} is loaded into the data dictionary
	 */
	public void testOpinionFeeAttributes() {
		super.testBoAttributesPresent(Fee.class.getCanonicalName());
	}
	
	@Test
	/**
	 * test that {@link Opinion} is loaded into the data dictionary
	 */
	public void testOpinionAttributes() {
		super.testBoAttributesPresent(Opinion.class.getCanonicalName());
		Class<Opinion> dataObjectClass = Opinion.class;
		super.verifyMaintDocDataDictEntries(dataObjectClass);
	}
}
