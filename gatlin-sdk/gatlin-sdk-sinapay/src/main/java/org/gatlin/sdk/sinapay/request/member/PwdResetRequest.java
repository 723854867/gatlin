package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.response.RedirectResponse;

public class PwdResetRequest extends MemberRequest<RedirectResponse, PwdResetRequest> {

	public static class Builder extends MemberRequest.Builder<RedirectResponse, PwdResetRequest, Builder> {

		private static final long serialVersionUID = 6929288806277054358L;

		public Builder() {
			super("modify_pay_password");
		}
	}
}
