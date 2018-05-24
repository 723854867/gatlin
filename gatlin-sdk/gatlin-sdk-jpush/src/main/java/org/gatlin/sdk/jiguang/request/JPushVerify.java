package org.gatlin.sdk.jiguang.request;

import org.gatlin.sdk.jiguang.JiGuangConfig;
import org.gatlin.sdk.jiguang.model.PushBody;
import org.gatlin.sdk.jiguang.response.JiGuangResponse;

public class JPushVerify extends JiGuangRequest<JiGuangResponse, JPushVerify> {

	public JPushVerify(PushBody body) {
		super(JiGuangConfig.pathPushValidate());
		this.body = body;
	}
}
