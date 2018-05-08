package org.gatlin.sdk.alipay.request;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import org.gatlin.sdk.alipay.AlipayConfig;
import org.gatlin.sdk.alipay.SignUtil;
import org.gatlin.sdk.alipay.bean.enums.Channel;
import org.gatlin.sdk.alipay.bean.enums.GoodsType;
import org.gatlin.sdk.alipay.bean.model.AppPayModel;
import org.gatlin.sdk.alipay.bean.model.ExtUserInfo;
import org.gatlin.sdk.alipay.response.AppPayResponse;
import org.gatlin.util.serial.SerializeUtil;

/**
 * APP支付
 * 
 * @author lynn
 */
public class AppPayRequest extends AlipayRequest<AppPayResponse, AppPayRequest> {

	public AppPayRequest(Map<String, String> params) {
		this.params = params;
	}
	
	@Override
	public AppPayResponse sync() {
		throw new UnsupportedOperationException("支付宝app充值不支持服务端调用！");
	}
	
	public static class Builder extends AlipayRequest.Builder<AppPayRequest, Builder> {

		private static final long serialVersionUID = -4885533906254982244L;
		
		protected Map<String, String> params = new TreeMap<String, String>();

		public Builder() {
			super("alipay.trade.app.pay");
			this.params.put("timeout_express", "30m");
			this.params.put("product_code", "QUICK_MSECURITY_PAY");
		}
		
		// 可选：对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
		public Builder body(String body) {
			this.params.put("body", body);
			return this;
		}
		
		// 商品的标题/交易标题/订单标题/订单关键字等。
		public Builder subject(String subject) {
			this.params.put("subject", subject);
			return this;
		}
		
		// 商户网站唯一订单号
		public Builder outerTradeNo(String outerTradeNo) {
			this.params.put("out_trade_no", outerTradeNo);
			return this;
		}
		
		// 可选(默认为30m)：该笔订单允许的最晚付款时间，逾期将关闭交易。1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。该参数数值不接受小数点
		public Builder timeoutExpress(String timeoutExpress) {
			this.params.put("timeout_express", timeoutExpress);
			return this;
		}
		
		// 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
		public Builder totalAmount(BigDecimal totalAmount) {
			this.params.put("total_amount", totalAmount.toString());
			return this;
		}
		
		// 可选：商品类型(0—虚拟类商品，1—实物类商品)虚拟类商品不支持使用花呗渠道
		public Builder goodsType(GoodsType type) {
			this.params.put("goods_type", String.valueOf(type.mark()));
			return this;
		}
		
		// 可选：公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
		public Builder passbackParams(String passbackParams) {
			this.params.put("passback_params", passbackParams);
			return this;
		}
		
		// 可选：优惠参数(仅与支付宝协商后可用)
		public Builder promoParams(String promoParams) {
			this.params.put("promo_params", promoParams);
			return this;
		}
		
		// 可选：可用渠道，用户只能在指定渠道范围内支付。当有多个渠道时用","分隔。与disable_pay_channels互斥
		public Builder enablePayPhannels(Channel... channels) {
			StringBuilder builder = new StringBuilder();
			for (Channel channel : channels)
				builder.append(channel.mark()).append(",");
			this.params.put("enable_pay_channels", builder.deleteCharAt(builder.length() - 1).toString());
			return this;
		}
		
		// 可选：禁用渠道，用户不可用指定渠道支付。当有多个渠道时用","分隔。与enable_pay_channels互斥
		public Builder disablePayChannels(Channel... channels) {
			StringBuilder builder = new StringBuilder();
			for (Channel channel : channels)
				builder.append(channel.mark()).append(",");
			this.params.put("disable_pay_channels", builder.deleteCharAt(builder.length() - 1).toString());
			return this;
		}
		
		// 可选：商户门店编号。该参数用于请求参数中以区分各门店，非必传项。
		public Builder storeId(String storeId) {
			this.params.put("store_id", storeId);
			return this;
		}
		
		// 可选：
		public Builder extendParams(AppPayModel param) {
			this.params.put("extend_params", SerializeUtil.GSON.toJson(param));
			return this;
		}
		
		// 可选：
		public Builder extUserInfo(ExtUserInfo extUserInfo) {
			this.params.put("ext_user_info", SerializeUtil.GSON.toJson(extUserInfo));
			return this;
		}
		
		@Override
		public AppPayRequest build() {
			this.bizContent = SerializeUtil.GSON.toJson(params);
			Map<String, String> map = SerializeUtil.JSON.beanToMap(SerializeUtil.GSON_ANNO, this);
			String sign = SignUtil.sign(AlipayConfig.priKey(), map);
			map = SerializeUtil.JSON.beanToMap(SerializeUtil.GSON_ANNO, this);
			map.put("sign", sign);
			return new AppPayRequest(new TreeMap<String, String>(map));
		}
	}
}
