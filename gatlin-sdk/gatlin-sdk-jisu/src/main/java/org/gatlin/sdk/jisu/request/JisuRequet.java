package org.gatlin.sdk.jisu.request;

import java.io.IOException;
import java.lang.reflect.Type;

import org.gatlin.core.bean.exceptions.RequestFailException;
import org.gatlin.core.service.http.HttpGet;
import org.gatlin.sdk.jisu.JisuConfig;
import org.gatlin.sdk.jisu.JisuResponse;
import org.gatlin.util.serial.SerializeUtil;

import okhttp3.Response;

public class JisuRequet<RESULT, REQUEST extends JisuRequet<RESULT, REQUEST>> extends HttpGet<JisuResponse<RESULT>, REQUEST> {
	
	protected Type type;

	public JisuRequet(String path, Type type) {
		super(JisuConfig.host(), JisuConfig.port(), path);
		this.params.put("appkey", JisuConfig.appKey());
		this.type = type;
	}
	
	protected JisuResponse<RESULT> response(Response response) {
		try {
			JisuResponse<RESULT> resp = SerializeUtil.GSON.fromJson(response.body().string(), type);
			resp.verify();
			return resp;
		} catch (IOException e) {
			throw new RequestFailException(e);
		}
	}
}
