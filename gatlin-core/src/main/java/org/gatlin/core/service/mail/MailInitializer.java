package org.gatlin.core.service.mail;

import java.util.Properties;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.condition.MailCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@Conditional(MailCondition.class)
public class MailInitializer {

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(GatlinConfigration.get("mail.host"));
		sender.setPort(GatlinConfigration.get("mail.port", Integer.class));
		sender.setProtocol(GatlinConfigration.get("mail.protocol", "smtp"));
		sender.setUsername(GatlinConfigration.get("mail.username"));
		sender.setPassword(GatlinConfigration.get("mail.password"));
		sender.setDefaultEncoding(GatlinConfigration.get("mail.encoding", "UTF-8"));
		Properties properties = GatlinConfigration.getProperties("classpath:conf/mail.properties");
		sender.setJavaMailProperties(properties);
		return sender;
	}
}
