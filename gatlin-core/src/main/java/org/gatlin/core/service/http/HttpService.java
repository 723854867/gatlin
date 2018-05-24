package org.gatlin.core.service.http;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.gatlin.core.bean.exceptions.RequestFailException;
import org.gatlin.core.condition.HttpCondition;
import org.gatlin.util.lang.StringUtil;
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
		this.client = new OkHttpClient();
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
			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				String errorContent = null;
				try {
					errorContent = response.body().string();
				} catch (Exception e) {}
				String error = response.message();
				if (StringUtil.hasText(errorContent))
					error += " - [" + errorContent + "]";
				throw new RequestFailException(response.code(), error);
			}
			return response;
		} catch (IOException e) {
			throw new RequestFailException(e);
		}
	}
}
