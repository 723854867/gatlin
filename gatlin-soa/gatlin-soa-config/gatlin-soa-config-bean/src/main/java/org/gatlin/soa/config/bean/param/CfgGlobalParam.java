package org.gatlin.soa.config.bean.param;

import org.gatlin.soa.bean.param.SoaParam;

public class CfgGlobalParam extends SoaParam {

	private static final long serialVersionUID = 5386525232135794664L;

	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
