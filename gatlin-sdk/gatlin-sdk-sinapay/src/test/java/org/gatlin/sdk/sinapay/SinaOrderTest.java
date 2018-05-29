package org.gatlin.sdk.sinapay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.enums.BidType;
import org.gatlin.sdk.sinapay.bean.enums.CashdeskAddrCategory;
import org.gatlin.sdk.sinapay.bean.enums.OutTradeCode;
import org.gatlin.sdk.sinapay.bean.enums.RepayType;
import org.gatlin.sdk.sinapay.bean.model.BalancePay;
import org.gatlin.sdk.sinapay.bean.model.BindingCardPay;
import org.gatlin.sdk.sinapay.bean.model.BorrowerInfo;
import org.gatlin.sdk.sinapay.bean.model.OnlineBankPay;
import org.gatlin.sdk.sinapay.request.order.BidCreateRequest;
import org.gatlin.sdk.sinapay.request.order.DepositCollectRequest;
import org.gatlin.sdk.sinapay.request.order.DepositPayRequest;
import org.gatlin.sdk.sinapay.request.order.DepositRechargeRequest;
import org.gatlin.sdk.sinapay.request.order.DepositWithdrawRequest;
import org.gatlin.sdk.sinapay.request.order.PayToCardRequest;
import org.gatlin.sdk.sinapay.response.BidCreateResponse;
import org.gatlin.sdk.sinapay.response.DepositRechargeResponse;
import org.gatlin.sdk.sinapay.response.DepositResponse;
import org.gatlin.sdk.sinapay.response.DepositWithdrawResponse;
import org.gatlin.sdk.sinapay.response.PayToCardResponse;
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
	
	@Test
	public void testBidCreate() {
		BidCreateRequest.Builder builder = new BidCreateRequest.Builder();
		builder.outBidNo(IDWorker.INSTANCE.nextSid());
		builder.webSiteName("微钱进");
		builder.bidName("sssswwswwsdsssdw");
		builder.bidType(BidType.CREDIT);
		builder.bidAmount(BigDecimal.valueOf(200));
		builder.bidYearRate(BigDecimal.ZERO);
		builder.bidDuration(10);
		builder.repayType(RepayType.AVERAGE_CAPITAL);
		builder.beginDate("20180804122323");
		builder.term("20180504122323");
		builder.guaranteeMethod("企业担保");
		List<BorrowerInfo> borrowers = new ArrayList<BorrowerInfo>();
		borrowers.add(new BorrowerInfo("447061371406778368", "13105716369", "ssss", BigDecimal.valueOf(100)));
		borrowers.add(new BorrowerInfo("430836763745845248", "13105716369", "ssss", BigDecimal.valueOf(100)));
		builder.borrowerInfoList(borrowers);
		BidCreateRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		BidCreateResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testPayToCard() {
		PayToCardRequest.Builder builder = new PayToCardRequest.Builder();
		builder.outTradeNo(IDWorker.INSTANCE.nextSid());
		BindingCardPay pay = new BindingCardPay();
		pay.setCardId("235287");
		pay.setSinaId("447061371406778368");
		builder.collectMethod(pay);
		builder.amount(BigDecimal.valueOf(2));
		builder.summary("放款");
		builder.goodsId("450950292763049984");
//		builder.setNotifyUrl(configService.get(WqjConsts.GlobalKeys.URL_NOTICE_LOANOUT_SINA));
		builder.userIp("127.0.0.1");
		PayToCardRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		PayToCardResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
}
