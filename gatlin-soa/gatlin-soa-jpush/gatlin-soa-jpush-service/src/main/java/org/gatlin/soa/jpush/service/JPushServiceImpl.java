package org.gatlin.soa.jpush.service;

import javax.annotation.Resource;

import org.gatlin.sdk.jpush.bean.model.PushBody;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.jpush.api.JPushService;
import org.gatlin.soa.jpush.bean.entity.JPushDevice;
import org.gatlin.soa.jpush.manager.JPushManager;
import org.springframework.stereotype.Service;

@Service("JPushService")
public class JPushServiceImpl implements JPushService {
	
	@Resource
	private JPushManager JPushManager;

	@Override
	public JPushDevice bind(SoaSidParam param) {
		return JPushManager.bind(param);
	}
	
	@Override
	public JPushDevice unbind(SoaParam param) {
		return JPushManager.unbind(param);
	}
	
	@Override
	public void push(PushBody body) {
		JPushManager.push(body);
	}
}
