package org.gatlin.sdk.sinapay.request.order;

import java.math.BigDecimal;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.enums.CashdeskAddrCategory;
import org.gatlin.sdk.sinapay.bean.enums.MemberIdentityType;
import org.gatlin.sdk.sinapay.response.DepositWithdrawResponse;

public class DepositWithdrawRequest extends OrderRequest<DepositWithdrawResponse, DepositWithdrawRequest> {

	public static class Builder extends OrderRequest.Builder<DepositWithdrawResponse, DepositWithdrawRequest, Builder> {

		private static final long serialVersionUID = 1983478678778189850L;

		public Builder() {
			super("create_hosting_withdraw");
			identityType(MemberIdentityType.UID);
		}
		
		public Builder outTradeNo(String outTradeNo) {
			this.params.put("out_trade_no", outTradeNo);
			return this;
		}
		
		public Builder summary(String summary) {
			this.params.put("summary", summary);
			return this;
		}
		
		public Builder identityId(String identityId) {
			this.params.put("identity_id", identityId);
			return this;
		}
		
		public Builder identityType(MemberIdentityType identityType) {
			this.params.put("identity_type", identityType.name());
			return this;
		}
		
		public Builder accountType(AccountType accountType) {
			this.params.put("account_type", accountType.name());
			return this;
		}
		
		public Builder amount(BigDecimal amount) {
			this.params.put("amount", amount.toString());
			return this;
		}
		
		public Builder userFee(BigDecimal userFee) {
			this.params.put("user_fee", userFee.toString());
			return this;
		}
		
		// 走收银台可以为空
		public Builder cardId(String cardId) {
			this.params.put("card_id", cardId);
			return this;
		}
		
		// 提现方式
		public Builder withdrawMode(String withdrawMode) {
			this.params.put("withdraw_mode", withdrawMode);
			return this;
		}
		
		// 到账类型
		public Builder paytoType(String paytoType) {
			this.params.put("payto_type", paytoType);
			return this;
		}
		
		// 设置未付款出款提现交易的超时时间，一旦超时，该笔交易就会自动被关闭。取值范围：1m～15m。m-分钟，h-小时不接受小数点默认为15分钟
		public Builder withdrawCloseTime(String withdrawCloseTime) {
			this.params.put("withdraw_close_time", withdrawCloseTime);
			return this;
		}
		
		public Builder userIp(String userIp) {
			this.params.put("user_ip", userIp);
			return this;
		}
		
		public Builder cashdeskAddrCategory(CashdeskAddrCategory cashdeskAddrCategory) {
			this.params.put("cashdesk_addr_category", cashdeskAddrCategory.name());
			return this;
		}
		
		public Builder extendParam(String extendParam) {
			this.params.put("extend_param", extendParam);
			return this;
		}
	}
}
