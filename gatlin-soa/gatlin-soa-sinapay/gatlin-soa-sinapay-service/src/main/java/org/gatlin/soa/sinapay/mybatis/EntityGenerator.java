package org.gatlin.soa.sinapay.mybatis;

import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;

public class EntityGenerator {

	public static final SinaUser newSinaUser(String tid, MemberType type) {
		SinaUser instance = new SinaUser();
		instance.setTid(tid);
		instance.setType(type.mark());
		instance.setSinaId(IDWorker.INSTANCE.nextSid());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
}
