package org.gatlin.core.service.mail;

import java.io.File;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.condition.MailCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@Conditional(MailCondition.class)
public class MailSender {

	@Resource
	private JavaMailSender javaMailSender;
	
	public MailSender() {
		System.out.println("init mail");
	}
	
	public void sendMail(String to, String subject, String text) { 
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(GatlinConfigration.get("mail.from"));
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
	}
	
	public void sendMail(String to, String subject, String text, File file) throws MessagingException { 
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(GatlinConfigration.get("mail.from"));
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);
		helper.addAttachment(file.getName(), file);
		javaMailSender.send(message);
	}
	
	public void sendMail(String to, String subject, String text, String fileName, InputStream input) throws MessagingException { 
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(GatlinConfigration.get("mail.from"));
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);
		helper.addAttachment(fileName, new InputStreamResource(input));
		javaMailSender.send(message);
	}
}
