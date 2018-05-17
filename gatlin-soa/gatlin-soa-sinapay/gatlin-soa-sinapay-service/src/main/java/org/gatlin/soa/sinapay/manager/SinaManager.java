package org.gatlin.soa.sinapay.manager;

import javax.annotation.Resource;

import org.gatlin.soa.sinapay.bean.entity.SinaBank;
import org.gatlin.soa.sinapay.mybatis.dao.SinaBankDao;
import org.springframework.stereotype.Component;

@Component
public class SinaManager {

	@Resource
	private SinaBankDao sinaBankDao;
	
	public SinaBank bank(String id) {
		return sinaBankDao.getByKey(id);
	}
}
