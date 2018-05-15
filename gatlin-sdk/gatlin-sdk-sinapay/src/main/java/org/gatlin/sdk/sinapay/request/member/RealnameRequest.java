package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.bean.enums.CertType;
import org.gatlin.sdk.sinapay.response.SinapayResponse;

public class RealnameRequest extends MemberRequest<SinapayResponse, RealnameRequest> {
	
	private RealnameRequest() {}

	public static class Builder extends MemberRequest.Builder<SinapayResponse, RealnameRequest, Builder> {

		private static final long serialVersionUID = 3135017688140220L;

		public Builder() {
			super("set_real_name");
			needConfirm("Y");
			certType(CertType.IC);
		}

		public Builder realname(String realname) {
			this.params.put("real_name", realname);
			return this;
		}
		
		public Builder certType(CertType certType) {
			this.params.put("cert_type", certType.name());
			return this;
		}
		
		public Builder certNo(String certType) {
			this.params.put("cert_no", certType);
			return this;
		}
		
		public Builder needConfirm(String needConfirm) {
			this.params.put("need_confirm", needConfirm);
			return this;
		}

		public Builder clientIp(String clientIp) {
			this.params.put("client_ip", clientIp);
			return this;
		}

		@Override
		protected RealnameRequest buildReq() {
			return new RealnameRequest();
		}
	}
}
