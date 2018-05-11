package org.gatlin.skd.chuanglan.request;

import org.gatlin.core.service.http.HttpPost;
import org.gatlin.skd.chuanglan.ChuangLanConfig;
import org.gatlin.skd.chuanglan.response.ChuangLanResponse;
import org.gatlin.util.bean.enums.ContentType;

public class ChuangLanRequest<RESPONSE extends ChuangLanResponse, REQUEST extends ChuangLanRequest<RESPONSE, REQUEST>> extends HttpPost<RESPONSE, REQUEST> {

	public ChuangLanRequest(String path) {
		super(ChuangLanConfig.host(), ChuangLanConfig.port(), path, ContentType.APPLICATION_JSON_UTF_8);
	}
	
	protected static class Builder implements Body {

		private static final long serialVersionUID = -6673320961995626257L;
		
		protected String account;
		protected String password;
		
		public Builder() {
			this.account = ChuangLanConfig.account();
			this.password = ChuangLanConfig.password();
		}
	}
}
