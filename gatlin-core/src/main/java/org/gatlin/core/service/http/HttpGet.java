package org.gatlin.core.service.http;

public class HttpGet<RESPONSE extends HttpResponse, REQUEST extends HttpGet<RESPONSE, REQUEST>> extends HttpRequest<RESPONSE, REQUEST> {

	public HttpGet(String host, int port, String path) {
		super(host, port, path);
	}
}
