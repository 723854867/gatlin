package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.bean.enums.CashdeskAddrCategory;
import org.gatlin.sdk.sinapay.response.RedirectResponse;

/**
 * 设置支付密码
 * 企业设置设置支付密码的委托扣款在正式环境不可以使用
 * 
 * @author lynn
 */
public class PwdSetRequest extends MemberRequest<RedirectResponse, PwdSetRequest> {

	public static class Builder extends MemberRequest.Builder<RedirectResponse, PwdSetRequest, Builder> {

		private static final long serialVersionUID = -6372115546048874790L;

		public Builder() {
			super("set_pay_password");
		}
		
		public Builder withholdParam(String withholdParam) { 
			this.params.put("withhold_param", withholdParam);
			return this;
		}
		
		public Builder cashdeskAddrCategory(CashdeskAddrCategory cashdeskAddrCategory) { 
			this.params.put("cashdesk_addr_category", cashdeskAddrCategory.name());
			return this;
		}
	}
}
