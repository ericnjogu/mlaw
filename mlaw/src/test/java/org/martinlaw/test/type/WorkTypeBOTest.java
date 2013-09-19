/**
 * 
 */
package org.martinlaw.test.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.martinlaw.bo.BaseDetail;
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.Scope;
import org.martinlaw.bo.WorkType;
import org.martinlaw.bo.WorkTypeScope;
import org.martinlaw.bo.contract.Contract;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.keyvalues.ScopedKeyValuesUif;

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
 * test various BO ops for {@link WorkType}
 * 
 * @author mugo
 * 
 */
public class WorkTypeBOTest extends BaseDetailBoTestBase {
	private WorkType workType;
	@Override
	public Class<? extends BaseDetail> getDataObjectClass() {
		return WorkType.class;
	}

	/**
	 * 
	 */
	public WorkTypeBOTest() {
		workType = new WorkType();
		workType.setId(10004l);
		workType.setName("demand letter");
		workType.setDescription("the battle cry");
	}

	@Override
	public BaseDetail getExpectedOnRetrieve() {
		return workType;
	}

	@Override
	public String getDocTypeName() {
		return "WorkTypeMaintenanceDocument";
	}

	/**
	 * test that the scope is populated from the db ok
	 */
	@Test
	public void testWorkTypeScopeRetrieve() {
		WorkType cnType = getBoSvc().findBySinglePrimaryKey(
				WorkType.class, getExpectedOnRetrieve().getId());
		assertNotNull("scope should not be null", cnType.getScope());
		assertFalse("scope should not be empty", cnType.getScope().isEmpty());
		assertEquals("simple class name differs", CourtCase.class.getSimpleName(), cnType.getScope().get(0).getSimpleClassName());
		
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.BaseDetailBoTestBase#testContractTypeCRUD()
	 */
	@Override
	@Test
	public void testBaseDetailCRUD() throws InstantiationException,
			IllegalAccessException {
		// C
		WorkType type = new WorkType();
		String name = "test type";
		type.setName(name);
		WorkTypeScope scope1 = new WorkTypeScope();
		scope1.setQualifiedClassName(Matter.class.getCanonicalName());
		type.getScope().add(scope1);
		WorkTypeScope scope2 = new WorkTypeScope();
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
		criteria.put("workTypeId", String.valueOf(type.getId()));
		assertTrue("scopes should have been deleted", getBoSvc().findMatching(WorkTypeScope.class, criteria).isEmpty());
	}
	
	@Test
	/**
	 * test that consideration type key values returns the correct number
	 */
	public void testMatterStatusKeyValues() {
		MaintenanceDocumentForm form = getTestUtils().createMockMaintenanceDocForm();
		ScopedKeyValuesUif kv = new ScopedKeyValuesUif();
		kv.setScopedClass(WorkType.class);
		Maintainable newMaintainableObject = form.getDocument().getNewMaintainableObject();
		
		String comment = "expected 5 work type that applies to all, 3 that apply to court case";
		when(newMaintainableObject.getDataObject()).thenReturn(new CourtCase());
		assertEquals(comment, 8, kv.getKeyValues(form).size());
		
		comment = "expected 5 work types that applies to all";
		when(newMaintainableObject.getDataObject()).thenReturn(new Contract());
		assertEquals(comment, 5, kv.getKeyValues(form).size());
		
		/*when(newMaintainableObject.getDataObject()).thenReturn(new Opinion());
		assertEquals(comment, 5, kv.getKeyValues(form).size());*/

		when(newMaintainableObject.getDataObject()).thenReturn(new Conveyance());
		assertEquals(comment, 5, kv.getKeyValues(form).size());
	}

	@Override
	public Class<? extends Scope> getScopeClass() {
		return WorkTypeScope.class;
	}
}
