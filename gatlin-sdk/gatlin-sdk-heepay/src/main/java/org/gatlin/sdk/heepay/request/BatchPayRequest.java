package org.gatlin.sdk.heepay.request;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import org.gatlin.sdk.heepay.HeepayConfig;
import org.gatlin.sdk.heepay.Des;
import org.gatlin.sdk.heepay.SignUtil;
import org.gatlin.sdk.heepay.bean.enums.AccountType;
import org.gatlin.sdk.heepay.bean.enums.Bank;
import org.gatlin.sdk.heepay.response.BatchPayResponse;
import org.gatlin.util.serial.SerializeUtil;

import com.google.gson.annotations.Expose;

/**
 * 批量支付
 * 
 * @author lynn
 */
public class BatchPayRequest extends HeepayRequest<BatchPayResponse, BatchPayRequest> {
	
	public BatchPayRequest(Map<String, String> params) {
		super();
		this.params = params;
	}
	
	public static class Builder extends HeepayRequest.Builder<BatchPayRequest, Builder> {

		private static final long serialVersionUID = -4885533906254982244L;

		public Builder() {
		}
		
		/**
		 * 批量付款订单号（要保证唯一）。最大长度50个字符，最小长度10个字符
		 */
		@Expose
		protected String batch_no;
		/**
		 * 付款总金额不可为空，单位：元，小数点后保留两位。
		 */
		@Expose
		protected BigDecimal batch_amt;
		/**
		 * 该次付款总笔数，付给多少人的数目，“单笔数据集”里面的数据总笔数
		 */
		@Expose
		protected int batch_num;
		/**
		 * 批付到银行帐户格式:“商户流水号^银行编号^对公对私^收款人帐号^收款人姓名^付款金额^付款理由^省份^城市^收款支行名称”来组织数据，
		 * 每条整数据间用“竖线”符号分隔，商户流水号长度最长20字符。
		 * 省市格式请严格按照4省市查询接口中的说明填写。注：整理好信息之后，需要对此数据进行3DES加密，
		 * 具体加密方式参考：接口规则-参数规定10 3DES加密机制,银行编号和对公对私对应数值请在 接口规则-参数规定8和9汉字编码是GBK
		 */
		@Expose
		protected String detail_data;
		
		public Builder batch_no(String batch_no) {
			this.batch_no=batch_no;
			return this;
		}
		
		public Builder notify_url(String notify_url) {
			this.notify_url=notify_url;
			return this;
		}
		
		public Builder batch_amt(BigDecimal batch_amt) {
			this.batch_amt = batch_amt;
			return this;
		}
		
		public Builder batch_num(int batch_num) {
			this.batch_num = batch_num;
			return this;
		}
		/**
		 * 
		 * @param orderNo 商户流水号
		 * @param bankName 银行编号
		 * @param bankNo 银行卡号
		 * @param name 用户姓名
		 * @param amount 打款金额
		 * @param bankDeposit 开户行
		 * @return
		 */
		public String detail_data(String orderNo,String bankName,String bankNo,String name,BigDecimal amount,String bankDeposit) {
			StringBuilder builder = new StringBuilder();
			builder.append(orderNo+"^");
			builder.append(Bank.match(bankName).mark()+"^");
			builder.append(AccountType.PRIVATE.mark()+"^");
			builder.append(bankNo+"^");
			builder.append(name+"^");
			builder.append(String.valueOf(amount.floatValue())+"^");
			builder.append(HeepayConfig.remit_reason+"^");
			builder.append("省^");
			builder.append("市^");
			builder.append(bankDeposit);
			return builder.toString();
		}
		
		public Builder detail_data(String detail_data){
			this.detail_data = detail_data;
			return this;
		}
		
		
		@Override
		public BatchPayRequest build() {
			Map<String, String> map = SerializeUtil.JSON.beanToMap(SerializeUtil.GSON_ANNO, this);
			map.put("key", HeepayConfig.KEY);
			System.out.println(SignUtil._signContent(map).toLowerCase());
			this.sign = SignUtil.MD5Sign(SignUtil._signContent(map).toLowerCase());
			this.detail_data = Des.Encrypt3Des(detail_data, HeepayConfig.DES_KEY,"ToHex16");
			map = SerializeUtil.JSON.beanToMap(SerializeUtil.GSON_ANNO, this);
			return new BatchPayRequest(new TreeMap<String, String>(map));
		}
	}
}
