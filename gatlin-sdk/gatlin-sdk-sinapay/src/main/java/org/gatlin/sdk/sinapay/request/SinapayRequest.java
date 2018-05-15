package org.gatlin.sdk.sinapay.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.service.http.HttpPost;
import org.gatlin.sdk.sinapay.SignUtil;
import org.gatlin.sdk.sinapay.SinapayConfig;
import org.gatlin.sdk.sinapay.response.SinapayResponse;
import org.gatlin.util.DateUtil;
import org.gatlin.util.bean.enums.Protocol;
import org.gatlin.util.serial.SerializeUtil;

import okhttp3.Response;

public class SinapayRequest<RESPONSE extends SinapayResponse, REQUEST extends SinapayRequest<RESPONSE, REQUEST>> extends HttpPost<RESPONSE, REQUEST> {

	protected SinapayRequest(String host, String path) {
		super(host, 443, path);
		this.protocol = Protocol.HTTPS;
	}
	
	protected void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	@Override
	protected RESPONSE response(Response response) {
		String value = null;
		try {
			value = new String(response.body().bytes(), "utf-8");
			value = value.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			value = URLDecoder.decode(value, "UTF-8");
		} catch (IOException e) {
			throw new CodeException();
		}
		RESPONSE resp = SerializeUtil.GSON.fromJson(value, clazz);
		resp.verify();
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	public abstract static class Builder<RESPONSE extends SinapayResponse, REQUEST extends SinapayRequest<RESPONSE, REQUEST>, BUILDER extends Builder<RESPONSE, REQUEST, BUILDER>> implements Body {
		
		private static final long serialVersionUID = -4425607359267779464L;
		
		protected Map<String, String> params = new TreeMap<String, String>();
		
		protected Builder(String service) {
			service(service);
			this.params.put("version", "1.2");
			this.params.put("sign_type", "RSA");
			this.params.put("sign_version", "1.0");
			this.params.put("encrypt_version", "1.0");
			this.params.put("_input_charset", "UTF-8");
			this.params.put("partner_id", SinapayConfig.partnerId());
			this.params.put("request_time", DateUtil.getDate(DateUtil.yyyyMMddHHmmss));
		}
		
		private BUILDER service(String service) {
			this.params.put("service", service);
			return (BUILDER) this;
		}

		public BUILDER memo(String memo) {
			this.params.put("memo", memo);
			return (BUILDER) this;
		}
		
		public BUILDER notifyUrl(String notifyUrl) {
			this.params.put("notifyUrl", notifyUrl);
			return (BUILDER) this;
		}
		
		public BUILDER returnUrl(String returnUrl) {
			this.params.put("return_url", returnUrl);
			return (BUILDER) this;
		}
		
		public REQUEST build() {
			SignUtil.encrypt(params);
			params.put("sign", SignUtil.sign(params));
			for (Entry<String, String> entry : params.entrySet()) {
				try {
					String key = URLEncoder.encode(entry.getKey(), "UTF-8");
					String value = URLEncoder.encode(entry.getValue(), "UTF-8");
					this.params.put(key, value);
				} catch (UnsupportedEncodingException e) {
					throw new CodeException();
				}
			}
			REQUEST request = buildReq();
			request.setParams(this.params);
			return request;
		}
		
		protected abstract REQUEST buildReq();
	}
}
