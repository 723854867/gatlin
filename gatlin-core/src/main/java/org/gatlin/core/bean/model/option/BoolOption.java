package org.gatlin.core.bean.model.option;

public class BoolOption extends Option<Boolean> {

	private static final long serialVersionUID = 8483961116691736610L;
	
	public BoolOption() {}

	public BoolOption(String key) {
		super(key);
	}

	public BoolOption(String key, boolean defaultValue) {
		super(key, defaultValue);
	}
}
