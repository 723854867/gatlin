package org.gatlin.sdk.ucpaas;

import org.gatlin.core.bean.model.option.IntegerOption;
import org.gatlin.core.bean.model.option.StrOption;

public interface UcpassConsts {

	interface Options {
		final StrOption SID									= new StrOption("ucpass.sid");
		final StrOption HOST								= new StrOption("ucpass.host");
		final StrOption TOKEN								= new StrOption("ucpass.token");
		final StrOption APPID								= new StrOption("ucpass.appid");
		final IntegerOption PORT							= new IntegerOption("ucpass.port");
		final StrOption PATH_SEND_SMS						= new StrOption("ucpass.path.send.sms");
		final StrOption PATH_CREATE_SMS_TEMPLATE			= new StrOption("ucpass.path.create.sms.template");
	}
}
