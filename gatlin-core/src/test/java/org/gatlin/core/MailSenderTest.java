package org.gatlin.core;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.gatlin.core.service.mail.MailSender;
import org.junit.Test;

public class MailSenderTest extends CoreTest {
	
	@Resource
	private MailSender mailSender;

	@Test
	public void testSend() {
		mailSender.sendMail("723854867@qq.com", "测试", "hello");
	}
	
	@Test
	public void testSendAttach() throws Exception {
		File file = new File("E:/test.xlsx");
		mailSender.sendMail("723854867@qq.com", "测试", "测试", file);
		TimeUnit.HOURS.sleep(1);
	}
	
	@Test
	public void testSendAttachStream() throws Exception {
		File file = new File("E:/test.xlsx");
		mailSender.sendMail("723854867@qq.com", "测试", "测试", "test.xslx", new FileInputStream(file));
		TimeUnit.HOURS.sleep(1);
	}
}
