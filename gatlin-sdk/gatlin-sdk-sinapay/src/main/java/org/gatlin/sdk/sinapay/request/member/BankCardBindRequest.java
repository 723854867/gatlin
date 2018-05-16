package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.bean.enums.CardAttribute;
import org.gatlin.sdk.sinapay.bean.enums.CardType;
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
		
		public Builder clientIp(String clientIp) {
			this.params.put("client_ip", clientIp);
			return this;
		}
	}
}
