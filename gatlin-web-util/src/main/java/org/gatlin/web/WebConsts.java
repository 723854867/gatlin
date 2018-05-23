package org.gatlin.web;

import org.gatlin.core.bean.model.option.BoolOption;
import org.gatlin.core.bean.model.option.IntegerOption;
import org.gatlin.core.bean.model.option.LongOption;
import org.gatlin.core.bean.model.option.StrOption;

public interface WebConsts {

	interface Options {
		final IntegerOption SERVER_STATE							= new IntegerOption("server.state", 0);
		final BoolOption UPLOAD_ENABLE								= new BoolOption("upload.enable", false);
		final LongOption RAPID_MAX_UPLOAD_SIZE						= new LongOption("upload.maxUploadSize", 5242880l);
		final IntegerOption RAPID_MAX_IN_MEMORY_SIZE				= new IntegerOption("upload.maxInMemorySize", 51200);
		final IntegerOption RAPID_MAX_UPLOAD_SIZE_PER_FILE			= new IntegerOption("upload.maxUploadSizePerFile", 10485760);
		final StrOption RESOURCE_PATH								= new StrOption("resource_path");
		final StrOption RESOURCE_PREFIX								= new StrOption("resource_prefix");
		final IntegerOption ACCOUNT_MOD_USER						= new IntegerOption("account.mod.user", 0);
		final IntegerOption ACCOUNT_RECHARGE_MOD_USER				= new IntegerOption("account.recharge.mod.user", 0);
		final IntegerOption ACCOUNT_RECHARGE_MOD_PLAT				= new IntegerOption("account.recharge.mod.plat", 0);
		final IntegerOption ACCOUNT_RECHARGE_MOD_COMPANY			= new IntegerOption("account.recharge.mod.company", 0);
		final IntegerOption ACCOUNT_WITHDRAW_MOD_USER				= new IntegerOption("account.withdraw.mod.user", 1);
		final IntegerOption ACCOUNT_WITHDRAW_MOD_COMPANY			= new IntegerOption("account.withdraw.mod.company", 16);
		final IntegerOption RECHARGE_TIMEOUT						= new IntegerOption("recharge.timeout", 30);
		final BoolOption ALIPAY_ENABLE								= new BoolOption("alipay.enable", false);
	}
}
