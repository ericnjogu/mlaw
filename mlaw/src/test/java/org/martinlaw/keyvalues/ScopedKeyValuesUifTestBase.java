/**
 * 
 */
package org.martinlaw.keyvalues;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.martinlaw.bo.Matter;
import org.martinlaw.bo.MatterExtensionHelper;
import org.martinlaw.bo.MatterTxDocBase;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.web.MatterTxForm;

/**
 * holds common methods for testing {@link ScopedKeyValuesUif} for specific scoped classes e.g. event type
 * @author mugo
 *
 */
public abstract class ScopedKeyValuesUifTestBase {

	private ScopedKeyValuesUif keyValues;
	private BusinessObjectService boSvc;
	private String concreteClass;
	private long matterId;
	private Matter matter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		keyValues = new ScopedKeyValuesUif();
		boSvc = mock(BusinessObjectService.class);
		keyValues.setBusinessObjectService(boSvc);
		matter = new Matter();
		concreteClass = "org.mlaw.bo.files.Prosecution";
		matter.setConcreteClass(concreteClass);
		matterId = 54l;
		
	}

	/**
	 * Test method for {@link org.martinlaw.keyvalues.ScopedKeyValuesUif#getQualifiedMatterClassName()}.
	 */
	@Test
	public void testGetQualifiedMatterClassName_maintenance() {

		MaintenanceDocumentForm model = mock(MaintenanceDocumentForm.class);
		final String message = "no qualified class name without document+maintainable+dataobject and valid matter id";
		assertTrue(message,StringUtils.isEmpty(keyValues.getQualifiedMatterClassName(model)));
		MaintenanceDocument doc = mock(MaintenanceDocument.class);
		when(model.getDocument()).thenReturn(doc);
		assertTrue(message,StringUtils.isEmpty(keyValues.getQualifiedMatterClassName(model)));
		Maintainable maintainable = mock(Maintainable.class);
		when(doc.getNewMaintainableObject()).thenReturn(maintainable);
		assertTrue(message,StringUtils.isEmpty(keyValues.getQualifiedMatterClassName(model)));
		
		MatterExtensionHelper matterHelper = getTestDataObject();
		matterHelper.setMatterId(matterId);
		when(maintainable.getDataObject()).thenReturn(matterHelper);
		assertTrue(message,StringUtils.isEmpty(keyValues.getQualifiedMatterClassName(model)));
		
		when(boSvc.findBySinglePrimaryKey(same(Matter.class), same(matterId))).thenReturn(matter);
		assertNotNull("Qualified Matter Class Name should not be null", keyValues.getQualifiedMatterClassName(model));
		assertEquals("Qualified Matter Class Name differs", concreteClass, keyValues.getQualifiedMatterClassName(model));
		
		// when the data object being maintained is an instance of Matter, return its class
		when(maintainable.getDataObject()).thenReturn(new Conveyance());
		assertNotNull("Qualified Matter Class Name should not be null", keyValues.getQualifiedMatterClassName(model));
		assertEquals("Qualified Matter Class Name differs", Conveyance.class.getCanonicalName(), keyValues.getQualifiedMatterClassName(model));
	}
	
	/**
	 * Test method for {@link org.martinlaw.keyvalues.ScopedKeyValuesUif#getQualifiedMatterClassName()}.
	 * <p>covers {@link MatterTransactionDoc} (transaction type), {@link MatterWork} (work type)
	 */
	@Test
	public void testGetQualifiedMatterClassName_transactional() {
		final String message = "no qualified class name without document (dataobject) and valid matter id";
		MatterTxForm model = mock(MatterTxForm.class);
		assertTrue(message,StringUtils.isEmpty(keyValues.getQualifiedMatterClassName(model)));
		MatterTxDocBase doc = mock(MatterTxDocBase.class);
		when(model.getDocument()).thenReturn(doc);
		assertTrue(message,StringUtils.isEmpty(keyValues.getQualifiedMatterClassName(model)));
		
		when(doc.getMatterId()).thenReturn(matterId);
		assertTrue(message, StringUtils.isEmpty(keyValues.getQualifiedMatterClassName(model)));
		
		when(boSvc.findBySinglePrimaryKey(same(Matter.class), same(matterId))).thenReturn(matter);
		final String qualifiedMatterClassName = keyValues.getQualifiedMatterClassName(model);
		assertNotNull("Qualified Matter Class Name should not be null", qualifiedMatterClassName);
		assertEquals("Qualified Matter Class Name differs", concreteClass, qualifiedMatterClassName);
	}

	/**
	 * Test method for {@link org.martinlaw.keyvalues.ScopedKeyValuesUif#getScopedKeyValuesHelper()}.
	 */
	@Test
	public void testGetScopedKeyValuesHelper() {
		assertNotNull("ScopedKeyValuesHelper should not be null", keyValues.getScopedKeyValuesHelper());
	}
	
	/**
	 * get the test data object
	 * @return
	 */
	protected abstract MatterExtensionHelper getTestDataObject();

}
