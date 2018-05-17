package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.response.BankCardUnbindResponse;

public class BankCardUnbindRequest extends MemberRequest<BankCardUnbindResponse, BankCardUnbindRequest> {

	public static class Builder extends MemberRequest.Builder<BankCardUnbindResponse, BankCardUnbindRequest, Builder> {

		private static final long serialVersionUID = -13349582149175900L;

		public Builder() {
			super("unbinding_bank_card");
			advanceFlag("Y");
		}
		
		public Builder cardId(String cardId) {
			this.params.put("card_id", cardId);
			return this;
		}
		
		public Builder advanceFlag(String advanceFlag) {
			this.params.put("advance_flag", advanceFlag);
			return this;
		}
		
		public Builder clientIp(String clientIp) {
			this.params.put("client_ip", clientIp);
			return this;
		}
	}
}
