package org.gatlin.sdk.heepay.request;

import java.util.Map;
import java.util.TreeMap;

import org.gatlin.sdk.heepay.HeepayConfig;
import org.gatlin.sdk.heepay.SignUtil;
import org.gatlin.sdk.heepay.response.QueryBankCardResponse;
import org.gatlin.util.serial.SerializeUtil;

import com.google.gson.annotations.Expose;

/**
 * 查询银行卡号
 * 
 * @author lynn
 */
public class QueryBankCardRequest extends HeepayRequest<QueryBankCardResponse, QueryBankCardRequest> {
	
	public QueryBankCardRequest(Map<String, String> params) {
		super(HeepayConfig.QUERY_BANK_CARD_URL);
		this.params = params;
	}
	public static class Builder extends HeepayRequest.Builder<QueryBankCardRequest, Builder>{


		private static final long serialVersionUID = -8486842482665541648L;

		public Builder() {
			this.ext_param1 = null;
			this.notify_url = null;
		}
		
		/**
		 * 银行卡号（对私），只支持对私银行卡查询
		 */
		@Expose
		protected String bank_card_no;

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
