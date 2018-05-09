package org.gatlin.soa.user.api;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.bean.param.SoaLidParam;
import org.gatlin.soa.user.bean.entity.UserAddress;
import org.gatlin.soa.user.bean.param.AddressAddparam;
import org.gatlin.soa.user.bean.param.AddressModifyParam;

public interface GeoService {

	long addressAdd(AddressAddparam param, Geo geo);
	
	void addressModify(AddressModifyParam param, Geo geo);
	
	void addressDelete(SoaLidParam param);
	
	UserAddress address(long id);
	
	Pager<UserAddress> addresses(Query query);
}
