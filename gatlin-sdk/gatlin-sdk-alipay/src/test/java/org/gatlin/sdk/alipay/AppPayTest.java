package org.gatlin.sdk.alipay;

import java.math.BigDecimal;
import java.util.Map;

import org.gatlin.sdk.alipay.request.AppPayRequest;
import org.junit.Test;

public class AppPayTest extends AlipayTest {

	@Test
	public void test() {
		AppPayRequest.Builder builder = new AppPayRequest.Builder();
		builder.subject("测试");
		builder.notifyUrl("test");
		builder.outerTradeNo("443108799541149696");
		builder.totalAmount(new BigDecimal("1.02"));
		builder.timestamp("2018-05-08 09:50:41");
		AppPayRequest pay = builder.build();
		Map<String, String> params = pay.params();
		System.out.println(SignUtil.buildQuery(params));
	}
}
