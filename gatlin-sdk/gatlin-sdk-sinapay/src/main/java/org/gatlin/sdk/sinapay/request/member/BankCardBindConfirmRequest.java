package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.response.SinapayResponse;

public class BankCardBindConfirmRequest extends MemberRequest<SinapayResponse, BankCardBindConfirmRequest> {

	public static class Builder extends MemberRequest.Builder<SinapayResponse, BankCardBindConfirmRequest, Builder> {

		private static final long serialVersionUID = -1712612041998079633L;

		public Builder() {
			super("binding_bank_card_advance");
//			this.params.remove("identity_type");
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
