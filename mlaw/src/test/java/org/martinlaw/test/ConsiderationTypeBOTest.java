/**
 * 
 */
package org.martinlaw.test;

import org.martinlaw.bo.BaseDetail;
import org.martinlaw.bo.ConsiderationType;

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




/**
 * test various BO ops for {@link ConsiderationType}
 * 
 * @author mugo
 * 
 */
public class ConsiderationTypeBOTest extends BaseDetailBoTestBase {
	private ConsiderationType considerationType;
	@Override
	public Class<? extends BaseDetail> getDataObjectClass() {
		return ConsiderationType.class;
	}

	/**
	 * 
	 */
	public ConsiderationTypeBOTest() {
		considerationType = new ConsiderationType();
		considerationType.setId(1003l);
		considerationType.setName("purchase price");
		considerationType.setDescription("the purchase price");
	}

	@Override
	public BaseDetail getExpectedOnRetrieve() {
		return considerationType;
	}

	@Override
	public String getDocTypeName() {
		return "ConsiderationTypeMaintenanceDocument";
	}
}
