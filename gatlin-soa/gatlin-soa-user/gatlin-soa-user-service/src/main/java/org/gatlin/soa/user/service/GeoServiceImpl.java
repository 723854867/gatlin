package org.gatlin.soa.user.service;

import javax.annotation.Resource;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.bean.param.SoaLidParam;
import org.gatlin.soa.user.api.GeoService;
import org.gatlin.soa.user.bean.entity.UserAddress;
import org.gatlin.soa.user.bean.param.AddressAddparam;
import org.gatlin.soa.user.bean.param.AddressModifyParam;
import org.gatlin.soa.user.manager.GeoManager;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("geoService")
public class GeoServiceImpl implements GeoService {
	
	@Resource
	private GeoManager geoManager;

	@Override
	public long addressAdd(AddressAddparam param, Geo geo) {
		return geoManager.addressAdd(param, geo);
	}

	@Override
	public void addressModify(AddressModifyParam param, Geo geo) {
		geoManager.addressModify(param, geo);
	}

	@Override
	public void addressDelete(SoaLidParam param) {
		geoManager.addressDelete(param);
	}
	
	@Override
	public UserAddress address(long id) {
		return geoManager.address(id);
	}
	
	@Override
	public Pager<UserAddress> addresses(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return new Pager<UserAddress>(geoManager.addresses(query));
	}
}
