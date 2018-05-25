package org.gatlin.soa.jpush.api;

import org.gatlin.sdk.jpush.bean.model.PushBody;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.jpush.bean.entity.JPushDevice;

public interface JPushService {

	JPushDevice bind(SoaSidParam param);
	
	JPushDevice unbind(SoaParam param);
	
	void push(PushBody body);
}
