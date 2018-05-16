package org.gatlin.sdk.sinapay;

import org.gatlin.sdk.sinapay.request.member.ActivateRequest;
import org.gatlin.sdk.sinapay.request.member.RealnameRequest;
import org.gatlin.sdk.sinapay.response.SinapayResponse;
import org.gatlin.util.IDWorker;
import org.gatlin.util.serial.SerializeUtil;
import org.junit.Test;

public class SinaMemberTest extends SinaTest {
	
	String identity = "445948416376176640";

	@Test
	public void testActivate() {
		ActivateRequest.Builder builder = new ActivateRequest.Builder();
		builder.clientIp("127.0.0.1");
		String id = IDWorker.INSTANCE.nextSid();
		System.out.println(id);
		builder.identityId(id);
		ActivateRequest request = builder.build();
		SinapayResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	/**
	 * 测试环境：
	 * 身份证号首位偶数位-失败
	 * 身份证号首位奇数位-成功
	 * 身份证号或姓名漏传-失败
	 */
	@Test
	public void testRealname() {
		RealnameRequest.Builder builder = new RealnameRequest.Builder();
		builder.identityId("446322135841898496");
		builder.clientIp("127.0.0.1");
		builder.certNo("330602198704222516");
		builder.realname("樊水东");
		RealnameRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		SinapayResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
}
