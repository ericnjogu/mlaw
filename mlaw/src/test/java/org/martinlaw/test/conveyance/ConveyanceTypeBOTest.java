/**
 * 
 */
package org.martinlaw.test.conveyance;

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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.martinlaw.bo.conveyance.ConveyanceAnnexType;
import org.martinlaw.bo.conveyance.ConveyanceType;
import org.martinlaw.test.MartinlawTestsBase;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * various tests for {@link }
 * @author mugo
 *
 */
public class ConveyanceTypeBOTest extends MartinlawTestsBase {
	@Test
	/**
	 * test that {@link ConveyanceType} is loaded into the data dictionary
	 */
	public void testConveyanceTypeAttributes() {
		testBoAttributesPresent(ConveyanceType.class.getCanonicalName());
		
		Class<ConveyanceType> dataObjectClass = ConveyanceType.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}
	
	@Test
	/**
	 * test that {@link ConveyanceAnnexType} is loaded into the data dictionary
	 */
	public void testConveyanceAnnexTypeAttributes() {
		testBoAttributesPresent(ConveyanceAnnexType.class.getCanonicalName());
		// will maintained as part of conveyance type
/*		Class<ConveyanceAnnexType> dataObjectClass = ConveyanceAnnexType.class;
		verifyMaintDocDataDictEntries(dataObjectClass);*/
	}
	
	@Test
	/**
	 * test CRUD for {@link ConveyanceAnnexType}
	 */
	public void testConveyanceTypeCRUD() {
		// get number of annex types before adding new
		int existingAnnexTypes = getBoSvc().findAll(ConveyanceAnnexType.class).size();
		// retrieve object populated via sql script
		ConveyanceType convType = getBoSvc().findBySinglePrimaryKey(ConveyanceType.class, 1001l);
		assertNotNull(convType);
		assertEquals("Sale of Urban Land", convType.getName());
		convType = getTestConveyanceType();
		
		getBoSvc().save(convType);
		//R
		convType.refresh();
		assertEquals(2, convType.getAnnexTypes().size());
		assertEquals(2 + existingAnnexTypes, getBoSvc().findAll(ConveyanceAnnexType.class).size());
		//U
		convType.setDescription("hiring land from go.ke");
		getBoSvc().delete(convType.getAnnexTypes().get(0));
		convType.refresh();
		assertNotNull(convType.getDescription());
		assertEquals(1, convType.getAnnexTypes().size());
		assertEquals(1 + existingAnnexTypes, getBoSvc().findAll(ConveyanceAnnexType.class).size());
		//D
		getBoSvc().delete(convType);
		assertNull(getBoSvc().findBySinglePrimaryKey(ConveyanceAnnexType.class, convType.getId()));
		assertEquals(existingAnnexTypes, getBoSvc().findAll(ConveyanceAnnexType.class).size());
	}

	/**
	 * gets a test {@link ContractType} object
	 * @return the test object
	 */
	public ConveyanceType getTestConveyanceType() {
		ConveyanceType convType;
		//C
		convType = new ConveyanceType();
		convType.setName("Lease of Gov Land");
		//create annex types
		List<ConveyanceAnnexType> annexTypes = new ArrayList<ConveyanceAnnexType>();
		
		ConveyanceAnnexType convAnnexType = new ConveyanceAnnexType();
		convAnnexType.setName("signed affidavit");
		annexTypes.add(convAnnexType);
		
		convAnnexType = new ConveyanceAnnexType();
		convAnnexType.setName("title deed");
		annexTypes.add(convAnnexType);
		
		convType.setAnnexTypes(annexTypes);
		return convType;
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * test that non nullable fields for {@link ConveyanceAnnexType} throw a DB exception
	 */
	public void testConveyanceTypeNullableFields() {
		ConveyanceType convType = new ConveyanceType();
		getBoSvc().save(convType);
	}
	
	@Test
	/**
	 * test CRUD for {@link ConveyanceAnnexType}
	 */
	public void testConveyanceAnnexTypeCRUD() {
		// retrieve object populated via sql script
		ConveyanceAnnexType convAnnexType = getBoSvc().findBySinglePrimaryKey(ConveyanceAnnexType.class, 1001l);
		assertNotNull(convAnnexType);
		assertEquals("land board approval", convAnnexType.getName());
		assertEquals("default value differs", Long.valueOf(1), convAnnexType.getSequence());
		//C
		convAnnexType = new ConveyanceAnnexType();
		convAnnexType.setName("signed affidavit");
		convAnnexType.setConveyanceTypeId(1001l);
		final Long id = Long.valueOf(5);
		convAnnexType.setSequence(id);
		getBoSvc().save(convAnnexType);
		//R
		convAnnexType.refresh();
		convAnnexType = getBoSvc().findBySinglePrimaryKey(ConveyanceAnnexType.class, convAnnexType.getId());
		assertEquals("sequence differs", id, convAnnexType.getSequence());
		//U
		convAnnexType.setDescription("signed before a commissioner of oaths");
		convAnnexType.refresh();
		assertNotNull(convAnnexType.getDescription());
		//D
		getBoSvc().delete(convAnnexType);
		assertNull(getBoSvc().findBySinglePrimaryKey(ConveyanceAnnexType.class, convAnnexType.getId()));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	/**
	 * test that non nullable fields for {@link ConveyanceAnnexType} throw a DB exception
	 */
	public void testConveyanceAnnexTypeNullableFields() {
		ConveyanceAnnexType convAnnexType = new ConveyanceAnnexType();
		getBoSvc().save(convAnnexType);
	}
}
