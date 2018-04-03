package org.gatlin.core.bean.model.option;

public class StrOption extends Option<String> {

	private static final long serialVersionUID = 7252344100376718061L;
	
	public StrOption() {}

	public StrOption(String key) {
		super(key);
	}
	
	public StrOption(String key, String defaultValue) {
		super(key, defaultValue);
	}
}
