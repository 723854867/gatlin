package org.gatlin.core.service.http;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.gatlin.core.bean.exceptions.RequestFailException;
import org.gatlin.core.util.SpringContextUtil;
import org.gatlin.util.bean.enums.Protocol;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.util.serial.SerializeUtil;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

@SuppressWarnings("unchecked")
public abstract class HttpRequest<RESPONSE extends HttpResponse, REQUEST extends HttpRequest<RESPONSE, REQUEST>> {
	
	// 请求地址
	protected int port;
	protected String host;
	protected String path;
	protected Protocol protocol;
	protected Class<RESPONSE> clazz;
	protected HttpService httpService;
	protected Map<String, String> params = new HashMap<String, String>();
	protected Map<String, String> headers = new HashMap<String, String>();
	
	protected HttpRequest(String host, int port, String path) {
		this.port = port;
		this.host = host;
		this.path = path;
		this.protocol = Protocol.HTTP;
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();  
		this.clazz = (Class<RESPONSE>) generics[0];
		this.httpService = SpringContextUtil.getBean("httpService", HttpService.class);
	}
	
	public void async(Callback<RESPONSE> callback) {
		callback.request = this;
		this.httpService.async(request(), callback);
	}
	
	public RESPONSE sync() {
		Response response = this.httpService.sync(request());
		if (!response.isSuccessful())
			requestFailure(response);
		return response(response);
	}
	
	protected void requestFailure(Response response) {
		String errorContent = null;
		try {
			errorContent = response.body().string();
		} catch (Exception e) {}
		String error = response.message();
		if (StringUtil.hasText(errorContent))
			error += " - [" + errorContent + "]";
		throw new RequestFailException(response.code(), error);
	}
	
	protected Request request() {
		Request.Builder rb = new Request.Builder().url(url());
		for (Entry<String, String> entry : headers.entrySet())
			rb.addHeader(entry.getKey(), entry.getValue());
		return rb.build();
	}
	
	protected HttpUrl url() {
		HttpUrl.Builder builder = new HttpUrl.Builder().scheme(protocol.name());
		builder.host(host).port(port).addPathSegments(path);
		for (Entry<String, String> entry : params.entrySet())
			builder.addQueryParameter(entry.getKey(), entry.getValue());
		return builder.build();
	}
	
	/**
	 * 默认直接使用序列化类来序列化
	 */
	protected RESPONSE response(Response response) {
		try {
			RESPONSE resp = SerializeUtil.GSON.fromJson(response.body().string(), clazz);
			resp.verify();
			return resp;
		} catch (IOException e) {
			throw new RequestFailException(e);
		}
	}
	
	public REQUEST param(String name, String value) { 
		this.params.put(name, value);
		return (REQUEST) this;
	}
	
	public REQUEST header(String name, String value) { 
		this.headers.put(name, value);
		return (REQUEST) this;
	}
	
	public Map<String, String> params() {
		return params;
	}
}
