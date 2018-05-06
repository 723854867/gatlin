package org.gatlin.soa.user.api;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.param.SoaLidParam;
import org.gatlin.soa.user.bean.entity.UserAddress;
import org.gatlin.soa.user.bean.param.AddressAddparam;
import org.gatlin.soa.user.bean.param.AddressModifyParam;

public interface GeoService {

	long addressAdd(AddressAddparam param);
	
	void addressModify(AddressModifyParam param);
	
	void addressDelete(SoaLidParam param);
	
	Pager<UserAddress> addresses(Query query);
}
