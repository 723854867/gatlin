package org.gatlin.web.util;

import org.gatlin.core.bean.model.option.BoolOption;
import org.gatlin.core.bean.model.option.IntegerOption;
import org.gatlin.core.bean.model.option.LongOption;

public interface WebConsts {

	interface Options {
		final BoolOption UPLOAD_ENABLE								= new BoolOption("upload.enable", false);
		final LongOption RAPID_MAX_UPLOAD_SIZE						= new LongOption("rapid.maxUploadSize", 5242880l);
		final IntegerOption RAPID_MAX_IN_MEMORY_SIZE				= new IntegerOption("rapid.maxInMemorySize", 51200);
		final IntegerOption RAPID_MAX_UPLOAD_SIZE_PER_FILE			= new IntegerOption("rapid.maxUploadSizePerFile", 1048576);
	}
}
