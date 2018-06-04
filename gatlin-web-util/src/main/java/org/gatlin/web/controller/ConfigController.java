package org.gatlin.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.soa.bean.model.ResourceInfo;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.config.bean.entity.CfgBank;
import org.gatlin.soa.config.bean.model.BankInfo;
import org.gatlin.soa.config.bean.param.BankParam;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.enums.ResourceType;
import org.gatlin.soa.resource.bean.param.CfgResourceEditParam;
import org.gatlin.soa.resource.bean.param.CfgResourceListParam;
import org.gatlin.soa.resource.bean.param.ResourcesParam;
import org.gatlin.util.lang.CollectionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("config")
public class ConfigController {

	@Resource
	private ConfigService configService;
	@Resource
	private ResourceService resourceService;
	
	@ResponseBody
	@RequestMapping("banks")
	public Object banks(@RequestBody @Valid BankParam param) {
		Pager<CfgBank> pager = configService.banks(param.query());
		if (CollectionUtil.isEmpty(pager.getList()))
			return pager;
		Set<String> set = new HashSet<String>();
		pager.getList().forEach(item -> set.add(item.getId()));
		ResourcesParam rp = new ResourcesParam();
		rp.addCfgId(ResourceType.BANK_ICON.mark());
		rp.setOwners(set);
		Map<String, ResourceInfo> map = resourceService.ownerMap(rp);
		return Pager.<BankInfo, CfgBank>convert(pager, () -> {
			List<BankInfo> list = new ArrayList<BankInfo>();
			pager.getList().forEach(item -> list.add(new BankInfo(item, map.get(item.getId()))));
			return list;
		});
	}
	
	@ResponseBody
	@RequestMapping("bank/edit")
	public Object bankEdit(@RequestBody @Valid BankParam param) {
		configService.bankEdit(param);
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("resource/edit")
	public Object resourceEdit(@RequestBody @Valid CfgResourceEditParam param) { 
		resourceService.cfgResourceEdit(param);
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("resources")
	public Object resources(@RequestBody @Valid CfgResourceListParam param) { 
		return resourceService.configs(param.query());
	}
}
