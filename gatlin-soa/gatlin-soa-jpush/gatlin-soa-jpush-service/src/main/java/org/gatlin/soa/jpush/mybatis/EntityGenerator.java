package org.gatlin.soa.jpush.mybatis;

import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.jpush.bean.entity.JPushDevice;
import org.gatlin.util.DateUtil;
import org.gatlin.util.lang.StringUtil;

public class EntityGenerator {

	public static final JPushDevice newJPushDevice(SoaSidParam param) {
		JPushDevice instance = new JPushDevice();
		instance.setId(param.getId());
		instance.setAlias(StringUtil.EMPTY);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
