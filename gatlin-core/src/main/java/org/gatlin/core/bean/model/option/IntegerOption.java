package org.gatlin.core.bean.model.option;

public class IntegerOption extends Option<Integer> {

	private static final long serialVersionUID = -5866384089670235960L;

	public IntegerOption() {}

	public IntegerOption(String key) {
		super(key);
	}
	
	public IntegerOption(String key, Integer defaultValue) {
		super(key, defaultValue);
	}
}
