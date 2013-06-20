/**
 * 
 */
package org.martinlaw.test.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.martinlaw.bo.BaseDetail;
import org.martinlaw.bo.ConsiderationType;
import org.martinlaw.bo.ConsiderationTypeScope;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.bo.opinion.Opinion;
import org.martinlaw.keyvalues.ContractConsiderationTypeKeyValues;
import org.martinlaw.keyvalues.ConveyanceConsiderationTypeKeyValues;
import org.martinlaw.keyvalues.CourtCaseConsiderationTypeKeyValues;
import org.martinlaw.keyvalues.OpinionConsiderationTypeKeyValues;

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
		considerationType.setId(10004l);
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

	/**
	 * test that the scope is populated from the db ok
	 */
	@Test
	public void testConsiderationTypeScopeRetrieve() {
		ConsiderationType cnType = getBoSvc().findBySinglePrimaryKey(
				ConsiderationType.class, getExpectedOnRetrieve().getId());
		assertNotNull("scope should not be null", cnType.getScope());
		assertFalse("scope should not be empty", cnType.getScope().isEmpty());
		assertEquals("simple class name differs", Conveyance.class.getSimpleName(), cnType.getScope().get(0).getSimpleClassName());
		
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.BaseDetailBoTestBase#testContractTypeCRUD()
	 */
	@Override
	@Test
	public void testBaseDetailCRUD() throws InstantiationException,
			IllegalAccessException {
		// C
		ConsiderationType type = new ConsiderationType();
		String name = "test type";
		type.setName(name);
		ConsiderationTypeScope scope1 = new ConsiderationTypeScope();
		scope1.setQualifiedClassName(Opinion.class.getCanonicalName());
		type.getScope().add(scope1);
		ConsiderationTypeScope scope2 = new ConsiderationTypeScope();
		scope2.setQualifiedClassName(CourtCase.class.getCanonicalName());
		type.getScope().add(scope2);
		getBoSvc().save(type);
		// R
		type.refresh();
		assertEquals("name does not match", name, type.getName());
		assertNotNull("scope should not be null", type.getScope());
		assertEquals("scope size differs", 2, type.getScope().size());
		// U
		type.setDescription("test description");
		type.getScope().remove(0);
		getBoSvc().save(type);
		assertNotNull("description should not be null", type.getDescription());
		assertEquals("scope size differs", 1, type.getScope().size());
		// D
		getBoSvc().delete(type);
		assertNull(getBoSvc().findBySinglePrimaryKey(getDataObjectClass(),	type.getId()));
		Map<String, String> criteria = new HashMap<String, String>();
		criteria.put("considerationTypeId", String.valueOf(type.getId()));
		assertTrue("scopes should have been deleted", getBoSvc().findMatching(ConsiderationTypeScope.class, criteria).isEmpty());
	}
	
	@Test
	/**
	 * test that consideration type key values returns the correct number
	 */
	public void testMatterStatusKeyValues() {
		String comment = "expected 1 consideration type that applies to all and a blank one";
		getTestUtils().testMatterStatusKeyValues(new CourtCaseConsiderationTypeKeyValues(), comment, 2);
		comment = "expected 1 consideration type with contract scope, one that applies to all (empty), plus a blank one";
		getTestUtils().testMatterStatusKeyValues(new ContractConsiderationTypeKeyValues(), comment, 3);
		comment = "expected one that applies to all (empty) and a blank one";
		getTestUtils().testMatterStatusKeyValues(new OpinionConsiderationTypeKeyValues(), comment, 2);
		comment = "expected two consideration type with conveyance scope, one that applies to all (empty), plus a blank one";
		getTestUtils().testMatterStatusKeyValues(new ConveyanceConsiderationTypeKeyValues(), comment, 4);
	}
}
