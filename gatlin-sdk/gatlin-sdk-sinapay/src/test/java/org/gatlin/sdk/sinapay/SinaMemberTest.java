package org.gatlin.sdk.sinapay;

import java.util.List;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.enums.MemberIdentityType;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.bean.model.CardTips;
import org.gatlin.sdk.sinapay.request.member.ActivateRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardBindConfirmRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardBindRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardUnbindConfirmRequest;
import org.gatlin.sdk.sinapay.request.member.BankCardUnbindRequest;
import org.gatlin.sdk.sinapay.request.member.CompanyApplyRequest;
import org.gatlin.sdk.sinapay.request.member.ModifyBankCardMobileConfirmRequest;
import org.gatlin.sdk.sinapay.request.member.ModifyBankCardMobileRequest;
import org.gatlin.sdk.sinapay.request.member.PwdResetRequest;
import org.gatlin.sdk.sinapay.request.member.PwdSetRequest;
import org.gatlin.sdk.sinapay.request.member.QueryBalanceRequest;
import org.gatlin.sdk.sinapay.request.member.QueryBankCardRequest;
import org.gatlin.sdk.sinapay.request.member.QueryMiddleBalanceRequest;
import org.gatlin.sdk.sinapay.request.member.QueryPwdSetRequest;
import org.gatlin.sdk.sinapay.request.member.QueryWithholdRequest;
import org.gatlin.sdk.sinapay.request.member.RealnameRequest;
import org.gatlin.sdk.sinapay.request.member.WithholdRequest;
import org.gatlin.sdk.sinapay.response.BankCardBindConfirmResponse;
import org.gatlin.sdk.sinapay.response.BankCardBindResponse;
import org.gatlin.sdk.sinapay.response.BankCardUnbindResponse;
import org.gatlin.sdk.sinapay.response.QueryBalanceResponse;
import org.gatlin.sdk.sinapay.response.QueryBankCardResponse;
import org.gatlin.sdk.sinapay.response.QueryMiddleBalanceResponse;
import org.gatlin.sdk.sinapay.response.QueryPwdSetResponse;
import org.gatlin.sdk.sinapay.response.QueryWithholdResponse;
import org.gatlin.sdk.sinapay.response.RedirectResponse;
import org.gatlin.sdk.sinapay.response.SinapayResponse;
import org.gatlin.util.IDWorker;
import org.gatlin.util.serial.SerializeUtil;
import org.junit.Test;

public class SinaMemberTest extends SinaTest {
	
	String identity = "445948416376176640";
	String companyIdentity = "449221449165570048";

	@Test
	public void testActivate() {
		ActivateRequest.Builder builder = new ActivateRequest.Builder();
		builder.clientIp("127.0.0.1");
		builder.memberType(MemberType.ENTERPRISE);
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
	
	@Test
	public void testQueryMerchantBalance() {
		QueryBalanceRequest.Builder builder = new QueryBalanceRequest.Builder();
		builder.identityId("200004595271");
		builder.identityType(MemberIdentityType.MEMBER_ID);
		builder.accountType(AccountType.BASIC);
		QueryBalanceRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		QueryBalanceResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testQueryBankCard() { 
		QueryBankCardRequest.Builder builder = new QueryBankCardRequest.Builder();
		builder.identityId("455666703926296576");
		QueryBankCardRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		QueryBankCardResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
		List<CardTips> cards = response.cardTips();
		for (CardTips tips : cards) {
			System.out.println(SerializeUtil.GSON.toJson(tips));
		}
	}
	
	@Test
	public void testQueryMiddle() {
		QueryMiddleBalanceRequest.Builder builder = new QueryMiddleBalanceRequest.Builder();
		QueryMiddleBalanceRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		QueryMiddleBalanceResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
		System.out.println(SerializeUtil.GSON.toJson(response.getList()));
	}
	
	@Test
	public void testCompanyApply() {
		CompanyApplyRequest.Builder builder = new CompanyApplyRequest.Builder();
		builder.auditOrderNo(IDWorker.INSTANCE.nextSid());
		builder.companyName("sdsdsdsds");
		builder.identityId("449221449165570048");
		builder.address("sdsdsds");
		builder.licenseNo("sdsdsds");
		builder.licenseAddress("ssssssss");
		builder.licenseExpireDate("20200506");
		builder.businessScope("sdsdsdsds");
		builder.telephone("13456785");
		builder.email("723854867@qq.com");
		builder.organizationNo("sd5468");
		builder.summary("hsdsdwdwdwdwdw");
		builder.legalPerson("sdsdsdsds");
		builder.certNo("33012719870603341X");
		builder.legalPersonPhone("15888837752");
		builder.bankCode("ICBC");
		builder.bankAccountNo("146464649");
		builder.province("浙江省");
		builder.city("杭州市");
		builder.bankBranch("杭州祥符支行");
		builder.clientIp("127.0.0.1");
		builder.fileName("sdsds");
		builder.digest("sdsdsdsd");
		CompanyApplyRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		SinapayResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testPwdModify() { 
		PwdResetRequest.Builder builder = new PwdResetRequest.Builder();
		builder.identityId("453951420639477760");
		PwdResetRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		RedirectResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testModifyBankCardMobile() {
		ModifyBankCardMobileRequest.Builder builder = new ModifyBankCardMobileRequest.Builder();
		builder.cardId("237465");
		builder.phoneNo("15257166511");
		builder.identityId("455666703926296576");
		ModifyBankCardMobileRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		BankCardBindResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
		System.out.println(response.getTicket());
	}
	
	@Test
	public void testModifyBankCardMobileConfirm() {
		ModifyBankCardMobileConfirmRequest.Builder builder = new ModifyBankCardMobileConfirmRequest.Builder();
		builder.validCode("532753");
		builder.ticket("24a16249088d481eae84d7117e4e2dc6");
		ModifyBankCardMobileConfirmRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		BankCardBindConfirmResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testPwdSet() {
		PwdSetRequest.Builder builder = new PwdSetRequest.Builder();
		builder.identityId("455666703926296576");
		PwdSetRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		RedirectResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response));
	}
	
	@Test
	public void testQueryPwdSet() {
		QueryPwdSetRequest.Builder builder = new QueryPwdSetRequest.Builder();
		builder.identityId("456506926222540800");
		QueryPwdSetRequest request = builder.build();
		System.out.println(SerializeUtil.GSON.toJson(request.params()));
		QueryPwdSetResponse response = request.sync();
		System.out.println(SerializeUtil.GSON.toJson(response.isPwdSet()));
	}
}
