package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.bean.enums.OutTradeCode;
import org.gatlin.sdk.sinapay.response.QueryMiddleBalanceResponse;

/**
 * 查询中间账户
 * 
 * @author lynn
 */
public class QueryMiddleBalanceRequest extends MemberRequest<QueryMiddleBalanceResponse, QueryMiddleBalanceRequest> {

	public static class Builder extends MemberRequest.Builder<QueryMiddleBalanceResponse, QueryMiddleBalanceRequest, Builder> {

		private static final long serialVersionUID = -4738394727212079071L;

		public Builder() {
			super("query_middle_account");
			this.params.remove("identity_type");
		}
		
		// 待收投资专用户填写：1001，待收还款金专用户1002
		public Builder outTradeCode(OutTradeCode tradeCode) { 
			this.params.put("out_trade_code", tradeCode.mark());
			return this;
		}
	}
}
