package org.gatlin.web.util;

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
		final IntegerOption RAPID_MAX_UPLOAD_SIZE_PER_FILE			= new IntegerOption("upload.maxUploadSizePerFile", 1048576);
		final StrOption RESOURCE_PATH								= new StrOption("resource.path");
		final StrOption RESOURCE_PREFIX								= new StrOption("resource.prefix");
		final IntegerOption ACCOUNT_MOD								= new IntegerOption("account.mod", 0);
		final IntegerOption ACCOUNT_RECHARGE_MOD					= new IntegerOption("account.recharge.mod", 0);
		final IntegerOption RECHARGE_TIMEOUT						= new IntegerOption("recharge.timeout", 30);
	}
}
