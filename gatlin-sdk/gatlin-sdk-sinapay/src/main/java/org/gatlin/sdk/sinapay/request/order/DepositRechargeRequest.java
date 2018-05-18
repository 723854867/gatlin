package org.gatlin.sdk.sinapay.request.order;

import java.math.BigDecimal;
import java.text.MessageFormat;

import org.gatlin.sdk.sinapay.bean.enums.AccountType;
import org.gatlin.sdk.sinapay.bean.enums.CashdeskAddrCategory;
import org.gatlin.sdk.sinapay.bean.model.PayMethod;
import org.gatlin.sdk.sinapay.response.DepositRechargeResponse;

/**
 * 托管充值
 * 1、通过此服务完成对托管用户账户的充值，充值只支持借记卡。
 * 2、由于基金公司开户时需要身份证和手机信息，因此在处理充值到存钱罐时，必须保证用户已经通过身份证认证并绑定了手机。此接口会做上述校验。
 * 3、如果超过12个小时还没有支付成功的充值订单会自动被关闭，被关闭的订单状态为FAILED，error_code为"F0012"，error_message为"订单关闭"。
 * 以下场景订单不会被关闭：
 * a.企业用户的充值订单、
 * b.充值支付方式为对公网银、确定性入款、线下充值。
 * 
 * @author lynn
 */
public class DepositRechargeRequest extends OrderRequest<DepositRechargeResponse, DepositRechargeRequest> {

	public static class Builder extends OrderRequest.Builder<DepositRechargeResponse, DepositRechargeRequest, Builder> {

		private static final long serialVersionUID = -5775478637702102106L;

		public Builder() {
			super("create_hosting_deposit");
		}
		
		// 交易订单号：必须
		public Builder outTradeNo(String outTradeNo) {
			this.params.put("out_trade_no", outTradeNo);
			return this;
		}
		
		// 摘要
		public Builder summary(String summary) {
			this.params.put("summary", summary);
			return this;
		}
		
		// 账户类型：默认基本户
		public Builder accountType(AccountType accountType) {
			this.params.put("account_type", accountType.name());
			return this;
		}
		
		// 金额:必须
		public Builder amount(BigDecimal amount) {
			this.params.put("amount", amount.toString());
			return this;
		}
		
		// 用户承担手续费
		public Builder userFee(BigDecimal userFee) {
			this.params.put("user_fee", userFee.toString());
			return this;
		}
		
		// 付款用户IP地址:必须
		public Builder payerIp(String payerIp) {
			this.params.put("payer_ip", payerIp);
			return this;
		}
		
		// 充值关闭时间，取值范围：15m～15h。m-分钟，h-小时不接受小数点
		public Builder depositCloseTime(String depositCloseTime) {
			this.params.put("deposit_close_time", depositCloseTime);
			return this;
		}
		
		// 支付方式：必须
		public Builder payMethod(PayMethod payMethod, BigDecimal amount) {
			this.params.put("amount", amount.toString());
			this.params.put("pay_method", MessageFormat.format(payMethod.toString(), amount));
			return this;
		}
		
		// 收银台地址类别：收银台地址类型，目前只包含MOBILE。为空时默认返回PC版页面，当传值为"MOBILE"时返回移动版页面。
		public Builder cashdeskAddrCategory(CashdeskAddrCategory cashdeskAddrCategory) {
			this.params.put("cashdesk_addr_category", cashdeskAddrCategory.name());
			return this;
		}
	}
}
