package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.response.BankCardBindConfirmResponse;

public class ModifyBankCardMobileConfirmRequest extends MemberRequest<BankCardBindConfirmResponse, ModifyBankCardMobileConfirmRequest> {

	public static class Builder extends MemberRequest.Builder<BankCardBindConfirmResponse, ModifyBankCardMobileConfirmRequest, Builder> {

		private static final long serialVersionUID = 172454681878675300L;

		public Builder() {
			super("change_bank_mobile_advance");
			this.params.remove("identity_type");
		}
		
		public Builder ticket(String ticket) {
			this.params.put("ticket", ticket);
			return this;
		}
		
		public Builder validCode(String validCode) {
			this.params.put("valid_code", validCode);
			return this;
		}
	}
}
