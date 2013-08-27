package org.martinlaw.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.uif.element.Link;
import org.kuali.rice.krad.uif.widget.Inquiry;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.InquiryForm;
import org.martinlaw.bo.conveyance.Consideration;
import org.martinlaw.bo.courtcase.Event;
import org.martinlaw.bo.courtcase.CourtCase;
import org.martinlaw.bo.courtcase.LandCase;
import org.martinlaw.test.MartinlawTestsBase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.same;
import static org.mockito.Matchers.anyMapOf;

public class EnhancedInquirableImplTest extends MartinlawTestsBase {

	private static final Class<CourtCase> DATA_OBJECT_CLASS = CourtCase.class;
	private EnhancedInquirableImpl inquirable;
	private Object dataObject;

	@Test
	public void testGetMaintenanceActionLink() {
		 String editUrl = "maintenance?viewTypeName=MAINTENANCE&methodToCall=maintenanceEdit" +
		 		"&dataObjectClassName=org.martinlaw.bo.courtcase.CourtCase&id=1001";
		 String copyUrl = "maintenance?viewTypeName=MAINTENANCE&methodToCall=maintenanceCopy" +
			 		"&dataObjectClassName=org.martinlaw.bo.courtcase.CourtCase&id=1001";
		 String delUrl = "maintenance?viewTypeName=MAINTENANCE&methodToCall=maintenanceDelete" +
			 		"&dataObjectClassName=org.martinlaw.bo.courtcase.CourtCase&id=1001";
		 String newUrl = "maintenance?viewTypeName=MAINTENANCE&methodToCall=start" +
			 		"&dataObjectClassName=org.martinlaw.bo.courtcase.CourtCase";
		 String[] urls = {editUrl, copyUrl, delUrl}; 
		 
		 String[] methods = {KRADConstants.Maintenance.METHOD_TO_CALL_EDIT, KRADConstants.Maintenance.METHOD_TO_CALL_COPY,
				 KRADConstants.Maintenance.METHOD_TO_CALL_DELETE};
		 InquiryForm form = new InquiryForm();
		 form.setDataObject(dataObject);
		 form.setDataObjectClassName(DATA_OBJECT_CLASS.getCanonicalName());
		 Link link = new Link();
		 // test for actions on existing object
		 for (int i=0; i<urls.length; i++) {
			 inquirable.getMaintenanceActionLink(link, form, methods[i]);
			 assertEquals("action URL differs", urls[i], link.getHref());
		 }
		 
		 //testing new url
		 form.setDataObject(null);
		 inquirable.getMaintenanceActionLink(link, form, KRADConstants.Maintenance.METHOD_TO_CALL_NEW);
		 assertEquals("action URL differs", newUrl, link.getHref());
	}
	
	@Test
	/**
	 * @see org.martinlaw.web.EnhancedInquirableImpl#allowsMaintenanceNewOrCopyAction()
	 */
	public void testAllowsMaintenanceNewOrCopyAction() {
		assertTrue(DATA_OBJECT_CLASS.getCanonicalName() + " should allow new or copy", 
				inquirable.allowsMaintenanceNewOrCopyAction());
	}
	
	@Test
	/**
	 * @see org.martinlaw.web.EnhancedInquirableImpl#allowsMaintenanceEditAction()
	 */
	public void testAllowsMaintenanceEditAction() {
		assertTrue(DATA_OBJECT_CLASS.getCanonicalName() + " should allow edit", 
				inquirable.allowsMaintenanceEditAction(dataObject));
	}
	
	@Test
	/**
	 * @see org.martinlaw.web.EnhancedInquirableImpl#allowsMaintenanceDeleteAction()
	 */
	public void testAllowsMaintenanceDeleteAction() {
		assertFalse(DATA_OBJECT_CLASS.getCanonicalName() + " does not allow Delete", 
				inquirable.allowsMaintenanceDeleteAction(dataObject));
	}

	/* (non-Javadoc)
	 * @see org.martinlaw.test.MartinlawTestsBase#setUpInternal()
	 */
	@Override
	protected void setUpInternal() throws Exception {
		super.setUpInternal();
		
		inquirable = new EnhancedInquirableImpl();
		inquirable.setDataObjectClass(DATA_OBJECT_CLASS);
		dataObject = getBoSvc().findBySinglePrimaryKey(DATA_OBJECT_CLASS, new Long(1001));
		
		String initiator = "clerk1";
		GlobalVariables.setUserSession(new UserSession(initiator));
	}
	
	@Test
	/**
	 * tests {@link Inquirable#buildInquirableLink(java.lang.Object, java.lang.String, org.kuali.rice.krad.uif.widget.Inquiry)}
	 */
	public void testBuildInquirableLinkObjectStringInquiry() {
		// concrete class is the same as the presented class
		Inquiry inq = mock(Inquiry.class);
		String propertyName = "localReference";
		inquirable.buildInquirableLink(dataObject, propertyName, inq);
		verify(inq).buildInquiryLink(same(dataObject), same(propertyName), same(CourtCase.class), anyMapOf(String.class, String.class));
		
		//concrete class is different from instantiated class
		CourtCase kase = new CourtCase();
		kase.setConcreteClass(LandCase.class.getCanonicalName());
		inquirable.buildInquirableLink(kase, propertyName, inq);
		verify(inq).buildInquiryLink(same(kase), same(propertyName), same(LandCase.class), anyMapOf(String.class, String.class));
		
		// land case event should turn up a inquiry link to land case, not court case
		Event event = getBoSvc().findBySinglePrimaryKey(Event.class, new Long(1002));
		propertyName = "matter.localReference";
		inquirable.buildInquirableLink(event, propertyName, inq);
		verify(inq).buildInquiryLink(same(event), same(propertyName), same(LandCase.class), anyMapOf(String.class, String.class));
		
		// test for an object that is not in the Matter class hierarchy
		Consideration csd = new Consideration();
		propertyName = "amount";
		inquirable.buildInquirableLink(csd, propertyName, inq);
		verify(inq).buildInquiryLink(same(csd), same(propertyName), same(Consideration.class), anyMapOf(String.class, String.class));
	}

}
