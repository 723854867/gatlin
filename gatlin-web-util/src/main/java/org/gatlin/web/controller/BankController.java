package org.gatlin.web.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gatlin.core.bean.info.Pager;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.bean.model.ResourceInfo;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.config.bean.entity.CfgBank;
import org.gatlin.soa.resource.api.ResourceService;
import org.gatlin.soa.resource.bean.enums.ResourceType;
import org.gatlin.soa.resource.bean.param.ResourcesParam;
import org.gatlin.soa.user.api.BankCardService;
import org.gatlin.soa.user.bean.model.BankCardInfo;
import org.gatlin.soa.user.bean.param.BankCardsParam;
import org.gatlin.util.lang.CollectionUtil;
import org.gatlin.util.lang.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("bank")
public class BankController {
	
	@Resource
	private ConfigService configService;
	@Resource
	private BankCardService bankCardService;
	@Resource
	private ResourceService resourceService;
	
	@ResponseBody
	@RequestMapping("card/list")
	public Object cardBind(@RequestBody @Valid BankCardsParam param) {
		Pager<BankCardInfo> pager = bankCardService.cards(param);
		if (CollectionUtil.isEmpty(pager.getList()))
			return pager;
		Set<String> set = new HashSet<String>();
		pager.getList().forEach(item -> set.add(item.getBankId()));
		ResourcesParam rp = new ResourcesParam();
		rp.setOwners(set);
		rp.addCfgId(ResourceType.BANK_ICON.mark());
		Map<String, ResourceInfo> map = resourceService.ownerMap(rp);
		List<CfgBank> list = configService.banks(new Query().in("id", set)).getList();
		pager.getList().forEach(card -> {
			card.setIcon(map.get(card.getId()));
			card.setMobile(StringUtil.mask(card.getMobile(), 6, 4));
			card.setOwnerName(StringUtil.mask(card.getOwnerName(), 1, 0));
			card.setOwnerPhone(StringUtil.mask(card.getOwnerPhone(), 3, 4));
			card.setOwnerIdentity(StringUtil.mask(card.getOwnerIdentity(), 3, 4));
			for (CfgBank bank : list) {
				if (bank.getId().equals(card.getBankId())) {
					card.setBankName(bank.getName());
					break;
				}
			}
		});
		return pager;
	}
}
