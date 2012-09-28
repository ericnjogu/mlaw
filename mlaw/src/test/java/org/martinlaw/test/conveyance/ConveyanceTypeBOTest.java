/**
 * 
 */
package org.martinlaw.test.conveyance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.martinlaw.bo.conveyance.ConveyanceAnnexType;
import org.martinlaw.bo.conveyance.ConveyanceType;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * various tests for {@link }
 * @author mugo
 *
 */
public class ConveyanceTypeBOTest extends ConveyanceBOTestBase {
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
		//C
		convAnnexType = new ConveyanceAnnexType();
		convAnnexType.setName("signed affidavit");
		convAnnexType.setConveyanceTypeId(1001l);
		getBoSvc().save(convAnnexType);
		//R
		convAnnexType.refresh();
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
