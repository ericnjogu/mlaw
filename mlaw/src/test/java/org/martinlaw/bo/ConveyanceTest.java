/**
 * 
 */
package org.martinlaw.bo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.krad.bo.Attachment;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.bo.conveyance.ConveyanceAnnex;
import org.martinlaw.bo.conveyance.ConveyanceAnnexType;
import org.martinlaw.bo.conveyance.ConveyanceAttachment;
import org.martinlaw.service.RiceServiceHelper;


/**
 * tests methods in {@link org.martinlaw.bo.conveyance.Conveyance}
 * 
 * @author mugo
 *
 */
public class ConveyanceTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link org.martinlaw.bo.conveyance.Conveyance#getAnnexes()}.
	 */
	@Test
	public void testGetAnnexes() {
		Conveyance conv = new Conveyance();
		assertEquals("should be zero (default) since no type id has been set", 0, conv.getAnnexes().size());
		long conveyanceTypeId = 5000l;
		conv.setTypeId(conveyanceTypeId);
		BusinessObjectService boSvc = mock(BusinessObjectService.class);
		// mock not finding annex types to match the provided conveyance type
		List<ConveyanceAnnexType> annexTypes = new ArrayList<ConveyanceAnnexType>(); 
		when(boSvc.findMatching(eq(ConveyanceAnnexType.class), anyMapOf(String.class, Long.class))).thenReturn(annexTypes);
		RiceServiceHelper riceSvcHelper = mock(RiceServiceHelper.class);
		when(riceSvcHelper.getBusinessObjectService()).thenReturn(boSvc);
		conv.setRiceServiceHelper(riceSvcHelper);
		assertEquals("when annex types is an empty list, the annexes should be empty also", 0, conv.getAnnexes().size());
		// create a list of conveyance annexes
		List<ConveyanceAnnex> annexes = new ArrayList<ConveyanceAnnex>();
		ConveyanceAnnex convAnnex = new ConveyanceAnnex();
		ConveyanceAnnexType annexType = new ConveyanceAnnexType();
		annexType.setConveyanceTypeId(conveyanceTypeId);
		convAnnex.setType(annexType);
		annexes.add(convAnnex);
		// emulate when the annexes have been fetched from the db
		conv.setAnnexes(annexes);
		assertEquals("when the type id is the same as the one in the first annex, the current annexes should be returned",
				annexes, conv.getAnnexes());
		assertEquals("the conveyance type id in the annexes returned should be equal to the one set in the annexes provided",
				new Long(conveyanceTypeId), conv.getAnnexes().get(0).getType().getConveyanceTypeId());
		//emulate returning a list of annex types - when the type id in the conveyance is diff from the in the first annex
		long newConveyanceTypeId = 5005l;
		conv.setTypeId(newConveyanceTypeId);
		annexType.setConveyanceTypeId(newConveyanceTypeId);
		// will be returned in the mock
		annexTypes.add(annexType);
		assertEquals("the conveyance type id in the annexes returned should be equal to the one set in the conveyance",
				new Long(newConveyanceTypeId), conv.getAnnexes().get(0).getType().getConveyanceTypeId());
	}

	/**
	 * Test method for {@link org.martinlaw.bo.conveyance.Conveyance#hasAttachments()}.
	 */
	@Test
	public void testHasAttachments() {
		// when annexes are null
		Conveyance conv = new Conveyance();
		Long conveyanceTypeId = 3000l;
		conv.setTypeId(conveyanceTypeId);
		assertFalse("annexes are null", conv.hasAttachments());
		List<ConveyanceAnnex> annexes =  new ArrayList<ConveyanceAnnex>();
		conv.setAnnexes(annexes);
		// zero annexes
		assertFalse("zero annexes", conv.hasAttachments());
		ConveyanceAnnex annex = new ConveyanceAnnex();
		ConveyanceAnnexType annexType = new ConveyanceAnnexType();
		annexType.setConveyanceTypeId(conveyanceTypeId);
		annex.setType(annexType);
		annexes.add(annex);
		// annex with zero (default) conveyance attachment
		assertFalse("annex with no attachment", conv.hasAttachments());
		ConveyanceAttachment convAtt  = mock(ConveyanceAttachment.class);
		annex.getAttachments().add(convAtt);
		// conveyance attachments with no attachments
		assertFalse("conveyance attachments with no attachments", conv.hasAttachments());
		Attachment att = new Attachment();
		when(convAtt.getAttachment()).thenReturn(att);
		assertTrue("one conveyance att has an att", conv.hasAttachments());
		when(convAtt.getAttachment()).thenReturn(null);
		when(convAtt.getNoteTimestamp()).thenReturn("ts");
		assertTrue("when conveyance att has the timestamp set, the result should be true", conv.hasAttachments());
	}

}
