package org.gatlin.soa.user;

import org.gatlin.core.bean.model.option.IntegerOption;

public interface Consts {

	interface GlobalKeys {
		// 每个用户可编辑的最大地址数
		final IntegerOption ADDRESS_MAXIMUM				= new IntegerOption("address.maximum", 6);
	}
}
