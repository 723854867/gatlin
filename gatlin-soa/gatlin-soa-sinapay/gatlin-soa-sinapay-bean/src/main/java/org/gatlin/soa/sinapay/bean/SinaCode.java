package org.gatlin.soa.sinapay.bean;

import org.gatlin.core.bean.model.code.Code;

public interface SinaCode {

	final Code UNRECOGNIZE_DATA							= new Code("code.sina.unrecoginize.data", "未识别的新浪数据");
	final Code MEMBER_EXIST								= new Code("code.sina.member.exist", "新浪会员已存在");
	final Code MEMBER_NOT_EXIST							= new Code("code.sina.member.not.exist", "新浪会员不存在");
	final Code MEMBER_ALREADY_REALNAME					= new Code("code.sina.member.already.realname", "新浪会员已实名");
	final Code MEMBER_UNREALNAME						= new Code("code.sina.member.unrealname", "新浪会员未实名");
	final Code BANK_UNSUPPORT							= new Code("code.sina.bank.unsupport", "新浪暂不支持该银行");
	final Code BANK_CARD_UNBIND							= new Code("code.sina.bank.unbindt", "银行卡未在新浪绑定");
	final Code USER_UNWITHHOLD							= new Code("code.sina.user.unwithhold", "用户未开启委托扣款");
	final Code COMPANY_UNWITHHOLD						= new Code("code.sina.company.unwithhold", "企业未开启委托扣款");
	final Code RECHARGE_NOT_EXIST						= new Code("code.sina.recharge.not.exist", "新浪充值订单不存在");
	final Code BANK_CARD_BIND_TICKET_INVALID			= new Code("code.sina.bank.card.bind.ticket.invalid", "新浪绑卡ticket已失效");
	final Code BANK_CARD_BIND_NOT_EXIST					= new Code("code.sina.bank.card.bind.not.exist", "新浪绑卡记录不存在");
	final Code RECHARGE_COLLECT_NOT_EXIST				= new Code("code.sina.recharge.collect.not.exist", "新浪充值待收交易不存在");
	final Code WITHDRAW_UNPAYED							= new Code("code.sina.withdraw.unpayed", "新浪提现代付中");
	final Code WITHDRAW_NOT_EXIST						= new Code("code.sina.withdraw.not.exist", "新浪提现交易不存在");
	final Code WITHDRAW_PAY_NOT_EXIST					= new Code("code.sina.withdraw.pay.not.exist", "新浪提现代付交易不存在");
	final Code COMPANY_ALREADY_APPLY					= new Code("code.sina.company.already.aply", "已申请新浪企业认证");
}
