package org.gatlin.web;

import java.util.List;

import javax.annotation.Resource;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.bean.info.Pager;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.sdk.sinapay.bean.enums.MemberType;
import org.gatlin.soa.sinapay.api.SinapayMemberService;
import org.gatlin.soa.sinapay.bean.SinaCode;
import org.gatlin.soa.sinapay.bean.entity.SinaBankCard;
import org.gatlin.soa.sinapay.bean.entity.SinaUser;
import org.gatlin.soa.sinapay.bean.enums.BankCardState;
import org.gatlin.util.lang.CollectionUtil;
import org.gatlin.util.lang.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class SinapayChecker {
	
	@Resource
	private SinapayMemberService sinapayMemberService;

	public void checkWithhold(MemberType type, Object tid) {
		Assert.isTrue(SinaCode.USER_UNWITHHOLD, sinapayMemberService.isWithhold(type, tid));
	}
	
	public void checkCardBind(MemberType type, Object tid) {
		SinaUser user = sinapayMemberService.user(tid, type);
		Assert.notNull(SinaCode.MEMBER_NOT_EXIST, user);
		Query query = new Query().eq("owner", user.getSinaId()).eq("state", BankCardState.BINDED.name());
		Pager<SinaBankCard> pager = sinapayMemberService.bankCards(query);
		if (type == MemberType.PERSONAL)
			Assert.isTrue(SinaCode.BANK_CARD_BIND_NOT_EXIST, !CollectionUtil.isEmpty(pager.getList()));
		else {
			Assert.isTrue(SinaCode.BANK_CARD_BIND_NOT_EXIST, !CollectionUtil.isEmpty(pager.getList()));
			List<SinaBankCard> list = pager.getList();
			list.forEach(card -> {
				if (StringUtil.hasText(card.getSinaCardId()))
					return;
			});
			throw new CodeException(SinaCode.BANK_CARD_ID_NOT_RESET);
		}
	}
}
