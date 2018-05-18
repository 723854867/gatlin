package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.bean.enums.CashdeskAddrCategory;
import org.gatlin.sdk.sinapay.response.RedirectResponse;

public class WithholdRequest extends MemberRequest<RedirectResponse, WithholdRequest> {

	public static class Builder extends MemberRequest.Builder<RedirectResponse, WithholdRequest, Builder> {

		private static final long serialVersionUID = -7362948306151906595L;

		public Builder() {
			super("handle_withhold_authority");
			quota("++");
			dayQuota("++");
			authTypeWhitelist("ACCOUNT");
			cashdeskAddrCategory(CashdeskAddrCategory.MOBILE);
		}
		
		public Builder quota(String quota) {
			this.params.put("quota", quota);
			return this;
		}
		
		public Builder dayQuota(String dayQuota) {
			this.params.put("day_quota", dayQuota);
			return this;
		}
		
		// 以逗号分隔，期望的授权类型，目前可用的授权类型 ALL:银行卡授权(该授权包含账户授权) ACCOUNT,账户授权
		public Builder authTypeWhitelist(String authTypeWhitelist) {
			this.params.put("auth_type_whitelist", authTypeWhitelist);
			return this;
		}
		
		// 收银台地址类型，目前只包含MOBILE。为空时默认返回PC版页面，当传值为“MOBILE”时返回移动版页面。
		public Builder cashdeskAddrCategory(CashdeskAddrCategory cashdeskAddrCategory) {
			this.params.put("cashdesk_addr_category", cashdeskAddrCategory.name());
			return this;
		}
	}
}
