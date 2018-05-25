package org.gatlin.soa.jpush.manager;

import java.io.IOException;

import javax.annotation.Resource;

import org.gatlin.core.Gatlin;
import org.gatlin.core.bean.enums.Env;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.service.http.Callback;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.jpush.bean.model.Options;
import org.gatlin.sdk.jpush.bean.model.PushBody;
import org.gatlin.sdk.jpush.request.Jpush;
import org.gatlin.sdk.jpush.response.JPushResponse;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.jpush.bean.JPushCode;
import org.gatlin.soa.jpush.bean.entity.JPushDevice;
import org.gatlin.soa.jpush.mybatis.EntityGenerator;
import org.gatlin.soa.jpush.mybatis.dao.JPushDao;
import org.gatlin.util.DateUtil;
import org.gatlin.util.serial.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import okhttp3.Call;

@Component("JPushManager")
public class JPushManager {
	
	private static final Logger logger = LoggerFactory.getLogger(JPushManager.class);

	@Resource
	private Gatlin gatlin;
	@Resource
	private JPushDao jPushDao;
	
	// 绑定设备：返回之前绑定的设备
	@Transactional
	public JPushDevice bind(SoaSidParam param) { 
		User user = param.getUser();
		JPushDevice odevice = null;
		if (null != user) {	// 绑定
			Query query = new Query().eq("uid", user.getId()).forUpdate();
			odevice = jPushDao.queryUnique(query);
			if (null != odevice)	{		
				if (odevice.getId().equals(param.getId()))
					return null;
				odevice.setUid(null);
				odevice.setUpdated(DateUtil.current());
				jPushDao.update(odevice);
				odevice.setUid(user.getId());
			}
			query = new Query().eq("id", param.getId()).forUpdate();
			JPushDevice device = jPushDao.queryUnique(query);
			if (null == device)
				device = _register(param);
			else {			// 此种情况一般uid肯定为null，如果不为null表示该设备用户退出时没解绑，无需处理
				device.setUid(user.getId());
				device.setUpdated(DateUtil.current());
				jPushDao.update(device);
			}
		} else 			// 仅仅注册
			_register(param);
		return odevice;
	}
	
	@Transactional
	public JPushDevice unbind(SoaParam param) { 
		Query query = new Query().eq("uid", param.getUser().getId()).forUpdate();
		JPushDevice device = jPushDao.queryUnique(query);
		if (null == device)
			return null;
		device.setUid(null);
		device.setUpdated(DateUtil.current());
		jPushDao.update(device);
		return device;
	}
	
	@Transactional
	public void push(PushBody body) {
		Options options = body.getOptions();
		if (null == options) 
			options = new Options();
		options.apnsProduction(gatlin.env() == Env.ONLINE ? true : false);
		body.options(options);
		Jpush push = new Jpush(body);
		push.async(new Callback<JPushResponse>() {
			@Override
			public void onFailure(Call call, IOException e) {
				logger.error("极光推送请求失败！", e);
			}
			@Override
			protected void onResponse(JPushResponse response) {
				logger.info("极光推送：{}", SerializeUtil.GSON.toJson(response));
			}
		});
	}
	
	private JPushDevice _register(SoaSidParam param) {
		try {
			JPushDevice device = EntityGenerator.newJPushDevice(param);
			jPushDao.insert(device);
			return device;
		} catch (DuplicateKeyException e) {
			throw new CodeException(JPushCode.DEVICE_ALREADY_REGISTERED);
		}
	}
}
