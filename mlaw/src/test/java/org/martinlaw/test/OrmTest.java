package org.martinlaw.test;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/org/martinlaw/bo/orm-test-context.xml")
/**
 * initially just to get schema generated for the entity classes
 * later for more substantive tests when rice moves to jpa
 * @author mugo
 *
 */
public class OrmTest {
	@Resource(name="mySessionFactory")
	SessionFactory mySessionFactory;
	private HibernateTemplate hibernateTemplate;
	
	@Before
	public void setup() {
		hibernateTemplate = new HibernateTemplate(mySessionFactory);
	}
	
	@Test
	public void testRetrieve() {
		// fee
		assertNotNull(hibernateTemplate.find("from CourtCase"));
		assertNotNull(hibernateTemplate.find("from ConveyanceAnnex"));
	}
}
