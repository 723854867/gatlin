package org.gatlin.sdk.sinapay.request.order;

import java.math.BigDecimal;
import java.text.MessageFormat;

import org.gatlin.sdk.sinapay.bean.enums.CashdeskAddrCategory;
import org.gatlin.sdk.sinapay.bean.enums.MemberIdentityType;
import org.gatlin.sdk.sinapay.bean.enums.OutTradeCode;
import org.gatlin.sdk.sinapay.bean.model.PayMethod;
import org.gatlin.sdk.sinapay.response.DepositResponse;

/**
 * 托管待收：需要用户开通委托扣款
 * 
 * @author lynn
 */
public class DepositCollectRequest extends OrderRequest<DepositResponse, DepositCollectRequest> {

	public static class Builder extends OrderRequest.Builder<DepositResponse, DepositCollectRequest, Builder> {

		private static final long serialVersionUID = 2434393913094997225L;

		public Builder() {
			super("create_hosting_collect_trade");
			payerIdentityType(MemberIdentityType.UID);
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
		
		// 摘要：必须
		public Builder summary(String summary) {
			this.params.put("summary", summary);
			return this;
		}
		
		// 交易关闭时间：取值范围：1m～15d。m-分钟，h-小时，d-天 不接受小数点
		public Builder tradeCloseTime(String tradeCloseTime) {
			this.params.put("trade_close_time", tradeCloseTime);
			return this;
		}
		
		// 支付失败后，是否可以重复发起支付
		public Builder canRepayOnFailed(boolean tradeCloseTime) {
			this.params.put("can_repay_on_failed", tradeCloseTime ? "Y" : "N");
			return this;
		}
		
		// 收银台地址类型，目前只包含MOBILE。为空时默认返回PC版页面，当传值为“MOBILE”时返回移动版页面。
		public Builder cashdeskAddrCategory(CashdeskAddrCategory cashdeskAddrCategory) {
			this.params.put("cashdesk_addr_category", cashdeskAddrCategory.name());
			return this;
		}
		
		// 对应 “标的录入”接口中的标的号，用来关联标的。如果非标的类资金业务，此字段可以为空
		public Builder goodsId(String goodsId) {
			this.params.put("goods_id", goodsId);
			return this;
		}
		
		// 付款用户编号：必须
		public Builder payerId(String payerId) {
			this.params.put("payer_id", payerId);
			return this;
		}
		
		// 付款用户标识类型：必须
		public Builder payerIdentityType(MemberIdentityType payerIdentityType) {
			this.params.put("payer_identity_type", payerIdentityType.name());
			return this;
		}
		
		// 付款用户IP地址：必须
		public Builder payerIp(String payerIp) {
			this.params.put("payer_ip", payerIp);
			return this;
		}
		
		// 必须
		public Builder paymethod(PayMethod payMethod, BigDecimal amount) {
			this.params.put("amount", amount.toString());
			this.params.put("pay_method", MessageFormat.format(payMethod.toString(), amount.toString()));
			return this;
		}
		
		// 1、代收冻结交易传pre_auth，其它场景不传该参数。2、代收冻结用户的账户余额；比如：用户的投资操作，商户想先冻结用户存钱罐的份额（代收冻结），保证用户的存钱罐余额在冻结期间仍然享受货基收益
		public Builder collectTradeType(String collectTradeType) {
			this.params.put("collect_tradeType", collectTradeType);
			return this;
		}
	}
}
