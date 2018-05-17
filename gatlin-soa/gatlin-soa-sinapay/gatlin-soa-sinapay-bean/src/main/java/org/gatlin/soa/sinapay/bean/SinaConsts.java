package org.gatlin.soa.sinapay.bean;

import org.gatlin.core.bean.model.option.IntegerOption;

public interface SinaConsts {

	// 绑卡 ticket 有效时间(分钟)
	final IntegerOption BANK_CARD_TICKET_EXPIRY						= new IntegerOption("sina_bank_card_ticket_expiry", 15);
	// 绑卡 ticket 最大使用次数
	final IntegerOption BANK_CARD_TICKET_MAXIMUM_USED				= new IntegerOption("sina_bank_card_ticket_maximum_used", 5);
}
