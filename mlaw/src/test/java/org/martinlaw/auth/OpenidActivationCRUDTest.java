/**
 * 
 */
package org.martinlaw.auth;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Test;
import org.martinlaw.test.MartinlawTestsBase;
import org.kuali.rice.test.BaselineTestCase;

/**
 * tests CRUD ops for {@link OpenidActivation}
 * 
 * @author mugo
 *
 */
 @BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE)
public class OpenidActivationCRUDTest extends MartinlawTestsBase {
	@Test
	/**
	 * test retrieving an object inserted via sql
	 */
	public void testRetrieve() {
		OpenidActivation activation = getBoSvc().findBySinglePrimaryKey(OpenidActivation.class, "xyz");
		verifyActivationFields(activation, "umbolatembo@wananchi.com", "http://my.openid.com/mogs", "signate1");
		assertNotNull(activation.getActivated());
	}
	
	@Test
	/**
	 * test CRUD ops
	 */
	//TODO only passes when @BaselineTestCase.BaselineMode(BaselineTestCase.Mode.NONE) is set (timestamp not null verification)
	public void testCRUD() {
		OpenidActivation activation = new OpenidActivation();
		final String destination = "test@protosoft.co.ke";
		activation.setDestination(destination);
		final String openid = "http://my.openid.net/googs";
		activation.setOpenid(openid);
		final String id = UUID.randomUUID().toString();
		activation.setId(id);
		final String entityId = "entity1";
		activation.setEntityId(entityId);
		
		getBoSvc().save(activation);
		OpenidActivation result = getBoSvc().findBySinglePrimaryKey(OpenidActivation.class, id);
		verifyActivationFields(result, destination, openid, entityId);
		
		getBoSvc().delete(result);
		assertEquals("object should have been deleted", null, getBoSvc().findBySinglePrimaryKey(OpenidActivation.class, id));
	}

	/**
	 * common method to test an open id activation object
	 * @param activation
	 * @param destination
	 * @param openid
	 * @param entityId TODO
	 */
	public void verifyActivationFields(OpenidActivation activation,
			final String destination, final String openid, String entityId) {
		assertNotNull("retrieved activation should not be null", activation);
		assertNotNull("created ts should not be null", activation.getCreated());
		assertEquals("openid differs", openid, activation.getOpenid());
		assertEquals("destination email differs", destination, activation.getDestination());
	}
}
