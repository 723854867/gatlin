package org.gatlin.soa.config.bean.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.model.option.Option;
import org.gatlin.soa.config.bean.entity.CfgGlobal;

public class Configs implements Serializable {

	private static final long serialVersionUID = 1814617119504097333L;

	private Map<String, CfgGlobal> globals = new HashMap<String, CfgGlobal>();
	
	public <T> T get(Option<T> option) {
		CfgGlobal config = globals.get(option.key());
		return null == config ? option.defaultValue() : GatlinConfigration.CONVERSION_SERVICE.convert(config.getValue(), option.clazz());
	}
	
	public Map<String, CfgGlobal> getGlobals() {
		return globals;
	}
	
	public void setGlobals(Map<String, CfgGlobal> globals) {
		this.globals = globals;
	}
}
