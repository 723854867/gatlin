package org.gatlin.core.bean.model.option;

public class ObjectOption<T> extends Option<T> {

	private static final long serialVersionUID = 8205737598655334886L;

	public ObjectOption() {}

	public ObjectOption(String key) {
		super(key);
	}
	
	public ObjectOption(String key, T defaultValue) {
		super(key, defaultValue);
	}
}
