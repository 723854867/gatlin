package org.gatlin.soa.sinapay.bean;

import org.gatlin.core.bean.model.option.IntegerOption;
import org.gatlin.core.bean.model.option.StrOption;

public interface SinaConsts {

	// 绑卡 ticket 有效时间(分钟)
	final IntegerOption BANK_CARD_TICKET_EXPIRY						= new IntegerOption("sina_bank_card_ticket_expiry", 15);
	// 绑卡 ticket 最大使用次数
	final IntegerOption BANK_CARD_TICKET_MAXIMUM_USED				= new IntegerOption("sina_bank_card_ticket_maximum_used", 5);
	// 充值回调url
	final StrOption URL_NOTICE_RECHARGE_SINA						= new StrOption("url_notice_recharge_sina");
	// 待收回调
	final StrOption URL_NOTICE_COLLECT_SINA							= new StrOption("url_notice_collect_sina");
	// 提现代付回调
	final StrOption URL_NOTICE_WITHDRAW_PAY_SINA					= new StrOption("url_notice_withdraw_pay_sina");
	// 提现回调
	final StrOption URL_NOTICE_WITHDRAW_SINA						= new StrOption("url_notice_withdraw_sina");
	// app充值跳转地址
	final StrOption URL_RETURN_ORIGINAL								= new StrOption("url_return_original");
	// pc端充值跳转地址
	final StrOption URL_RETURN_BROWSER_PC							= new StrOption("url_return_browser_pc");
	// 移动端充值跳转地址
	final StrOption URL_RETURN_BROWSER_WAP							= new StrOption("url_return_browser_wap");
}
