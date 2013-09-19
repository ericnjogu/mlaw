/**
 * 
 */
package org.martinlaw.test;

import org.junit.Test;
import org.martinlaw.bo.MatterClient;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * bo tests for {@link MatterClient}
 * @author mugo
 *
 */
public class MatterClientBOTest extends MartinlawTestsBase {
	/**
	 * tests CRUD ops
	 */
	@Test
	public void testMatterClientCRUD() {
		MatterClient person = new MatterClient();
		person.setMatterId(1001l);
		testMartinlawPersonCRUD(MatterClient.class, "client1", person);
	}

	/**
	 * tests that required fields are validated by the database
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testCaseMatterClientNullableFields() {
		MatterClient caseClient = new MatterClient();
		getBoSvc().save(caseClient);
	}

	/**
	 * tests data dictionary setup - presence, inquiry, lookup
	 */
	@Test
	public void testMatterClientAttributes() {
		testBoAttributesPresent(MatterClient.class.getCanonicalName());
		Class<MatterClient> dataObjectClass = MatterClient.class;
		verifyInquiryLookup(dataObjectClass);
	}

}
