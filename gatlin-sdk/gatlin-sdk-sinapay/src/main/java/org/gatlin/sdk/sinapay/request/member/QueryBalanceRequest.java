package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.response.QueryBalanceResponse;

/**
 * 1.查询用户的余额，可根据不同账户类型分别查询。
 * 2.存钱罐昨日收益指的是用户昨日实际到账的收益。
 * 3.余额在特定情况下是包含冻结金额的。可用余额就是客户可以用于消费的金额。
 * 4.商户可以通过传入partnerId来查询自己平台账户余额，identity_id = partnerId，identity_type = MEMBER_ID
 * 
 * @author lynn
 */
public class QueryBalanceRequest extends MemberRequest<QueryBalanceResponse, QueryBalanceRequest> {

	public static class Builder extends MemberRequest.Builder<QueryBalanceResponse, QueryBalanceRequest, Builder> {

		private static final long serialVersionUID = 2606719035174740709L;

		public Builder() {
			super("query_balance");
		}
		
		public Builder accountType(AccountType accountType) { 
			this.params.put("account_type", accountType.name());
			return this;
		}
	}
}
