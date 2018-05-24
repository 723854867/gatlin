package org.gatlin.sdk.jiguang.request;

import org.apache.commons.codec.binary.Base64;
import org.gatlin.core.service.http.HttpPost;
import org.gatlin.sdk.jiguang.JiGuangConfig;
import org.gatlin.sdk.jiguang.response.JiGuangResponse;
import org.gatlin.util.Consts;
import org.gatlin.util.bean.enums.ContentType;
import org.gatlin.util.bean.enums.Protocol;

public class JiGuangRequest<RESPONSE extends JiGuangResponse, REQUEST extends JiGuangRequest<RESPONSE, REQUEST>> extends HttpPost<RESPONSE, REQUEST> {

	public JiGuangRequest(String path) {
		super(JiGuangConfig.host(), JiGuangConfig.port(), path, ContentType.APPLICATION_JSON_UTF_8);
		this.protocol = Protocol.HTTPS;
		this.header("Authorization", _authentication());
	}
	
	private String _authentication() {
		StringBuilder builder = new StringBuilder();
		builder.append(JiGuangConfig.appKey()).append(":").append(JiGuangConfig.masterSecret());
		return "Basic " + Base64.encodeBase64String(builder.toString().getBytes(Consts.UTF_8));
	}
}
