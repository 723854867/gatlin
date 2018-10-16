package org.gatlin.core.service.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.gatlin.core.bean.exceptions.RequestFailException;
import org.gatlin.core.condition.HttpCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
@Conditional(HttpCondition.class)
public class HttpService {
	
	private OkHttpClient client;
	
	@PostConstruct
	public void init() {
		this.client = new OkHttpClient.Builder()
				.readTimeout(300, TimeUnit.SECONDS)
				.writeTimeout(300, TimeUnit.SECONDS)
				.connectTimeout(60, TimeUnit.SECONDS).build();
	}
	
	public void requestAsync(Request request, Callback callback) throws RequestFailException {
		client.newCall(request).enqueue(callback);
	}
	
	/**
	 * 异步请求
	 * 
	 * @param url
	 * @param callback
	 */
	public void async(Request request, Callback callback) { 
		client.newCall(request).enqueue(callback);
	}
	
	/**
	 * 同步请求
	 */
	public <RESPONSE extends HttpResponse> Response sync(Request request) throws RequestFailException {
		try {
			return client.newCall(request).execute();
		} catch (IOException e) {
			throw new RequestFailException(e);
		}
	}
}
