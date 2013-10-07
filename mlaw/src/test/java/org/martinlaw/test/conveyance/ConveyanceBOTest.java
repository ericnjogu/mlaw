/**
 * 
 */
package org.martinlaw.test.conveyance;

/*
 * #%L
 * mlaw
 * %%
 * Copyright (C) 2012,2013 Eric Njogu (kunadawa@gmail.com)
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.martinlaw.bo.MatterClient;
import org.martinlaw.bo.conveyance.Conveyance;
import org.martinlaw.test.MartinlawTestsBase;

/**
 * various tests for {@link Conveyance}
 * @author mugo
 *
 */
public class ConveyanceBOTest extends MartinlawTestsBase {
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(getClass());
	@Test
	/**
	 * test CRUD ops on {@link Conveyance}
	 */
	public void testConveyanceCRUD() {
		// C
		Conveyance conv = getTestUtils().getTestConveyance();
		/*try {
			conv.getConsiderations().add((Consideration) getTestUtils().getTestConsideration(Consideration.class));
		} catch (Exception e) {
			fail("could not add consideration");
			log.error(e);
		}*/
		// add client
		MatterClient client = new MatterClient();
		String principalName = "clientX";
		client.setPrincipalName(principalName);
		conv.getClients().add(client);

		getBoSvc().save(conv);
		
		// R
		conv.refresh();

		assertEquals("conveyance name differs", getTestUtils().getTestConveyance().getName(), conv.getName());
		assertEquals("number of clients differs", 1, conv.getClients().size());
		assertEquals("principal name differs", principalName, conv.getClients().get(0).getPrincipalName());
		assertNotNull("matter type should not be null", conv.getType());
		assertEquals("matter type name differs", "Sale of Motor Vehicle", conv.getType().getName());
	
		getTestUtils().testConsiderationFields(conv.getConsiderations().get(0));
		assertNotNull("considerations should not be null", conv.getConsiderations());
		assertEquals("default number of considerations differs", 2, conv.getConsiderations().size());
		getTestUtils().testMatterClient(conv, getTestUtils().getTestClientFirstName());
		// U
		String name2 = "EN/C010";
		conv.setName(name2);

		conv.refresh();
		assertEquals(name2, conv.getName());
		
		// D
		getBoSvc().delete(conv);
		assertNull("client should have been deleted", 
				getBoSvc().findBySinglePrimaryKey(MatterClient.class, conv.getClients().get(0).getId()));
		/*assertNull("conveyance fee should have been deleted", 
				getBoSvc().findBySinglePrimaryKey(Transaction.class, conv.getFees().get(0).getId()));*/
	}
	
	@Test
	/**
	 * test retrieving the {@link Conveyance} populated from sql
	 */
	public void testConveyanceRetrieve() {
		Conveyance conv = getBoSvc().findBySinglePrimaryKey(Conveyance.class, 1008l);
		assertNotNull(conv);
		assertEquals("Sale of LR4589", conv.getName());
		assertEquals("c1", conv.getLocalReference());
		assertEquals("Sale of Urban Land", conv.getType().getName());
		assertEquals("pending", conv.getStatus().getStatus());
		// clients
		assertEquals("number of clients differs", 2, conv.getClients().size());
		assertEquals("client name differs", "client1", conv.getClients().get(0).getPrincipalName());
		// assignees
		assertEquals("number of clients differs", 1, conv.getAssignees().size());
		assertEquals("assignee principal name differs", "edwin_njogu", conv.getAssignees().get(0).getPrincipalName());
		//type
		assertNotNull("matter type should not be null", conv.getType());
		assertEquals("matter type name differs", "Sale of Urban Land", conv.getType().getName());
		
		getTestUtils().testWorkList(conv.getWork());
		
		getTestUtils().testRetrievedConsiderationFields(conv.getConsiderations().get(0));
		getTestUtils().testMatterClient(conv, "Client");
	}
	
	@Test
	/**
	 * test that {@link Conveyance} is loaded into the data dictionary
	 */
	public void testConveyanceAttributes() {
		testBoAttributesPresent(Conveyance.class.getCanonicalName());
		
		Class<Conveyance> dataObjectClass = Conveyance.class;
		verifyMaintDocDataDictEntries(dataObjectClass);
	}
}
