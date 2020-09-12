package com.prestaging.fulvila.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendMail {
	private MailSender mailSender;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(Mail mail) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(mail.getToMail());
		message.setSubject(mail.getSubject());
		message.setText(mail.getMailBody());
		mailSender.send(message);	
	}
}
