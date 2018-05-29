package org.gatlin.sdk.sinapay.request.order;

import java.math.BigDecimal;

import org.gatlin.sdk.sinapay.bean.enums.OutTradeCode;
import org.gatlin.sdk.sinapay.bean.enums.PaytoType;
import org.gatlin.sdk.sinapay.bean.model.PayMethod;
import org.gatlin.sdk.sinapay.response.PayToCardResponse;

/**
 * 同一个 goods_id可以放款多次，但是放款的总额不能大于goods_id的募集金额
 * 
 * @author lynn
 */
public class PayToCardRequest extends OrderRequest<PayToCardResponse, PayToCardRequest> {
	
	public static class Builder extends OrderRequest.Builder<PayToCardResponse, PayToCardRequest, Builder> {

		private static final long serialVersionUID = 4436413581852421853L;

		public Builder() {
			super("create_single_hosting_pay_to_card_trade");
			outTradeCode(OutTradeCode.PAY_LOAN);
			paytoType(PaytoType.GENERAL);
		}
		
		// 交易订单号
		public Builder outTradeNo(String outTradeNo) {
			this.params.put("out_trade_no", outTradeNo);
			return this;
		}
		
		// 交易码
		public Builder outTradeCode(OutTradeCode outTradeCode) {
			this.params.put("out_trade_code", outTradeCode.mark());
			return this;
		}
		
		// 收款方式
		public Builder collectMethod(PayMethod payMethod) {
			this.params.put("collect_method", payMethod.toString());
			return this;
		}
		
		// 金额
		public Builder amount(BigDecimal amount) {
			this.params.put("amount", amount.toString());
			return this;
		}
		
		// 摘要
		public Builder summary(String summary) {
			this.params.put("summary", summary);
			return this;
		}
		
		// 到账类型：可空
		public Builder paytoType(PaytoType paytoType) {
			this.params.put("payto_type", paytoType.name());
			return this;
		}
		
		// 扩展参数：可空
		public Builder extendParam(String extendParam) {
			this.params.put("extend_param", extendParam);
			return this;
		}
		
		// 标的号
		public Builder goodsId(String goodsId) {
			this.params.put("goods_id", goodsId);
			return this;
		}
		
		// 债权变动明细列表：可空(恒丰存管商户非空，参数间用“^”分割，条目间用“|”分割)
		public Builder creditorInfoList(String creditorInfoList) {
			this.params.put("creditor_info_list", creditorInfoList);
			return this;
		}
		
		// 用户在商户平台发起请求时候的IP地址
		public Builder userIp(String userIp) {
			this.params.put("user_ip", userIp);
			return this;
		}
	}
}
