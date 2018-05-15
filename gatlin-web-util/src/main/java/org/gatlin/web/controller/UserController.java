package org.gatlin.web.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.param.SoaNameParam;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.enums.ResourceType;
import org.gatlin.soa.resource.bean.model.ResourceInfo;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.model.UserListInfo;
import org.gatlin.soa.user.bean.param.UserListParam;
import org.gatlin.soa.user.bean.param.UsernameResetParam;
import org.gatlin.util.lang.CollectionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Resource
	private UserService userService;
	@Resource
	private ResourceService resourceService;

	@ResponseBody
	@RequestMapping("list")
	public Object users(@RequestBody @Valid UserListParam param) {
		Pager<UserListInfo> pager = userService.users(param);
		if (CollectionUtil.isEmpty(pager.getList()))
			return pager;
		Set<Long> uids = new HashSet<Long>();
		pager.getList().forEach(item -> uids.add(item.getUid()));
		Query query = new Query().eq("cfg_id", ResourceType.AVATAR.mark()).in("owner", uids);
		Pager<ResourceInfo> resources = resourceService.resources(query);
		if (CollectionUtil.isEmpty(resources.getList()))
			return pager;
		for (UserListInfo info : pager.getList()) {
			Iterator<ResourceInfo> itr = resources.getList().iterator();
			while (itr.hasNext()) {
				ResourceInfo resource = itr.next();
				if (resource.getOwner() == info.getUid()) {
					itr.remove();
					info.setAvatar(resource.getUrl());
					break;
				}
			}
		}
		return pager;
	}
	
	@ResponseBody
	@RequestMapping("username/reset")
	public Object usernameReset(@RequestBody @Valid UsernameResetParam param) { 
		userService.usernameReset(param);
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("modify")
	public Object modify(@RequestBody @Valid SoaNameParam param) { 
		User user = param.getUser();
		user.setNickname(param.getName());
		userService.update(user);
		return Response.ok();
	}
}
