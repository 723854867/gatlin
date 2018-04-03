package org.gatlin.soa.config.api;

import org.gatlin.core.bean.model.option.Option;
import org.gatlin.soa.config.bean.entity.CfgApi;
import org.gatlin.soa.config.bean.model.Configs;
import org.gatlin.soa.config.bean.model.query.ConfigQuery;

public interface ConfigService {

	<T> T config(Option<T> option);
	
	Configs configs(ConfigQuery query);
	
	CfgApi api(String path);
}
