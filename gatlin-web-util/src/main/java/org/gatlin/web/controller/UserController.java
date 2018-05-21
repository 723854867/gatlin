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
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.model.ResourceInfo;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.enums.ResourceType;
import org.gatlin.soa.user.api.BankCardService;
import org.gatlin.soa.user.api.UserService;
import org.gatlin.soa.user.bean.model.BankCardInfo;
import org.gatlin.soa.user.bean.model.UserListInfo;
import org.gatlin.soa.user.bean.param.BankCardsParam;
import org.gatlin.soa.user.bean.param.UserListParam;
import org.gatlin.soa.user.bean.param.UserModifyParam;
import org.gatlin.soa.user.bean.param.UsernameResetParam;
import org.gatlin.util.lang.CollectionUtil;
import org.gatlin.util.lang.StringUtil;
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
	private BankCardService bankCardService;
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
				long owner = Long.valueOf(resource.getOwner());
				if (owner == info.getUid()) {
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
	public Object modify(@RequestBody @Valid UserModifyParam param) { 
		User user = param.getUser();
		if (StringUtil.hasText(param.getName()))
			user.setNickname(param.getName());
		if (null != param.getMod())
			user.setMod(param.getMod());
		userService.update(user);
		return Response.ok();
	}
	
	@ResponseBody
	@RequestMapping("bank/cards")
	public Object bankCards(@RequestBody @Valid BankCardsParam param) {
		param.setOwner(param.getUser().getId());
		param.setOwnerType(TargetType.USER.mark());
		Pager<BankCardInfo> pager = bankCardService.cards(param);
		if (CollectionUtil.isEmpty(pager.getList()))
			return pager;
		Set<String> set = new HashSet<String>();
		pager.getList().forEach(item -> set.add(item.getBankId()));
		Query query = new Query().in("owner", set).eq("cfg_id", ResourceType.BANK_ICON.mark());
		Pager<ResourceInfo> resources = resourceService.resources(query);
		pager.getList().forEach(card -> {
			if (!CollectionUtil.isEmpty(resources.getList())) {
				Iterator<ResourceInfo> itr = resources.getList().iterator();
				while (itr.hasNext()) {
					ResourceInfo icon = itr.next();
					if (icon.getOwner().equals(card.getBankId())) {
						card.setIcon(icon);
						break;
					}
				}
			}
			card.setMobile(StringUtil.mask(card.getMobile(), 6, 4));
			card.setOwnerName(StringUtil.mask(card.getOwnerName(), 1, 0));
			card.setOwnerPhone(StringUtil.mask(card.getOwnerPhone(), 3, 4));
			card.setOwnerIdentity(StringUtil.mask(card.getOwnerIdentity(), 3, 4));
		});
		return pager;
	}
}
