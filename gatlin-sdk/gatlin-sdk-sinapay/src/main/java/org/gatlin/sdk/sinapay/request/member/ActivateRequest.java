package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.sdk.sinapay.response.SinapayResponse;

/**
 * 创建激活会员
 * 
 * @author lynn
 */
public class ActivateRequest extends MemberRequest<SinapayResponse, ActivateRequest> {
	
	public static class Builder extends MemberRequest.Builder<SinapayResponse, ActivateRequest, Builder> {

		private static final long serialVersionUID = 3135017688140220L;
		
		public Builder() {
			super("create_activate_member");
			memberType(MemberType.PERSONAL);
		}
		
		public Builder memberType(MemberType memberType) {
			this.params.put("member_type", String.valueOf(memberType.mark()));
			return this;
		}
		
		public Builder clientIp(String clientIp) {
			this.params.put("client_ip", clientIp);
			return this;
		}
	}
}
