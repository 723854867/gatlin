package org.gatlin.sdk.heepay.request;

import java.io.IOException;

import org.gatlin.core.bean.exceptions.RequestFailException;
import org.gatlin.core.service.http.HttpPost;
import org.gatlin.sdk.heepay.HeepayConfig;
import org.gatlin.sdk.heepay.response.BatchPayResponse;
import org.gatlin.util.serial.SerializeUtil;

import com.google.gson.annotations.Expose;

import okhttp3.Response;

/*
 * 批量支付请求
 */
public class HeepayRequest <RESPONSE extends BatchPayResponse, REQUEST extends HeepayRequest<RESPONSE, REQUEST>> extends HttpPost<RESPONSE, REQUEST>{
	
	public HeepayRequest() {
		super(HeepayConfig.SMALL_AMOUNT_URL);
	}
	
	
	@Override
	protected RESPONSE response(Response response) {
		try {
			RESPONSE resp = SerializeUtil.XmlUtil.xmlToBean(response.body().string(), clazz);
			resp.verify();
			return resp;
		} catch (IOException e) {
			throw new RequestFailException(e);
		}
	}
	
	public static abstract class Builder<REQUEST, BUILDER extends Builder<REQUEST, BUILDER>> implements org.gatlin.core.service.http.HttpPost.Body {

		private static final long serialVersionUID = 7491685951697117463L;
		
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
		/**
		 * 支付后返回的商户处理页面，URL参数是以http://或https://开头的完整URL地址(后台处理)，提交的url地址必须外网能访问到，否则无法通知商户。
		 */
		@Expose
		protected String notify_url = HeepayConfig.notify_url;
		/**
		 * 商户自定义原样返回，最大长度50个字符
		 */
		@Expose
		protected String ext_param1 = HeepayConfig.ext_param1;
		/**
		 * MD5签名结果
		 */
		@Expose
		protected String sign;
		
		public abstract REQUEST build();
	}

}
