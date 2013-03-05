package org.martinlaw.test;

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
		assertNotNull(hibernateTemplate.find("from court_case_event"));
		assertNotNull(hibernateTemplate.find("from ConveyanceAnnex"));
		assertNotNull(hibernateTemplate.find("from EventType"));
	}
}
