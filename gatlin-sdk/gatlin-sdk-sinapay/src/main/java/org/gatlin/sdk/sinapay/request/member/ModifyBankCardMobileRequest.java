package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.response.BankCardBindResponse;
import org.gatlin.util.IDWorker;

public class ModifyBankCardMobileRequest extends MemberRequest<BankCardBindResponse, ModifyBankCardMobileRequest> {

	public static class Builder extends MemberRequest.Builder<BankCardBindResponse, ModifyBankCardMobileRequest, Builder> {

		private static final long serialVersionUID = 581290009701743393L;

		public Builder() {
			super("change_bank_mobile");
			requestNo(IDWorker.INSTANCE.nextSid());
		}
		
		public Builder requestNo(String requestNo) {
			this.params.put("request_no", requestNo);
			return this;
		}
		
		public Builder cardId(String cardId) {
			this.params.put("card_id", cardId);
			return this;
		}
		
		// 密文
		public Builder phoneNo(String phoneNo) {
			this.params.put("phone_no", phoneNo);
			return this;
		}
	}
}
