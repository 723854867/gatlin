package org.gatlin.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.model.Geo;
import org.gatlin.soa.bean.param.SoaLidParam;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.config.bean.param.DistrictAddParam;
import org.gatlin.soa.config.bean.param.DistrictModifyParam;
import org.gatlin.soa.user.api.GeoService;
import org.gatlin.soa.user.bean.entity.UserAddress;
import org.gatlin.soa.user.bean.model.AddressInfo;
import org.gatlin.soa.user.bean.param.AddressAddparam;
import org.gatlin.soa.user.bean.param.AddressModifyParam;
import org.gatlin.soa.user.bean.param.AddressesParam;
import org.gatlin.util.lang.CollectionUtil;
import org.gatlin.util.lang.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("geo")
public class GeoController {
	
	@Resource
	private GeoService geoService;
	@Resource
	private ConfigService configService;
	
	@ResponseBody
	@RequestMapping("districts")
	public Object districts(@RequestBody @Valid SoaParam param) {
		Query query = new Query().eq("valid", 1);
		return configService.districts(query);
	}
	
	@ResponseBody
	@RequestMapping("districts/all")
	public Object districtsAll(@RequestBody @Valid SoaParam param) {
		return configService.districts(new Query());
	}
	
	@ResponseBody
	@RequestMapping("districts/add")
	public Object districtsAdd(@RequestBody @Valid DistrictAddParam param) {
		configService.districtAdd(param);
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("districts/modify")
	public Object districtsModify(@RequestBody @Valid DistrictModifyParam param) {
		configService.districtModify(param);
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("addresses/user")
	public Object addresses(@RequestBody @Valid SoaParam param) {
		Query query = new Query().eq("deleted", 0).eq("uid", param.getUser().getId());
		Pager<UserAddress> pager = geoService.addresses(query);
		if (CollectionUtil.isEmpty(pager.getList()))
			return pager;
		Set<String> codes = new HashSet<String>();
		pager.getList().forEach(address -> codes.add(address.getCounty()));
		Map<String, Geo> geos = configService.geos(codes, false);
		return Pager.<AddressInfo, UserAddress>convert(pager, () -> {
			List<AddressInfo> list = new ArrayList<AddressInfo>();
			pager.getList().forEach(address -> list.add(new AddressInfo(address, geos.get(address.getCounty()))));
			return list;
		});
	}
	
	@ResponseBody
	@RequestMapping("addresses/all")
	public Object addresses(@RequestBody @Valid AddressesParam param) {
		Pager<UserAddress> pager = geoService.addresses(param.query());
		if (CollectionUtil.isEmpty(pager.getList()))
			return pager;
		Set<String> codes = new HashSet<String>();
		pager.getList().forEach(address -> codes.add(address.getCounty()));
		Map<String, Geo> geos = configService.geos(codes, false);
		return Pager.<AddressInfo, UserAddress>convert(pager, () -> {
			List<AddressInfo> list = new ArrayList<AddressInfo>();
			pager.getList().forEach(address -> list.add(new AddressInfo(address, geos.get(address.getCounty()))));
			return list;
		});
	}
	
	@ResponseBody
	@RequestMapping("address/add")
	public Object addressAdd(@RequestBody @Valid AddressAddparam param) {
		Geo geo = configService.geo(param.getCounty(), true);
		Assert.hasText(CoreCode.PARAM_ERR, geo.getCounty());
		return geoService.addressAdd(param, geo);
	}
	
	@ResponseBody
	@RequestMapping("address/modify")
	public Object addressModify(@RequestBody @Valid AddressModifyParam param) {
		Geo geo = null;
		if (StringUtil.hasText(param.getCounty())) {
			geo = configService.geo(param.getCounty(), true);
			Assert.hasText(CoreCode.PARAM_ERR, geo.getCounty());
		}
		geoService.addressModify(param, geo);
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("address/delete")
	public Object addressDelete(@RequestBody @Valid SoaLidParam param) {
		geoService.addressDelete(param);
		return Response.ok();
	}
}
