package org.gatlin.sdk.sinapay.request.order;

import java.math.BigDecimal;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.enums.MemberIdentityType;
import org.gatlin.sdk.sinapay.bean.enums.OutTradeCode;
import org.gatlin.sdk.sinapay.response.DepositResponse;

public class DepositPayRequest extends OrderRequest<DepositResponse, DepositPayRequest> {

	public static class Builder extends OrderRequest.Builder<DepositResponse, DepositPayRequest, Builder> {

		private static final long serialVersionUID = 4436413581852421853L;

		public Builder() {
			super("create_single_hosting_pay_trade");
			outTradeCode(OutTradeCode.PAY_LOAN);
			payeeIdentityType(MemberIdentityType.UID);
		}

		// 交易订单号：必须
		public Builder outTradeNo(String outTradeNo) {
			this.params.put("out_trade_no", outTradeNo);
			return this;
		}

		// 外部业务码：必须
		public Builder outTradeCode(OutTradeCode tradeCode) {
			this.params.put("out_trade_code", tradeCode.mark());
			return this;
		}
		
		// 收款人标识：必须
		public Builder payeeIdentityId(String payeeIdentityId) {
			this.params.put("payee_identity_id", payeeIdentityId);
			return this;
		}
		
		// 收款人标识类型：必须
		public Builder payeeIdentityType(MemberIdentityType payeeIdentityType) {
			this.params.put("payee_identity_type", payeeIdentityType.name());
			return this;
		}
		
		// 收款人账户类型：可空
		public Builder accountType(AccountType accountType) {
			this.params.put("account_type", accountType.name());
			return this;
		}
		
		// 摘要：必须
		public Builder summary(String summary) {
			this.params.put("summary", summary);
			return this;
		}
		
		// 金额：必须
		public Builder amount(BigDecimal amount) {
			this.params.put("amount", amount.toString());
			return this;
		}

		// 对应 “标的录入”接口中的标的号，用来关联标的。如果非标的类资金业务，此字段可以为空
		public Builder goodsId(String goodsId) {
			this.params.put("goods_id", goodsId);
			return this;
		}
		
		// 交易关联号
		public Builder tradeRelatedNo(String tradeRelatedNo) {
			this.params.put("trade_related_no", tradeRelatedNo);
			return this;
		}

		// 用户IP地址：必须
		public Builder userIp(String userIp) {
			this.params.put("user_ip", userIp);
			return this;
		}
		
		// 债权变动明细
		public Builder creditorInfoList(String creditorInfoList) {
			this.params.put("creditor_info_list", creditorInfoList);
			return this;
		}
		
		// 分账信息列表
		public Builder splitList(String splitList) {
			this.params.put("split_list", splitList);
			return this;
		}
	}
}
