package org.gatlin.sdk.ucpaas;

import org.gatlin.sdk.ucpaas.model.SmsType;
import org.gatlin.sdk.ucpaas.request.CreateSmsTemplateRequest;
import org.gatlin.sdk.ucpaas.response.CreateSmsTemplateResponse;
import org.junit.Test;

public class CreateSmsTemplateTest extends UcpassTest {

	@Test
	public void testSync() {
		CreateSmsTemplateRequest.Builder builder = new CreateSmsTemplateRequest.Builder();
		builder.autograph("微钱进").name("注册验证码").type(SmsType.CAPTCHA).content("您的注册验证码是{1}，请及时使用！");
		CreateSmsTemplateResponse response = builder.build().sync();
		System.out.println(response.getTemplateid() + " " + response.getCreateDate());
	}
}
