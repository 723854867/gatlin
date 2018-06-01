package org.gatlin.web.controller;

import java.io.File;
import java.util.Set;

import javax.validation.Valid;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.model.ResourceInfo;
import org.gatlin.soa.bean.param.SoaSidParam;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.ResourceCode;
import org.gatlin.soa.resource.bean.entity.CfgResource;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.soa.resource.bean.param.ResourceModifyParam;
import org.gatlin.soa.resource.bean.param.ResourcesParam;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.web.bean.param.ResourceReplaceParam;
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
	public Object pictures(@RequestBody @Valid ResourcesParam param) {
		return resourceService.resources(param);
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public Object delete(@RequestBody @Valid SoaSidParam param) {
		Set<Resource> resources = resourceService.delete(param.getId(), param.getUser());
		resources.forEach(item -> {
			File file = new File(item.getPath());
			file.delete();
		});
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("delete/form")
	public Object deleteFrom(@Valid SoaSidParam param) {
		Set<Resource> resources = resourceService.delete(param.getId(), param.getUser());
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
		Assert.notNull(CoreCode.PARAM_ERR, param.getSource());
		CfgResource cfgResource = resourceService.cfgResource(param.getCfgResourceId());
		Assert.notNull(ResourceCode.RESOURCE_CONFIG_NOT_EXIST, cfgResource);
		if (null != resourceHook)
			resourceHook.uploadVerify(cfgResource, param);
		resourceService.uploadVerify(cfgResource, param.getOwner(), param.getSource().getSize());
		return uploader.upload(param.getSource(), cfgResource.getDirectory(), resource -> {
			resource.setName(param.getName());
			resource.setPriority(param.getPriority());
			resource.setCfgId(param.getCfgResourceId());
			resource.setLink(null == param.getLink() ? StringUtil.EMPTY : param.getLink());
			resource.setOwner(null == param.getOwner() ? StringUtil.EMPTY : param.getOwner());
			Resource deleted = resourceService.upload(resource, false);
			if (null != deleted) {
				File file = new File(deleted.getPath());
				file.delete();
			}
			return resource;
		});
	}
	
	@ResponseBody
	@RequestMapping("replace")
	public Object replace(@Valid ResourceReplaceParam param) {
		Assert.hasText(CoreCode.PARAM_ERR, param.getName());
		Assert.notNull(CoreCode.PARAM_ERR, param.getSource());
		ResourceInfo info = resourceService.resource(new Query().eq("id", param.getId()));
		Assert.notNull(ResourceCode.RESOURCE_NOT_EXIST, info);
		CfgResource cfgResource = resourceService.configs(new Query().eq("id", info.getCfgId())).getList().iterator().next();
		if (0 < cfgResource.getCacheSize()) {
			long maximumSize = cfgResource.getCacheUnit().bytes(cfgResource.getCacheSize());
			Assert.isTrue(ResourceCode.RESOURCE_SIZE_LIMIT, maximumSize >= param.getSource().getSize());
		}
		return uploader.upload(param.getSource(), cfgResource.getDirectory(), resource -> {
			resource.setName(param.getName());
			resource.setPriority(param.getPriority());
			resource.setId(info.getId());
			resource.setCfgId(info.getCfgId());
			resource.setOwner(info.getOwner());
			resource.setLink(null == param.getLink() ? StringUtil.EMPTY : param.getLink());
			Resource deleted = resourceService.upload(resource, true);
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
