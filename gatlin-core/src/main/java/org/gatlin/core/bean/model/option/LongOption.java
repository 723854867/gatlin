package org.gatlin.core.bean.model.option;

public class LongOption extends Option<Long> {

	private static final long serialVersionUID = -3456978246633613489L;

	public LongOption() {}

	public LongOption(String key) {
		super(key);
	}
	
	public LongOption(String key, Long defaultValue) {
		super(key, defaultValue);
	}
}
