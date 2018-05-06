package org.gatlin.soa.user.service;

import javax.annotation.Resource;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
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
	public long addressAdd(AddressAddparam param) {
		return geoManager.addressAdd(param);
	}

	@Override
	public void addressModify(AddressModifyParam param) {
		geoManager.addressModify(param);
	}

	@Override
	public void addressDelete(SoaLidParam param) {
		geoManager.addressDelete(param);
	}
	
	@Override
	public Pager<UserAddress> addresses(Query query) {
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return new Pager<UserAddress>(geoManager.addresses(query));
	}
}
