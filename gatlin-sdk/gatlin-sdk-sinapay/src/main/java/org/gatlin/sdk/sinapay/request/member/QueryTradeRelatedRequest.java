package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.request.SinapayRequest;
import org.gatlin.sdk.sinapay.response.QueryTradeRelatedResponse;

public class QueryTradeRelatedRequest extends MemberRequest<QueryTradeRelatedResponse, QueryTradeRelatedRequest> {

	public static class Builder extends SinapayRequest.Builder<QueryTradeRelatedResponse, QueryTradeRelatedRequest, Builder> {

		private static final long serialVersionUID = 3135017688140220L;
		
		public Builder() {
			super("query_trade_related_no");
		}
		
		public Builder tradeRelatedNo(String tradeRelatedNo) {
			this.params.put("trade_related_no", tradeRelatedNo);
			return this;
		}
	}
}
