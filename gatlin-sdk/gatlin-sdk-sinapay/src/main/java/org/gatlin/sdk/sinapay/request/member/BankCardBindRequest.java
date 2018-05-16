package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.bean.enums.CardAttribute;
import org.gatlin.sdk.sinapay.bean.enums.CardType;
import org.gatlin.sdk.sinapay.bean.enums.CardVerifyMode;
import org.gatlin.sdk.sinapay.bean.enums.CertType;
import org.gatlin.sdk.sinapay.response.SinapayResponse;

public class BankCardBindRequest extends MemberRequest<SinapayResponse, BankCardBindRequest> {

	public static class Builder extends MemberRequest.Builder<SinapayResponse, BankCardBindRequest, Builder> {

		private static final long serialVersionUID = 3135017688140220L;
		
		public Builder() {
			super("binding_bank_card");
		}
		
		public Builder requestNo(String requestNo) {
			this.params.put("request_no", requestNo);
			return this;
		}
		
		// 银行编号
		public Builder bankCode(String bankCode) {
			this.params.put("bank_code", bankCode);
			return this;
		}
		
		// 银行卡号:密文
		public Builder bankAccountNo(String bankAccountNo) {
			this.params.put("bank_account_no", bankAccountNo);
			return this;
		}
		
		// 户名：密文，空则使用实名认证时的信息
		public Builder accountName(String accountName) {
			this.params.put("account_name", accountName);
			return this;
		}
		
		// 卡类型
		public Builder cardType(CardType cardType) {
			this.params.put("card_type", cardType.name());
			return this;
		}
		
		// 卡属性
		public Builder cardAttribute(CardAttribute attribute) {
			this.params.put("card_attribute", attribute.name());
			return this;
		}
		
		// 证件类型
		public Builder certType(CertType certType) {
			this.params.put("cert_type", certType.name());
			return this;
		}
		
		// 证件号码：密文，空则使用实名认证时实名信息
		public Builder certNo(String certNo) {
			this.params.put("cert_no", certNo);
			return this;
		}
		
		// 银行预留手机号：密文，如果认证方式不为空，则此信息也不能为空
		public Builder phoneNo(String phoneNo) {
			this.params.put("phone_no", phoneNo);
			return this;
		}
		
		// 有效期：密文(月份/年份 —— 10/13 表示2013年10月)
		public Builder validityPeriod(String validityPeriod) {
			this.params.put("validity_period", validityPeriod);
			return this;
		}
		
		// 信用卡专用：密文
		public Builder verificationValue(String verificationValue) {
			this.params.put("verification_value", verificationValue);
			return this;
		}
		
		// 省份
		public Builder province(String province) {
			this.params.put("province", province);
			return this;
		}
		
		// 城市
		public Builder city(String city) {
			this.params.put("city", city);
			return this;
		}
		
		// 支行名称
		public Builder brankBranch(String brankBranch) {
			this.params.put("brank_branch", brankBranch);
			return this;
		}
		
		// 认证方式
		public Builder verifyMode(CardVerifyMode verifyMode) {
			this.params.put("verify_mode", verifyMode.name());
			return this;
		}
		
		public Builder clientIp(String clientIp) {
			this.params.put("client_ip", clientIp);
			return this;
		}
	}
}
