package org.gatlin.sdk.jiguang.request;

import org.gatlin.core.service.http.HttpPost;
import org.gatlin.sdk.jiguang.JiGuangConfig;
import org.gatlin.sdk.jiguang.response.JiGuangResponse;
import org.gatlin.util.bean.enums.ContentType;
import org.gatlin.util.bean.enums.Protocol;

public class JiGuangRequest<RESPONSE extends JiGuangResponse, REQUEST extends JiGuangRequest<RESPONSE, REQUEST>> extends HttpPost<RESPONSE, REQUEST> {

	public JiGuangRequest(String path) {
		super(JiGuangConfig.host(), JiGuangConfig.port(), path, ContentType.APPLICATION_JSON_UTF_8);
		this.protocol = Protocol.HTTPS;
	}
}
