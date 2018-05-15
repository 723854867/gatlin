package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.SinapayConfig;
import org.gatlin.sdk.sinapay.bean.enums.MemberIdentityType;
import org.gatlin.sdk.sinapay.request.SinapayRequest;
import org.gatlin.sdk.sinapay.response.SinapayResponse;

/**
 * 会员网关
 * 
 * @author lynn
 *
 * @param <RESPONSE>
 * @param <REQUEST>
 */
class MemberRequest<RESPONSE extends SinapayResponse, REQUEST extends SinapayRequest<RESPONSE, REQUEST>> extends SinapayRequest<RESPONSE, REQUEST> {

	protected MemberRequest() {
		super(SinapayConfig.memberHost(), SinapayConfig.memberPath());
	}
	
	@SuppressWarnings("unchecked")
	public abstract static class Builder<RESPONSE extends SinapayResponse, REQUEST extends MemberRequest<RESPONSE, REQUEST>, BUILDER extends Builder<RESPONSE, REQUEST, BUILDER>> extends SinapayRequest.Builder<RESPONSE, REQUEST, BUILDER> {
		
		private static final long serialVersionUID = -4775652927768932559L;
		
		protected Builder(String service) {
			super(service);
			identityType(MemberIdentityType.UID);
		}
		
		public BUILDER identityId(String identityId) {
			this.params.put("identity_id", identityId);
			return (BUILDER) this;
		}
		
		public BUILDER identityType(MemberIdentityType identityType) {
			this.params.put("identity_type", identityType.name());
			return (BUILDER) this;
		}
		
		public BUILDER extendParam(String extendParam) {
			this.params.put("extend_param", extendParam);
			return (BUILDER) this;
		}
	}
}
