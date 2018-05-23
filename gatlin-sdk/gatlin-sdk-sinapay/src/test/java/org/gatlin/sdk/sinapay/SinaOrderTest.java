package org.gatlin.sdk.sinapay;

import java.math.BigDecimal;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.enums.CashdeskAddrCategory;
import org.gatlin.sdk.sinapay.bean.enums.OutTradeCode;
import org.gatlin.sdk.sinapay.bean.model.BalancePay;
import org.gatlin.sdk.sinapay.bean.model.OnlineBankPay;
import org.gatlin.sdk.sinapay.request.order.DepositCollectRequest;
import org.gatlin.sdk.sinapay.request.order.DepositPayRequest;
import org.gatlin.sdk.sinapay.request.order.DepositRechargeRequest;
import org.gatlin.sdk.sinapay.request.order.DepositWithdrawRequest;
import org.gatlin.sdk.sinapay.response.DepositRechargeResponse;
import org.gatlin.sdk.sinapay.response.DepositResponse;
import org.gatlin.sdk.sinapay.response.DepositWithdrawResponse;
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
		builder.payerId("447061371406778368");
		builder.payerIp("127.0.0.1");
		builder.summary("充值待收");
		builder.outTradeNo(IDWorker.INSTANCE.nextSid());
		builder.outTradeCode(OutTradeCode.COLLECT_INVEST);
		BalancePay balancePay = new BalancePay();
		balancePay.setAccountType(AccountType.SAVING_POT);
		builder.paymethod(balancePay, BigDecimal.valueOf(10));
		DepositCollectRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		DepositResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testDepositPay() { 
		DepositPayRequest.Builder builder = new DepositPayRequest.Builder();
		builder.outTradeNo(IDWorker.INSTANCE.nextSid());
		builder.accountType(AccountType.SAVING_POT);
		builder.payeeIdentityId("447061371406778368");
		builder.amount(BigDecimal.valueOf(100));
		builder.summary("提现代付");
		builder.userIp("127.0.0.1");
		DepositPayRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		DepositResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testDepositWithdraw() {
		DepositWithdrawRequest.Builder builder = new DepositWithdrawRequest.Builder();
		builder.outTradeNo(IDWorker.INSTANCE.nextSid());
		builder.accountType(AccountType.SAVING_POT);
		builder.identityId("447061371406778368");
		builder.amount(BigDecimal.valueOf(100));
		builder.userFee(BigDecimal.valueOf(10));
		builder.userIp("127.0.0.1");
		builder.withdrawCloseTime("1m");
		builder.extendParam("customNotify^Y"); // 支持申请成功通知
		builder.cashdeskAddrCategory(CashdeskAddrCategory.PC);
		DepositWithdrawRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		DepositWithdrawResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
}
