package org.gatlin.web.controller;

import java.io.File;

import javax.validation.Valid;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.param.ResourceLinkParam;
import org.gatlin.soa.resource.bean.param.ResourceModifyParam;
import org.gatlin.web.bean.param.CfgResourceSearcher;
import org.gatlin.web.bean.param.ResourceSearcher;
import org.gatlin.web.bean.param.ResourceUploadParam;
import org.gatlin.web.util.Uploader;
import org.gatlin.web.util.hook.ResourceHook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("resource")
public class ResourceController {
	
	@javax.annotation.Resource
	private Uploader uploader;
	@Autowired(required = false)
	private ResourceHook resourceHook;
	@javax.annotation.Resource
	private ResourceService resourceService;
	
	@ResponseBody
	@RequestMapping("configs")
	public Object configs(@RequestBody @Valid CfgResourceSearcher param) {
		return resourceService.configs(param.query());
	}

	@ResponseBody
	@RequestMapping("list")
	public Object pictures(@RequestBody @Valid ResourceSearcher param) {
		return resourceService.resources(param.query());
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public Object delete(@RequestBody @Valid SoaSidParam param) {
		Resource resource = resourceService.delete(param.getId());
		File file = new File(resource.getPath());
		file.delete();
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("link")
	public Object link(@RequestBody @Valid ResourceLinkParam param) {
		resourceService.link(param.getId(), param.getLink());
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("upload")
	public Object upload(@Valid ResourceUploadParam param) {
		Assert.hasText(CoreCode.PARAM_ERR, param.getName());
		Assert.notNull(CoreCode.PARAM_ERR, param.getSource(), param.getPriority());
		CfgResource cfgResource = resourceService.uploadVerify(param.getCfgResourceId(), param.getOwner(), param.getSource().getSize());
		if (null != resourceHook)
			resourceHook.uploadVerify(cfgResource, param);
		return uploader.upload(param.getSource(), cfgResource.getDirectory(), resource -> {
			resource.setName(param.getName());
			resource.setOwner(param.getOwner());
			resource.setPriority(param.getPriority());
			resource.setCfgId(param.getCfgResourceId());
			resourceService.upload(resource);
			return resource;
		});
	}
	
	@ResponseBody
	@RequestMapping("modify")
	public Object modify(@RequestBody @Valid ResourceModifyParam param) {
		resourceService.modify(param);
		return Response.ok();
	}
}
