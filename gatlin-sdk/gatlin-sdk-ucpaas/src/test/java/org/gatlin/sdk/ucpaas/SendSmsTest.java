package org.gatlin.sdk.ucpaas;

import java.io.IOException;

import org.gatlin.sdk.ucpaas.request.SendSmsRequest;
import org.gatlin.sdk.ucpaas.response.SendSmsResponse;
import org.gatlin.util.serial.SerializeUtil;
import org.junit.Test;

public class SendSmsTest extends UcpassTest {

	@Test
	public void testSync() throws IOException {
		SendSmsRequest.Builder builder = new SendSmsRequest.Builder();
		builder.templateId("305945").params("123456").mobile("158s88837752");
		SendSmsRequest request = builder.build();
		SendSmsResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
}
