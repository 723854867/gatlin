package org.gatlin.sdk.sinapay;

import java.math.BigDecimal;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.model.OnlineBankPay;
import org.gatlin.sdk.sinapay.request.order.DepositCollectRequest;
import org.gatlin.sdk.sinapay.request.order.DepositRechargeRequest;
import org.gatlin.sdk.sinapay.response.DepositCollectResponse;
import org.gatlin.sdk.sinapay.response.DepositRechargeResponse;
import org.gatlin.util.IDWorker;
import org.gatlin.util.serial.SerializeUtil;
import org.junit.Test;

public class SinaOrderTest extends SinaTest {

	@Test
	public void testDepoistRecharge() {
		DepositRechargeRequest.Builder builder = new DepositRechargeRequest.Builder();
		builder.outTradeNo(IDWorker.INSTANCE.nextSid());
		builder.accountType(AccountType.SAVING_POT);
		builder.payMethod(new OnlineBankPay(), BigDecimal.valueOf(10));
		builder.payerIp("127.0.0.1");
		builder.identityId("446626586486112256");
		DepositRechargeRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		DepositRechargeResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testDepositCollect() {
		DepositCollectRequest.Builder builder = new DepositCollectRequest.Builder();
		builder.identityId("447061371406778368");
		DepositCollectRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		DepositCollectResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
}
