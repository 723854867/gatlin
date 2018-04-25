package org.gatlin.soa.config.api;

import org.gatlin.core.bean.model.option.Option;
import org.gatlin.dao.bean.model.Query;
import org.gatlin.soa.config.bean.entity.CfgApi;
import org.gatlin.soa.config.bean.model.Configs;

public interface ConfigService {

	<T> T config(Option<T> option);
	
	Configs configs(Query query);
	
	CfgApi api(String path);
}
