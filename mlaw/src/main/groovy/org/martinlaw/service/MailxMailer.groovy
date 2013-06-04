/**
 * 
 */
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

import javax.mail.MessagingException;

import org.kuali.rice.core.api.mail.EmailBcList;
import org.kuali.rice.core.api.mail.EmailBody;
import org.kuali.rice.core.api.mail.EmailCcList;
import org.kuali.rice.core.api.mail.EmailFrom;
import org.kuali.rice.core.api.mail.EmailSubject;
import org.kuali.rice.core.api.mail.EmailTo;
import org.kuali.rice.core.api.mail.EmailToList;
import org.kuali.rice.core.api.mail.MailMessage;
import org.kuali.rice.core.api.mail.Mailer;
import org.apache.log4j.Logger;

/**
 * use mailx program to send mail
 * mailx [-s subject] [-a attachment ] [-c cc-addr] [-b bcc-addr] [-r from-addr] to-addr
 * @author mugo
 *
 */
public class MailxMailer implements Mailer {
	def Logger log = Logger.getLogger(getClass())
	def mailxPath = "mailx"
	def catPath = "cat"
	
	/**
	 * @return the location of the mailx binary
	 */
	public String getMailxPath() {
		return mailxPath;
	}
	
	public void setMailxPath(String path) {
		this.mailxPath = path
	}
	
	/**
	 * @return the location of the cat binary
	 */
	public String getCatPath() {
		return catPath;
	}
	
	public void setCatPath(String path) {
		this.catPath = path
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.rice.core.api.mail.Mailer#sendEmail(org.kuali.rice.core.api.mail.MailMessage)
	 */
	@Override
	public void sendEmail(MailMessage message) throws MessagingException {
		def cmd1 = getMailxCommandPrefix(getTempMessageFile(message.getMessage(), false));
		def cmd2 = getMailxCommand(message);
		executeMailx(cmd1 + cmd2);
	}

	/**
	 * execute the command
	 * @param cmd - the command to run
	 * @return
	 */
	protected executeMailx(String cmd) {
		log.info("sending msg: " + cmd);
		def process = cmd.execute();
		log.info("sent message output: '" + process.getText() + "'. Exit value: " + process.exitValue())
	}
	
	/**
	 * return the path to a temp file with the message contents
	 * @param message
	 * @return
	 */
	public String getTempMessageFile(String message, boolean isHtml) {
		def extn = (isHtml ? ".html" : ".txt")
		def msgFile = File.createTempFile("mlaw-msg", extn);
		msgFile << message;
		return msgFile.getCanonicalFile();
	}
	
	/**
	 * get the mailx command
	 * @param message - the email being sent
	 * @param tempFilePath - path to the temp file holding the email body contents
	 * @return
	 */
	public String getMailxCommand(MailMessage message) {
		def cmd = new StringBuffer();
		cmd.append("-s ")
		cmd.append("'" + message.getSubject() + "' ")
		if (! message.getCcAddresses().empty) {
			cmd.append("-c ");
			cmd.append(message.getCcAddresses().join(","));
			cmd.append(" ");
		}
		if (! message.getBccAddresses().empty) {
			cmd.append("-b ");
			cmd.append(message.getBccAddresses().join(","));
			cmd.append(" ");
		}
		cmd.append("-r ");
		cmd.append(message.getFromAddress());
		cmd.append(" ");
		cmd.append(message.getToAddresses().join(" "));
		return cmd.toString();
	}

	/**
	 * a convenience method to generate the first part of the command that features the temporary file and pipe 
	 * @param tempFilePath - path to the temp file
	 * @param html - whether the msg is html
	 * @return
	 */
	protected String getMailxCommandPrefix(String tempFilePath, boolean html) {
		def cmd = new StringBuffer();
		if (html) {
			cmd.append(getMailxPath());
			cmd.append(" -a ")
			cmd.append(tempFilePath)
			cmd.append(" ")
		} else {
			cmd.append(getCatPath())
			cmd.append(" ");
			cmd.append(tempFilePath);
			cmd.append(" | ");
			cmd.append(getMailxPath());
			cmd.append(" ")
		}
		return cmd.toString();
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.core.api.mail.Mailer#sendEmail(org.kuali.rice.core.api.mail.EmailFrom, org.kuali.rice.core.api.mail.EmailTo, org.kuali.rice.core.api.mail.EmailSubject, org.kuali.rice.core.api.mail.EmailBody, boolean)
	 */
	@Override
	public void sendEmail(EmailFrom from, EmailTo to, EmailSubject subject,
			EmailBody body, boolean htmlMessage) {
		def cmd1 = getMailxCommandPrefix(getTempMessageFile(body.getBody()), htmlMessage);
		MailMessage message = createMailMessage(from, subject, body, to);
		def cmd2 = getMailxCommand(message);
		executeMailx(cmd1 + cmd2)
	}

	/**
	 * convert the params into a MailMessage for further common processing
	 * @param from
	 * @param subject
	 * @param body
	 * @param to
	 * @return
	 */
	protected MailMessage createMailMessage(EmailFrom from, EmailSubject subject, EmailBody body, EmailTo to) {
		def MailMessage message = new MailMessage();
		message.setFromAddress(from.getFromAddress());
		message.setSubject(subject.getSubject());
		message.setMessage(body.getBody());
		message.addToAddress(to.getToAddress())
		return message
	}
	
	/**
	 * get the mailx command
	 * @param message - the email being sent
	 * @param tempFilePath - path to the temp file holding the email body contents
	 * @return
	 *//*
	public String getMailxCommand(EmailFrom from, EmailTo to, EmailSubject subject) {
		def cmd = new StringBuffer();
		cmd.append("-s ")
		cmd.append("'" + subject.getSubject() + "' ")
		cmd.append("-r ");
		cmd.append(from.getFromAddress());
		cmd.append(" ");
		cmd.append(to.getToAddress());
		return cmd.toString();
	}
	*/
	/* (non-Javadoc)
	 * @see org.kuali.rice.core.api.mail.Mailer#sendEmail(org.kuali.rice.core.api.mail.EmailFrom, org.kuali.rice.core.api.mail.EmailToList, org.kuali.rice.core.api.mail.EmailSubject, org.kuali.rice.core.api.mail.EmailBody, org.kuali.rice.core.api.mail.EmailCcList, org.kuali.rice.core.api.mail.EmailBcList, boolean)
	 */
	@Override
	public void sendEmail(EmailFrom from, EmailToList to, EmailSubject subject,
			EmailBody body, EmailCcList cc, EmailBcList bc, boolean htmlMessage) {
		MailMessage message = createMailMessage(from, to, subject, body, cc, bc);
		
		def cmd1 = getMailxCommandPrefix(getTempMessageFile(message.getMessage(), htmlMessage));
		def cmd2 = getMailxCommand(message);
		executeMailx(cmd1 + cmd2);
	}
	
	/**
	 * convert the params into a MailMessage for further common processing
	 * @param from
	 * @param to
	 * @param subject
	 * @param body
	 * @param cc
	 * @param bc
	 * @return
	 */
	protected MailMessage createMailMessage(EmailFrom from, EmailToList to, EmailSubject subject, EmailBody body, EmailCcList cc, EmailBcList bc) {
		def MailMessage message = new MailMessage();
		message.setFromAddress(from.getFromAddress());
		message.getToAddresses().addAll(to.getToAddresses());
		message.setSubject(subject.getSubject());
		message.setMessage(body.getBody());
		message.getCcAddresses().addAll(cc.getCcAddresses());
		message.getBccAddresses().addAll(bc.getBcAddresses())
		return message
	}

}
