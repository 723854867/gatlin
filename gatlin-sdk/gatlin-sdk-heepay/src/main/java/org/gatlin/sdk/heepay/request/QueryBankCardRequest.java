package org.gatlin.sdk.heepay.request;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import org.gatlin.sdk.heepay.HeepayConfig;
import org.gatlin.sdk.heepay.SignUtil;
import org.gatlin.sdk.heepay.response.QueryBankCardResponse;
import org.gatlin.util.serial.SerializeUtil;

import com.google.gson.annotations.Expose;

/**
 * 批量支付
 * 
 * @author lynn
 */
public class QueryBankCardRequest extends HeepayRequest<QueryBankCardResponse, QueryBankCardRequest> {
	
	public QueryBankCardRequest(Map<String, String> params) {
		super(HeepayConfig.QUERY_BANK_CARD_URL);
		this.params = params;
	}
	
	public static class Builder implements Serializable{


		private static final long serialVersionUID = -8486842482665541648L;

		public Builder() {
		}
		
		/**
		 * 银行卡号（对私），只支持对私银行卡查询
		 */
		@Expose
		protected String bank_card_no;
		/**
		 * 当前接口版本号3
		 */
		@Expose
		protected String version = "3";
		/**
		 * 商户内码，例如1664502
		 */
		@Expose
		protected String agent_id = HeepayConfig.agent_id;
		
		@Expose
		protected String sign;
	

		
		public Builder bank_card_no(String bank_card_no) {
			this.bank_card_no=bank_card_no;
			return this;
		}
		
		public QueryBankCardRequest build() {
			Map<String, String> map = SerializeUtil.JSON.beanToMap(SerializeUtil.GSON_ANNO, this);
			map.put("key", HeepayConfig.KEY);
			System.out.println(SignUtil._signContent(map).toLowerCase());
			this.sign = SignUtil.MD5Sign(SignUtil._signContent(map).toLowerCase());
			map = SerializeUtil.JSON.beanToMap(SerializeUtil.GSON_ANNO, this);
			return new QueryBankCardRequest(new TreeMap<String, String>(map));
		}
	}
}
