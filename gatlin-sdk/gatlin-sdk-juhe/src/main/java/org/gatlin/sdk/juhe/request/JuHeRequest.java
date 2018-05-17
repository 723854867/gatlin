package org.gatlin.sdk.juhe.request;

import org.gatlin.core.service.http.HttpPost;
import org.gatlin.sdk.juhe.response.JuHeResponse;

public class JuHeRequest<RESPONSE extends JuHeResponse, REQUEST extends JuHeRequest<RESPONSE, REQUEST>> extends HttpPost<RESPONSE, REQUEST> {

	public JuHeRequest(String host, int port, String path) {
		super(host, port, path);
	}
}
