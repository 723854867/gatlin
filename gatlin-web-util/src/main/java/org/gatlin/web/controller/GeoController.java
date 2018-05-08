package org.gatlin.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.bean.model.message.Response;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.param.SoaLidParam;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.user.api.GeoService;
import org.gatlin.soa.user.bean.param.AddressAddparam;
import org.gatlin.soa.user.bean.param.AddressModifyParam;
import org.gatlin.soa.user.bean.param.AddressesParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("geo")
public class GeoController {
	
	@Resource
	private GeoService geoService;
	
	@ResponseBody
	@RequestMapping("addresses/user")
	public Object addresses(@RequestBody @Valid SoaParam param) {
		Query query = new Query().eq("deleted", 0).eq("uid", param.getUser().getId());
		return geoService.addresses(query);
	}
	
	@ResponseBody
	@RequestMapping("addresses/all")
	public Object addresses(@RequestBody @Valid AddressesParam param) {
		return geoService.addresses(param.query());
	}
	
	@ResponseBody
	@RequestMapping("address/add")
	public Object addressAdd(@RequestBody @Valid AddressAddparam param) {
		return geoService.addressAdd(param);
	}
	
	@ResponseBody
	@RequestMapping("address/modify")
	public Object addressModify(@RequestBody @Valid AddressModifyParam param) {
		geoService.addressModify(param);
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("address/delete")
	public Object addressDelete(@RequestBody @Valid SoaLidParam param) {
		geoService.addressDelete(param);
		return Response.ok();
	}
}
