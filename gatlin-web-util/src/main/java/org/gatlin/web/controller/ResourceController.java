package org.gatlin.web.controller;

import java.io.File;
import java.util.Set;

import javax.validation.Valid;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.param.ResourceModifyParam;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.web.bean.param.ResourceListParam;
import org.gatlin.web.bean.param.ResourceUploadParam;
import org.gatlin.web.util.Uploader;
import org.gatlin.web.util.validator.ResourceHook;
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
	@RequestMapping("list")
	public Object pictures(@RequestBody @Valid ResourceListParam param) {
		return resourceService.resources(param.query());
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public Object delete(@RequestBody @Valid SoaSidParam param) {
		Set<Resource> resources = resourceService.delete(param.getId());
		resources.forEach(item -> {
			File file = new File(item.getPath());
			file.delete();
		});
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("delete/form")
	public Object deleteFrom(@Valid SoaSidParam param) {
		Set<Resource> resources = resourceService.delete(param.getId());
		resources.forEach(item -> {
			File file = new File(item.getPath());
			file.delete();
		});
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
			resource.setPriority(param.getPriority());
			resource.setCfgId(param.getCfgResourceId());
			resource.setLink(null == param.getLink() ? StringUtil.EMPTY : param.getLink());
			resource.setOwner(null == param.getOwner() ? StringUtil.EMPTY : param.getOwner());
			Resource deleted = resourceService.upload(resource);
			if (null != deleted) {
				File file = new File(deleted.getPath());
				file.delete();
			}
			return resource;
		});
	}
	
	@ResponseBody
	@RequestMapping("modify")
	public Object modify(@RequestBody @Valid ResourceModifyParam param) {
		Resource deleted = resourceService.modify(param);
		if (null != deleted) {
			File file = new File(deleted.getPath());
			file.delete();
		}
		return Response.ok();
	}
}
