package org.gatlin.sdk.jiguang.request;

import org.gatlin.core.service.http.HttpPost;
import org.gatlin.sdk.jiguang.response.JiGuangResponse;

public class JiGuangPush<RESPONSE extends JiGuangResponse, REQUEST extends JiGuangPush<RESPONSE, REQUEST>> extends HttpPost<RESPONSE, REQUEST> {

	public JiGuangPush(String host, int port, String path) {
		super(host, port, path);
	}
}
