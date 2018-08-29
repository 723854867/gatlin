package org.gatlin.soa.resource.bean;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.option.StrOption;

public class JzqConfig {

	private static final StrOption APP_SECRET = new StrOption("app_secret");
	private static final StrOption APP_KEY = new StrOption("app_key");
	private static final StrOption SERVICES_URL = new StrOption("services_url");

	public static final String appSecret() {
		return GatlinConfigration.get(APP_SECRET);
	}

	public static final String appKey() {
		return GatlinConfigration.get(APP_KEY);
	}

	public static final String ServicesUrl() {
		return GatlinConfigration.get(SERVICES_URL);
	}

}
