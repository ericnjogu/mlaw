/**
 * 
 */
package org.martinlaw.service

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

import org.junit.Test
import static org.junit.Assert.assertEquals;
import org.junit.Before
import org.kuali.rice.core.api.mail.EmailBcList
import org.kuali.rice.core.api.mail.EmailBody
import org.kuali.rice.core.api.mail.EmailCcList
import org.kuali.rice.core.api.mail.EmailFrom
import org.kuali.rice.core.api.mail.EmailSubject
import org.kuali.rice.core.api.mail.EmailTo
import org.kuali.rice.core.api.mail.EmailToList
import org.kuali.rice.core.api.mail.MailMessage;

import groovy.util.GroovyTestCase

/**
 * tests methods in {@link org.martinlaw.service.MailxMailer}
 * @author mugo
 *
 */
class MailxMailerTest {
	def MailxMailer mailer;
	@Before
	public void setup() {
		mailer = new MailxMailer();
	}
	
	@Test
	/**
	 * tests {@link org.martinlaw.service.MailxMailer#getTempMessageFile()}
	 */
	public void testGetTempMessageFile() {
		def msg = "Habari\nKwaheri"
		def path = mailer.getTempMessageFile(msg, true)
		assert new File(path).text == msg
		assert path.endsWith(".html");
		path = mailer.getTempMessageFile(msg, false)
		assert path.endsWith(".txt");
	}
	
	/**
	 * tests {@link org.martinlaw.service.MailxMailer#getMailxCommand(MailMessage)}
	 */
	@Test
	public void testGetMailxCommand() {
		MailMessage msg = new MailMessage();
		def from = "mogs@protosoft"
		msg.setFromAddress(from)
		def to1 = "alice@protosoft"
		msg.addToAddress(to1)
		def to2 = "signate@protosoft"
		msg.addToAddress(to2)
		def subj = "sema mambo"
		msg.setSubject(subj);
		def body = "habari gani?"
		msg.setMessage(body)
		def expectedCmd = "-s '" + subj + "' -r " + from + " " + to1 + " " + to2
		assert expectedCmd == mailer.getMailxCommand(msg)
		
		def cc1 = "em@protosoft"
		msg.addCcAddress(cc1);
		expectedCmd = "-s '" + subj + "' -c " + cc1 + " -r " + from + " " + to1 + " " + to2
		assertEquals("mailx command differs", expectedCmd,  mailer.getMailxCommand(msg));
		
		def cc2 = "paula@protosoft"
		msg.addCcAddress(cc2);
		expectedCmd = "-s '" + subj + "' -c " + cc1 + "," + cc2  + " -r " + from + " " + to1 + " " + to2
		assertEquals("mailx command differs", expectedCmd,  mailer.getMailxCommand(msg));
		
		def bcc = "edmon@protosoft"
		msg.addBccAddress(bcc);
		expectedCmd = "-s '" + subj + "' -c " + cc1 + "," + cc2  + " -b " + bcc + " -r " + from + " " + to1 + " " + to2
		assertEquals("mailx command differs", expectedCmd,  mailer.getMailxCommand(msg));
	}
	
	/**
	 * tests {@link org.martinlaw.service.MailxMailer#getMailxCommandPrefix(String, boolean)}
	 */
	@Test
	public void testGetMailxCommandPrefix() {
		def tmpFilePath = "/path/to/file"
		def String expectedCmd = "cat " + tmpFilePath + " | mailx "
		def testLabel = "mailx command prefix differs"
		assertEquals(testLabel, expectedCmd, mailer.getMailxCommandPrefix(tmpFilePath, false))
		expectedCmd = "echo ' ' | mailx -a " + tmpFilePath + " "
		assertEquals(testLabel, expectedCmd, mailer.getMailxCommandPrefix(tmpFilePath, true))
	}
	
	/**
	 * tests {@link org.martinlaw.service.MailxMailer#createMailMessage(EmailFrom, EmailSubject, EmailBody, EmailTo)}
	 */
	@Test
	public void testCreateMailMessage_simple() {
		def from = "irene@protosoft"
		def subj = "promotion"
		def body = "habari njema"
		def to = "hellen@protosoft"
		def MailMessage msg = mailer.createMailMessage(new EmailFrom(from), 
			new EmailSubject(subj), new EmailBody(body), new EmailTo(to))
		
		assert msg.getFromAddress() == from
		assert msg.getSubject() == subj
		assert msg.getMessage() == body
		assert msg.getToAddresses().contains(to)
	}
	
	/**
	 * tests {@link org.martinlaw.service.MailxMailer.createMailMessage(EmailFrom, EmailToList, EmailSubject, EmailBody, EmailCcList, EmailBcList)}
	 */
	@Test
	public void testCreateMailMessage_detailed() {
		def from = "irene@protosoft"
		def subj = "promotion"
		def body = "habari njema"
		def to = ["hellen@protosoft", "gitau@protosoft"]
		def bc = ["wambui@protosoft", "nyawira@protosoft"]
		def MailMessage msg = mailer.createMailMessage(new EmailFrom(from), new EmailToList(to), 
			new EmailSubject(subj), new EmailBody(body), new EmailCcList(Collections.EMPTY_LIST), new EmailBcList(bc))
		
		assert msg.getFromAddress() == from
		assert msg.getSubject() == subj
		assert msg.getMessage() == body
		def testLabel = "addresses differ"
		assert msg.getToAddresses().containsAll(to)
		assert msg.getBccAddresses().containsAll(bc);
		assert msg.getCcAddresses().isEmpty()
	}
}
