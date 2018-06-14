package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.response.QueryPwdSetResponse;

public class QueryPwdSetRequest extends MemberRequest<QueryPwdSetResponse, QueryPwdSetRequest> {

	public static class Builder extends MemberRequest.Builder<QueryPwdSetResponse, QueryPwdSetRequest, Builder> {

		private static final long serialVersionUID = 7349259638699899118L;

		public Builder() {
			super("query_is_set_pay_password");
		}
	}
}
