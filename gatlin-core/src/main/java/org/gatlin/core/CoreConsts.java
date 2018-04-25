package org.gatlin.core;

import org.gatlin.core.bean.enums.Env;
import org.gatlin.core.bean.enums.Locale;
import org.gatlin.core.bean.model.option.BoolOption;
import org.gatlin.core.bean.model.option.Option;

public interface CoreConsts {

	interface Options {
		final BoolOption HTTP_ENABLE								= new BoolOption("http.enable", false);
		final BoolOption MAIL_ENABLE								= new BoolOption("mail.enable", false);
		final Option<Env> GATLIN_ENV								= new Option<Env>("gatlin.env", Env.LOCAL);
		final Option<Locale> GATLIN_LOCALE							= new Option<Locale>("gatlin.locale", Locale.ZH_CN);
	}
}
