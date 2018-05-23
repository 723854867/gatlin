package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.response.QueryBankCardResponse;

public class QueryBankCardRequest extends MemberRequest<QueryBankCardResponse, QueryBankCardRequest> {

	public static class Builder extends MemberRequest.Builder<QueryBankCardResponse, QueryBankCardRequest, Builder> {

		private static final long serialVersionUID = -2980680231698755342L;

		public Builder() {
			super("query_bank_card");
		}
		
		// 卡ID：可空
		public Builder cardId(String cardId) { 
			this.params.put("card_id", cardId);
			return this;
		}
	}
}
