package org.gatlin.sdk.jpush.request;

import org.apache.commons.codec.binary.Base64;
import org.gatlin.core.service.http.HttpPost;
import org.gatlin.sdk.jpush.JPushConfig;
import org.gatlin.sdk.jpush.response.JPushResponse;
import org.gatlin.util.Consts;
import org.gatlin.util.bean.enums.ContentType;
import org.gatlin.util.bean.enums.Protocol;

public class JiGuangRequest<RESPONSE extends JPushResponse, REQUEST extends JiGuangRequest<RESPONSE, REQUEST>> extends HttpPost<RESPONSE, REQUEST> {

	public JiGuangRequest(String path) {
		super(JPushConfig.host(), JPushConfig.port(), path, ContentType.APPLICATION_JSON_UTF_8);
		this.protocol = Protocol.HTTPS;
		this.header("Authorization", _authentication());
	}
	
	private String _authentication() {
		StringBuilder builder = new StringBuilder();
		builder.append(JPushConfig.appKey()).append(":").append(JPushConfig.masterSecret());
		return "Basic " + Base64.encodeBase64String(builder.toString().getBytes(Consts.UTF_8));
	}
}
