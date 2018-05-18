package org.gatlin.sdk.sinapay;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.request.member.ActivateRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardBindConfirmRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardBindRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardUnbindConfirmRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardUnbindRequest;
import org.gatlin.sdk.sinapay.request.member.QueryBalanceRequest;
import org.gatlin.sdk.sinapay.request.member.QueryWithholdRequest;
import org.gatlin.sdk.sinapay.request.member.RealnameRequest;
import org.gatlin.sdk.sinapay.request.member.WithholdRequest;
import org.gatlin.sdk.sinapay.response.BankCardBindConfirmResponse;
import org.gatlin.sdk.sinapay.response.BankCardBindResponse;
import org.gatlin.sdk.sinapay.response.BankCardUnbindResponse;
import org.gatlin.sdk.sinapay.response.QueryBalanceResponse;
import org.gatlin.sdk.sinapay.response.QueryWithholdResponse;
import org.gatlin.sdk.sinapay.response.RedirectResponse;
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
	
	@Test
	public void testBankCardBind() {
		BankCardBindRequest.Builder builder = new BankCardBindRequest.Builder();
		builder.identityId("446322135841898496");
		builder.clientIp("127.0.0.1");
		builder.bankCode("ICBC");
		builder.bankAccountNo("6212261202025453326");
		builder.province("浙江省");
		builder.city("杭州市");
		builder.phoneNo("15888837752");
		BankCardBindRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		BankCardBindResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testBankCardBindConfirm() {
		BankCardBindConfirmRequest.Builder builder = new BankCardBindConfirmRequest.Builder();
		builder.ticket("329d1f99c50c4c969b761679ae4277db");
		builder.validCode("867649");
		builder.clientIp("127.0.0.1");
		BankCardBindConfirmRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		BankCardBindConfirmResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testBankCardUnbind() { 
		BankCardUnbindRequest.Builder builder = new BankCardUnbindRequest.Builder();
		builder.identityId("446626586486112256");
		builder.clientIp("127.0.0.1");
		builder.cardId("235145");
		BankCardUnbindRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		BankCardUnbindResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
		System.out.println(response.getTicket());
	}
	
	@Test
	public void testBankCardUnbindConfirm() { 
		BankCardUnbindConfirmRequest.Builder builder = new BankCardUnbindConfirmRequest.Builder();
		builder.identityId("446626586486112256");
		builder.ticket("6e5055b1657e4cc98f0c1721cb17cb3c");
		builder.validCode("428637");
		builder.clientIp("127.0.0.1");
		BankCardUnbindConfirmRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		SinapayResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testQueryWithhold() {
		QueryWithholdRequest.Builder builder = new QueryWithholdRequest.Builder();
		builder.identityId("446626586486112256");
		QueryWithholdRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		QueryWithholdResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testWithhold() {
		WithholdRequest.Builder builder = new WithholdRequest.Builder();
		builder.identityId("446626586486112256");
		WithholdRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		RedirectResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testQueryBalance() {
		QueryBalanceRequest.Builder builder = new QueryBalanceRequest.Builder();
		builder.identityId("447061371406778368");
		builder.accountType(AccountType.SAVING_POT);
		QueryBalanceRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		QueryBalanceResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
}
