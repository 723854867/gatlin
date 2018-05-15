package org.gatlin.sdk.sinapay;

import org.gatlin.sdk.sinapay.request.member.MemberActivateRequest;
import org.gatlin.sdk.sinapay.response.SinapayResponse;
import org.gatlin.util.IDWorker;
import org.gatlin.util.serial.SerializeUtil;
import org.junit.Test;

public class SinaMemberTest extends SinaTest {

	@Test
	public void testActivate() {
		MemberActivateRequest.Builder builder = new MemberActivateRequest.Builder();
		builder.clientIp("127.0.0.1");
		String id = IDWorker.INSTANCE.nextSid();
		System.out.println(id);
		builder.identityId(id);
		MemberActivateRequest request = builder.build();
		SinapayResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
}
