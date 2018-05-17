package org.gatlin.sdk.sinapay.request.member;

import org.gatlin.sdk.sinapay.response.BankCardUnbindResponse;

/**
 * 1.当用户需要删除银行卡时，可使用此服务。
 * 2.安全卡商户在用户存在安全卡的情况下，解绑卡需要客户账户余额为0的情况才可以解绑。
 * 3.非提现卡，出于安全性考虑，商户在使用解绑银行卡接口时必须采用推进方式，推进方式将发送短信至该银行卡认证手机，通过认证后方能解绑银行卡。推进后返回参数将增加参数ticket，用于后续解绑银行卡推进接口。
 * 4.提现卡无需推进。
 * 5.返回参数ticket不为空，则解绑采用推进方式，反之为不推进方式。
 * 
 * @author lynn
 */
public class BankCardUnbindRequest extends MemberRequest<BankCardUnbindResponse, BankCardUnbindRequest> {

	public static class Builder extends MemberRequest.Builder<BankCardUnbindResponse, BankCardUnbindRequest, Builder> {

		private static final long serialVersionUID = -13349582149175900L;

		public Builder() {
			super("unbinding_bank_card");
			advanceFlag("Y");
		}
		
		public Builder cardId(String cardId) {
			this.params.put("card_id", cardId);
			return this;
		}
		
		public Builder advanceFlag(String advanceFlag) {
			this.params.put("advance_flag", advanceFlag);
			return this;
		}
		
		public Builder clientIp(String clientIp) {
			this.params.put("client_ip", clientIp);
			return this;
		}
	}
}
