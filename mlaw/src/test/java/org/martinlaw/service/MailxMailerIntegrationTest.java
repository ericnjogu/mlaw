package org.martinlaw.service;

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

import org.junit.Test;
import org.kuali.rice.core.api.mail.Mailer;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.martinlaw.test.MartinlawTestsBase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * tests whether {@link org.martinlaw.service.MailxMailer} is overridden
 * <p>
 * to pass, add an entry of &lt;param name="rice.core.additionalSpringFiles"&gt;classpath:org/martinlaw/rice-overrides/core.xml&lt;/param&gt;
 * in the config file</p> 
 * @author mugo
 *
 */
public class MailxMailerIntegrationTest extends MartinlawTestsBase {
	@Test
	public void testOverridingConfig() {
		Mailer mailer = GlobalResourceLoader.getService("mailer");
		assertTrue("mailx mailer not available", mailer instanceof MailxMailer);
		final String message = "path differs";
		assertEquals(message, "/usr/bin/mailx", ((MailxMailer)mailer).getMailxPath());
		assertEquals(message, "/bin/cat", ((MailxMailer)mailer).getCatPath());
	}
}