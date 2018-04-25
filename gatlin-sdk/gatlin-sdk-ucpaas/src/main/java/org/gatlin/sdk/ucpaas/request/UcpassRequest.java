package org.gatlin.sdk.ucpaas.request;

import org.gatlin.core.service.http.HttpPost;
import org.gatlin.sdk.ucpaas.UcpassConfig;
import org.gatlin.sdk.ucpaas.response.UcpassResponse;
import org.gatlin.util.bean.enums.ContentType;
import org.gatlin.util.bean.enums.Protocol;

public class UcpassRequest<RESPONSE extends UcpassResponse, REQUEST extends UcpassRequest<RESPONSE, REQUEST>> extends HttpPost<RESPONSE, REQUEST> {
	
	public UcpassRequest(String path) {
		super(UcpassConfig.host(), UcpassConfig.port(), path, ContentType.APPLICATION_JSON_UTF_8);
		this.protocol = Protocol.HTTPS;
	}
	
	public static class Builder implements org.gatlin.core.service.http.HttpPost.Body {

		private static final long serialVersionUID = 7491685951687117463L;
		
		protected String sid;
		protected String token;
		protected String appid;
		
		protected Builder() {
			this.sid = UcpassConfig.sid();
			this.token = UcpassConfig.token();
			this.appid = UcpassConfig.appId();
		}
	}
}
