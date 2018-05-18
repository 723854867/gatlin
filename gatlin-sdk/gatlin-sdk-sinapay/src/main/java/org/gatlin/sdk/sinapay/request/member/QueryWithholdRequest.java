package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.bean.enums.AuthSubType;
import org.gatlin.sdk.sinapay.bean.enums.AuthType;
import org.gatlin.sdk.sinapay.response.QueryWithholdResponse;

public class QueryWithholdRequest extends MemberRequest<QueryWithholdResponse, QueryWithholdRequest> {

	public static class Builder extends MemberRequest.Builder<QueryWithholdResponse, QueryWithholdRequest, Builder> {

		private static final long serialVersionUID = 211462229804390364L;

		public Builder() {
			super("query_withhold_authority");
			isDetaildisp(false);
			authType(AuthType.ACCOUNT);
		}
		
		public Builder authType(AuthType type) {
			this.params.put("auth_type", type.name());
			return this;
		}
		
		public Builder authSubType(AuthSubType subType) {
			this.params.put("auth_sub_type", subType.name());
			return this;
		}
		
		public Builder isDetaildisp(boolean isDetaildisp) {
			this.params.put("is_detail_disp", isDetaildisp ? "Y" : "N");
			return this;
		}
	}
}
