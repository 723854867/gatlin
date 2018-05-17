package org.gatlin.soa.sinapay.bean;

import org.gatlin.core.bean.model.code.Code;

public interface SinaCode {

	final Code MEMBER_EXIST								= new Code("code.sina.member.exist", "会员已存在");
	final Code MEMBER_NOT_EXIST							= new Code("code.sina.member.not.exist", "会员不存在");
	final Code MEMBER_ALREADY_REALNAME					= new Code("code.sina.member.already.realname", "会员已实名");
	final Code BANK_UNSUPPORT							= new Code("code.sina.bank.unsupport", "新浪暂不支持该银行");
	final Code BANK_CARD_BIND_NOT_EXIST					= new Code("code.sina.bank.card.bind.not.exist", "新浪绑卡记录不存在");
	final Code BANK_CARD_BIND_TICKET_INVALID			= new Code("code.sina.bank.card.bind.ticket.invalid", "新浪绑卡ticket已失效");
}
