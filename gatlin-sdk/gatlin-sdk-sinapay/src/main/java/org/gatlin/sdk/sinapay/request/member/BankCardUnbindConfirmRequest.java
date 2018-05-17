package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.response.SinapayResponse;

public class BankCardUnbindConfirmRequest extends MemberRequest<SinapayResponse, BankCardUnbindConfirmRequest> {

	public static class Builder extends MemberRequest.Builder<SinapayResponse, BankCardUnbindConfirmRequest, Builder> {

		private static final long serialVersionUID = -6798075253077859938L;

		public Builder() {
			super("unbinding_bank_card_advance");
		}
		
		public Builder ticket(String ticket) {
			this.params.put("ticket", ticket);
			return this;
		}
		
		public Builder validCode(String validCode) {
			this.params.put("valid_code", validCode);
			return this;
		}
		
		public Builder clientIp(String clientIp) {
			this.params.put("client_ip", clientIp);
			return this;
		}
	}
}
