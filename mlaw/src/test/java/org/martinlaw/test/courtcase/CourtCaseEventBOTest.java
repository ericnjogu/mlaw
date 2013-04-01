/**
 * 
 */
package org.martinlaw.test.courtcase;

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



import org.martinlaw.bo.MatterEvent;
import org.martinlaw.bo.courtcase.Event;
import org.martinlaw.test.MatterEventBOTest;

/**
 * test various BO ops for {@link Event}
 * 
 * @author mugo
 * 
 */
// @BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
public class CourtCaseEventBOTest extends MatterEventBOTest {
	/**
	 * @return the specific class being tested
	 */
	public Class<? extends MatterEvent> getDataObjectClass() {
		return Event.class;
	}

	@Override
	public String getMatterIdLabel() {
		return "Court Case";
	}
}
